import React, { useEffect, useState } from 'react';
import { MapMarker, Map } from 'react-kakao-maps-sdk';

const { kakao } = window;

const KakaoMap = props => {
  const { searchPlace, setPlaces } = props;

  useEffect(() => {
    const ps = new kakao.maps.services.Places();

    ps.keywordSearch(searchPlace, placesSearchCB);

    function placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        setPlaces(data);
      }
    }
  }, [searchPlace]);

  return (
    <Map center={{ lat: 33.5563, lng: 126.79581 }} style={{ width: '100%', height: '100%' }}>
      <MapMarker position={{ lat: 33.55635, lng: 126.795841 }}>
        <div style={{ color: '#000' }}>현재 위치</div>
      </MapMarker>
    </Map>
  );
};

export default KakaoMap;
