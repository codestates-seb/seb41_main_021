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
          lat: lat, // 위도
          lng: lng, // 경도
        },
        level: taeoonda ? f(diff, 0.005, 5) : f(diff, 0.005, 4),
        isLoading: false,
      }));
    } else if (navigator.geolocation) {
      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(
        position => {
          setState(
            prev => ({
              ...prev,
              center: {
                lat: position.coords.latitude, // 위도
                lng: position.coords.longitude, // 경도
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
      // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
      setState(prev => ({
        ...prev,
        errMsg: 'geolocation을 사용할수 없어요..',
        isLoading: false,
      }));
    }
  }, []);

  useEffect(() => {
    //장소 검색 객체
    const ps = new kakao.maps.services.Places();

    //장소 검색 객체를 통해 키워드로 장소검색을 요청
    ps.keywordSearch(des.destination, placesSearchCB);

    //장소 검색이 완료됐을 때 호출되는 콜백함수
    function placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        // const bounds = new kakao.maps.LatLngBounds();
        // let markers = [];

        // setMarkers(markers);

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        // map.setBounds(bounds);

        dispatch(setPlaces({ places: data }));
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        dispatch(setPlaces({ places: [] }));
        console.log('검색 결과가 존재하지 않습니다.');
        return;
      } else if (status === kakao.maps.services.Status.ERROR) {
        dispatch(setPlaces({ places: [] }));
        console.log('검색 결과 중 오류가 발생했습니다.');
        return;
      }
    }
  }, [des.destination]);

  function getAddr(lat, lng) {
    // 주소-좌표 변환 객체를 생성합니다
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
