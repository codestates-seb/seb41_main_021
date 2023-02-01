import { useState, useEffect } from 'react';
import styled from 'styled-components';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import { VscAccount } from 'react-icons/vsc';
import Button from '../components/common/Button';
import RatingList from '../components/rating/RatingList';
import { useGetData } from '../hooks/useGetData';
import usePostData from '../hooks/usePostData';
import { useParams, useNavigate } from 'react-router';
import HelpContainer from '../components/Journey/HelpContainer';
import { toast } from 'react-toastify';

export default function DriverRatingAdd(props) {
  const [positiveList, setPositiveList] = useState([]);
  const [negativeList, setNegativeList] = useState([]);
  const [isChecked, setIsChecked] = useState([]);

  const params = useParams();
  const yataId = params.yataId;
  const navigate = useNavigate();

  useEffect(() => {
    useGetData('https://server.yata.kro.kr/api/v1/checklist').then(res => {
      setPositiveList(res.data.data.positiveList);
      setNegativeList(res.data.data.negativeList);
    });
  }, []);

  const ratingHandler = () => {
    const data = {
      checklistIds: isChecked,
    };
    usePostData(`https://server.yata.kro.kr/api/v1/review/${yataId}`, data).then(res => {
      navigate('/journey-state');
      toast.success('매너 평가하기 성공');
    });
  };

  return (
    <>
      <Header title={'운전자 매너 평가'}></Header>
      <Container>
        <HelpContainer>운전자의 매너를 평가해주세요 !</HelpContainer>
        <RatingList
          isChecked={isChecked}
          setIsChecked={setIsChecked}
          title={'👍 좋았던 점을 선택해 주세요 !'}
          Items={positiveList}
        />
        <RatingList
          isChecked={isChecked}
          setIsChecked={setIsChecked}
          title={' 👎 아쉬웠던 점을 선택해 주세요 !'}
          Items={negativeList}
        />
        <FinishBtn onClick={ratingHandler}>완료</FinishBtn>
      </Container>
      <NavBar />
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;
`;
const ProfileCotainer = styled.div`
  width: 90%;
  padding: 1rem;
  display: flex;
  align-items: center;
`;
const Profile = styled.div`
  width: 100%;
  height: 100%;
  padding: 1rem;
  display: flex;
  align-items: center;

  svg {
    font-size: 3.5rem;
    color: #26264c;
  }
`;

const Info = styled.div`
  width: 100%;
  height: 100%;
  padding-left: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: space-around;

  .name {
    font-size: 1.4rem;
    font-weight: bold;
    color: ${props => props.theme.colors.dark_blue};
    position: relative;
    top: 0.5rem;
  }
`;

const FinishBtn = styled(Button)`
  width: 40%;
  margin-top: 1rem;
  margin-bottom: 3rem;
`;
