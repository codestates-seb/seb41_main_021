import React, { useEffect, useState } from 'react';
import { MapMarker, Map } from 'react-kakao-maps-sdk';

const { kakao } = window;

const KakaoMap = props => {
  const { searchPlace, setPlaces } = props;

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

  return (
    <Map center={state.center} style={{ width: '100%', height: '100%' }} level={5} onCreate={setMap}>
      <MapMarker position={state.center}>
        <div style={{ padding: '5px', color: '#000' }}>{state.errMsg ? state.errMsg : '현재 위치'}</div>
      </MapMarker>
    </Map>
  );
};

export default KakaoMap;
