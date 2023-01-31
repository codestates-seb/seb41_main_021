import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Header from '../components/Header';
import CircleButton from '../components/common/CircleButton';
import DestinationInput from '../components/DestinationInput';
import { useNavigate } from 'react-router-dom';
import { MdAdd } from 'react-icons/md';
import ListItemView from '../components/ListItemView';
import { useGetData } from '../hooks/useGetData';
import { useDispatch } from 'react-redux';
import { clearAll } from '../redux/slice/DestinationSlice';

const { kakao } = window;

export default function TabnidaList() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [list, setList] = useState([]);
  const [target, setTarget] = useState(null);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [hasNext, setHasNext] = useState(false);

  // const [start, setStart] = useState({
  //   lat: '',
  //   lng: '',
  //   address: '',
  // });

  const fetch = url => {
    useGetData(url).then(res => {
      setList(prev => prev.concat(res.data.data));
      setLoading(false);
      setPage(prev => prev + 1);
      // setHasNext(res.data.sliceInfo.hasNext);
    });
  };
  const add = () => {
    setOpen(!open);
    navigate('/tabnida-add');
  };

  // const temp = () => {
  //   console.log(page);
  //   console.log(list);
  //   console.log(hasNext);
  //   console.log(start);
  // };
  // function getAddr(lat, lng) {
  //   // 주소-좌표 변환 객체를 생성합니다
  //   let geocoder = new kakao.maps.services.Geocoder();

  //   let coord = new kakao.maps.LatLng(lat, lng);
  //   let callback = function (result, status) {
  //     if (status === kakao.maps.services.Status.OK) {
  //       setStart(prev => ({
  //         ...prev,
  //         address: result[0].address.address_name,
  //       }));
  //     }
  //   };
  //   geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
  // }
  useEffect(() => {
    // if (navigator.geolocation) {
    //   // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    //   navigator.geolocation.getCurrentPosition(position => {
    //     setStart(
    //       {
    //         lat: position.coords.latitude, // 위도
    //         lng: position.coords.longitude, // 경도
    //       },
    //       getAddr(position.coords.latitude, position.coords.longitude),
    //     );
    //   });
    //   setTimeout(() => {
    //     fetch(
    //       `/api/v1/yata/search/location?startLon=${start.lng}&startLat=${start.lat}&startAddress=${start.address}&distance=1&yataStatus=YATA_NATA&page=${page}&size=100`,
    //     );
    //   }, 2000);
    // } else {
    fetch(`/api/v1/yata/search/location?distance=1&yataStatus=YATA_NATA&page=${page}&size=100`);
    // }
    dispatch(clearAll());
  }, []);

  // useEffect(() => {
  //   let observer;
  //   if (target) {
  //     const onIntersect = async ([entry], observer) => {
  //       if (entry.isIntersecting) {
  //         observer.unobserve(entry.target);
  //         fetch();
  //         console.log('관측');
  //         setTimeout(() => {
  //           observer.observe(entry.target);
  //         }, 1000);
  //       }
  //     };
  //     observer = new IntersectionObserver(onIntersect, { threshold: 1 });
  //     observer.observe(target);
  //   }
  //   return () => observer && observer.disconnect();
  // }, [target]);

  return (
    <>
      {loading || (
        <>
          <Header title="탑니다" />
          <Container>
            <DestinationInput />
            <ListItemView list={list}>
              <div ref={setTarget}></div>
            </ListItemView>
            <CircleButton onClick={add} open={open}>
              <MdAdd />
            </CircleButton>
          </Container>
          <Navbar />
        </>
      )}
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
`;
