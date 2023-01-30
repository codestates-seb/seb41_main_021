import styled from 'styled-components';
import Header from '../components/Header';
import { IoIosCheckmarkCircleOutline } from 'react-icons/io';
import { BiWon } from 'react-icons/bi';
import { BsCreditCard, BsCalendarCheck } from 'react-icons/bs';
import Button from '../components/common/Button';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useGetData } from '../hooks/useGetData';
import { useEffect, useState } from 'react';
import { dateFormat } from '../components/common/DateFormat';

// /api/v1/payments/toss/success?paymentKey=paymentKey&orderId=d3134662-748f-44e7-a76c-3be4bccc7528&amount=15000
const PointSuccess = () => {
  const [data, setData] = useState([]);
  const navigate = useNavigate();
  const [SearchParams, setSearchParams] = useSearchParams();
  const paymentKey = SearchParams.get('paymentKey'),
    orderId = SearchParams.get('orderId'),
    amount = SearchParams.get('amount');
  useEffect(() => {
    useGetData(`/api/v1/payments/toss/success?paymentKey=${paymentKey}&orderId=${orderId}&amount=${amount}`).then(res =>
      setData(res.data.data),
    );
  });
  return (
    <>
      <Header title="포인트 충전 "></Header>
      <Container>
        <IoIosCheckmarkCircleOutline></IoIosCheckmarkCircleOutline>
        <SuccessMessage>포인트 충전 성공!</SuccessMessage>
        <InfoContainer>
          <Info>
            <BiWon></BiWon>충전 금액: {amount}원
          </Info>
          <Info>
            <BsCreditCard></BsCreditCard>결제 수단: {data.method}
          </Info>
          <Info>
            <BsCalendarCheck></BsCalendarCheck>충전 일시: {dateFormat(data.approvedAt)}
          </Info>
        </InfoContainer>
        <MypageButton
          onClick={() => {
            navigate('/my-page');
          }}>
          마이페이지로 이동
        </MypageButton>
        <HistoryButton
          onClick={() => {
            navigate('/point-history');
          }}>
          충전 내역 보기
        </HistoryButton>
      </Container>
    </>
  );
};

const Container = styled.div`
  flex: 1;
  display: flex;
  align-items: center;
  flex-direction: column;

  svg {
    color: ${props => props.theme.colors.main_blue};
    font-size: 6rem;
    margin-top: 8rem;
  }
`;

const SuccessMessage = styled.div`
  font-size: 1.3rem;
  padding: 2rem;
  font-weight: bold;
`;

const InfoContainer = styled.div`
  padding: 3rem 1rem 6.5rem 1rem;
  display: flex;
  flex-direction: column;
  width: 90%;
`;

const Info = styled.div`
  font-size: 1.2rem;
  margin-bottom: 1.5rem;

  svg {
    font-size: 1.2rem;
    color: black;
    margin-right: 1rem;
    margin-top: 0;
  }
`;

const MypageButton = styled(Button)`
  width: 50%;
  background-color: #ffffff;
  color: ${props => props.theme.colors.dark_gray};
  border: 1px solid ${props => props.theme.colors.light_gray}; ;
`;

const HistoryButton = styled(Button)`
  width: 50%;
  margin-top: 2rem;
`;

export default PointSuccess;
