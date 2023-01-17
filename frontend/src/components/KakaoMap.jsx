import React, { useEffect, useState } from 'react';
import { MapMarker, Map } from 'react-kakao-maps-sdk';

const { kakao } = window;
// *** 전역 상태 관리 되면 해야 할 것 ***
// - 카카오 맵 -
// 탑니다인지 태웁니다인지 구분
// 출발지인지 도착지인지 구분
// 출발지를 어떻게 정하는지에 대한 구분 ( 드래그, 직접 입력 후 선택)
// onCenterChanged에 setIsDeparture넣기

// - 디테일 페이지 -
// addressName, placeName, x, y 상태 얻어오기, 적용
// 등록하기 버튼 클릭 시 setIsDeparture, setIsDestination 구분
// setIsDeparture, setIsDestination 상태 변경

const KakaoMap = props => {
  const startImage = {
    src: 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b.png',
    size: [50, 45],
    options: {
      offset: [15, 43],
    },
  };
  const { searchPlace, setPlaces, setDeparture } = props;

  const [info, setInfo] = useState();
  const [markers, setMarkers] = useState([]);
  const [map, setMap] = useState();

  const [state, setState] = useState({
    center: {
      lat: 33.450701,
      lng: 126.570667,
    },
    errMsg: null,
    isLoading: true,
    isPanto: true,
  });

  useEffect(() => {
    if (navigator.geolocation) {
      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(
        position => {
          setState(prev => ({
            ...prev,
            center: {
              lat: position.coords.latitude, // 위도
              lng: position.coords.longitude, // 경도
            },
            isLoading: false,
          }));
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
    ps.keywordSearch(searchPlace, placesSearchCB);

    //장소 검색이 완료됐을 때 호출되는 콜백함수
    function placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        // const bounds = new kakao.maps.LatLngBounds();
        // let markers = [];

        // setMarkers(markers);

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        // map.setBounds(bounds);

        setPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        setPlaces([]);
        console.log('검색 결과가 존재하지 않습니다.');
        return;
      } else if (status === kakao.maps.services.Status.ERROR) {
        setPlaces([]);
        console.log('검색 결과 중 오류가 발생했습니다.');
        return;
      }
    }
  }, [searchPlace]);

  function getAddr(lat, lng) {
    // 주소-좌표 변환 객체를 생성합니다
    let geocoder = new kakao.maps.services.Geocoder();

    let coord = new kakao.maps.LatLng(lat, lng);
    let callback = function (result, status) {
      if (status === kakao.maps.services.Status.OK) {
        console.log(result[0].address.address_name);
        setDeparture(result[0].address.address_name);
      }
    };
    geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
  }
  return (
    <Map
      center={state.center}
      style={{ width: '100%', height: '100%' }}
      level={5}
      isPanto={state.isPanto}
      onCenterChanged={map => {
        setState(
          {
            level: map.getLevel(),
            center: {
              lat: map.getCenter().getLat(),
              lng: map.getCenter().getLng(),
            },
          },
          getAddr(state.center.lat, state.center.lng),
        );
      }}>
      <MapMarker position={state.center} image={startImage}></MapMarker>
    </Map>
  );
};

export default KakaoMap;
