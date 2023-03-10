import React, { useEffect, useState } from 'react';
import { MapMarker, Map } from 'react-kakao-maps-sdk';

import { addDeparture, setDeparture } from '../redux/slice/DestinationSlice';
import { useDispatch, useSelector } from 'react-redux';
import { setPlaces } from '../redux/slice/DestinationSlice';
import { f } from '../util/f';

const { kakao } = window;

const KakaoMap = ({ taeoonda }) => {
  const dispatch = useDispatch();
  const des = useSelector(state => {
    return state.destination;
  });
  const startImage = {
    src: 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b.png',
    size: {
      width: 50,
      height: 45,
    },
    options: {
      offset: {
        x: 15,
        y: 45,
      },
    },
  };
  const startDragImage = {
    src: 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_drag.png',
    size: {
      width: 50,
      height: 64,
    },
    options: {
      offset: {
        x: 15,
        y: 54,
      },
    },
  };
  const endImage = {
    src: 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/blue_b.png',
    size: {
      width: 50,
      height: 45,
    },
    options: {
      offset: {
        x: 15,
        y: 45,
      },
    },
  };

  const [info, setInfo] = useState();
  const [markers, setMarkers] = useState([]);
  const [map, setMap] = useState();

  const [draggable, setDraggable] = useState(true);
  const [start, setStart] = useState(startImage);
  const [state, setState] = useState({
    level: 5,
    center: {
      lat: 33.450701,
      lng: 126.570667,
    },
    errMsg: null,
    isLoading: true,
    isPanto: true,
  });

  useEffect(() => {
    if (des.isDeparture && des.isDestination) {
      const lat = (parseFloat(des.destinationPoint.latitude) + parseFloat(des.departurePoint.latitude)) / 2;
      const lng = (parseFloat(des.destinationPoint.longitude) + parseFloat(des.departurePoint.longitude)) / 2;
      const latDiff = Math.abs(parseFloat(des.destinationPoint.latitude) - parseFloat(des.departurePoint.latitude));
      const lngDiff = Math.abs(parseFloat(des.destinationPoint.longitude) - parseFloat(des.departurePoint.longitude));
      const diff = latDiff + lngDiff;
      setDraggable(false);
      setState(prev => ({
        ...prev,
        center: {
          lat: lat, // ??????
          lng: lng, // ??????
        },
        level: taeoonda ? f(diff, 0.005, 5) : f(diff, 0.005, 4),
        isLoading: false,
      }));
    } else if (navigator.geolocation) {
      // GeoLocation??? ???????????? ?????? ????????? ???????????????
      navigator.geolocation.getCurrentPosition(
        position => {
          setState(
            prev => ({
              ...prev,
              center: {
                lat: position.coords.latitude, // ??????
                lng: position.coords.longitude, // ??????
              },
              isLoading: false,
            }),
            getAddr(position.coords.latitude, position.coords.longitude),
          );
        },
        err => {
          setState(prev => ({
            ...prev,
            errMsg: err.message,
            isLoading: false,
          }));
        },
      );
    } else {
      // HTML5??? GeoLocation??? ????????? ??? ????????? ?????? ?????? ????????? ??????????????? ????????? ???????????????
      setState(prev => ({
        ...prev,
        errMsg: 'geolocation??? ???????????? ?????????..',
        isLoading: false,
      }));
    }
  }, []);

  useEffect(() => {
    //?????? ?????? ??????
    const ps = new kakao.maps.services.Places();

    //?????? ?????? ????????? ?????? ???????????? ??????????????? ??????
    ps.keywordSearch(des.destination, placesSearchCB);

    //?????? ????????? ???????????? ??? ???????????? ????????????
    function placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        // const bounds = new kakao.maps.LatLngBounds();
        // let markers = [];

        // setMarkers(markers);

        // ????????? ?????? ????????? ???????????? ?????? ????????? ??????????????????
        // map.setBounds(bounds);

        dispatch(setPlaces({ places: data }));
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        dispatch(setPlaces({ places: [] }));
        console.log('?????? ????????? ???????????? ????????????.');
        return;
      } else if (status === kakao.maps.services.Status.ERROR) {
        dispatch(setPlaces({ places: [] }));
        console.log('?????? ?????? ??? ????????? ??????????????????.');
        return;
      }
    }
  }, [des.destination]);

  function getAddr(lat, lng) {
    // ??????-?????? ?????? ????????? ???????????????
    let geocoder = new kakao.maps.services.Geocoder();

    let coord = new kakao.maps.LatLng(lat, lng);
    let callback = function (result, status) {
      if (status === kakao.maps.services.Status.OK) {
        dispatch(
          addDeparture({
            departurePoint: {
              longitude: lng,
              latitude: lat,
              address: result[0].address.address_name,
            },
            isDeparture: true,
          }),
        );
        dispatch(setDeparture({ departure: result[0].address.address_name }));
      }
    };
    geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
  }
  return (
    <Map
      center={state.center}
      style={{ width: '100%', height: '100%' }}
      level={state.level}
      isPanto={state.isPanto}
      onCenterChanged={map =>
        draggable &&
        setState({
          level: map.getLevel(),
          center: {
            lat: map.getCenter().getLat(),
            lng: map.getCenter().getLng(),
          },
        })
      }
      onDragStart={() => draggable && setStart(startDragImage)}
      onDragEnd={map =>
        draggable &&
        setState(
          {
            level: map.getLevel(),
            center: {
              lat: map.getCenter().getLat(),
              lng: map.getCenter().getLng(),
            },
          },
          getAddr(state.center.lat, state.center.lng),
          setStart(startImage),
        )
      }>
      {draggable ? (
        <MapMarker position={state.center} image={start}></MapMarker>
      ) : (
        <MapMarker
          position={{ lat: des.departurePoint.latitude, lng: des.departurePoint.longitude }}
          image={start}></MapMarker>
      )}

      {des.isDestination && (
        <MapMarker
          position={{ lat: des.destinationPoint.latitude, lng: des.destinationPoint.longitude }}
          image={endImage}></MapMarker>
      )}
    </Map>
  );
};

export default KakaoMap;
