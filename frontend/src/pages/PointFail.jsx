import styled from 'styled-components';
import Header from '../components/Header';
import { IoIosCloseCircleOutline } from 'react-icons/io';
import Button from '../components/common/Button';
import { useNavigate } from 'react-router-dom';

const PointFail = () => {
  const navigate = useNavigate();
  return (
    <>
      <Header title="포인트 충전 "></Header>
      <Container>
        <IoIosCloseCircleOutline></IoIosCloseCircleOutline>
        <SuccessMessage>포인트 충전 실패 ㅠㅜ 흑</SuccessMessage>
        <MypageButton
          onClick={() => {
            navigate('/my-page');
          }}>
          마이페이지로 이동
        </MypageButton>
        <HistoryButton
          onClick={() => {
            navigate('/purchase');
          }}>
          충전 페이지로 이동
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
    color: ${props => props.theme.colors.gray};
    font-size: 6rem;
    margin-top: 8rem;
  }
`;

const InfoContainer = styled.div``;

const SuccessMessage = styled.div`
  font-size: 1.3rem;
  padding: 2rem;
  font-weight: bold;
`;

const Price = styled.div`
  font-size: 1.2rem;
  margin-top: 5rem;
  margin-bottom: 15rem;
`;

const MypageButton = styled(Button)`
  margin-top: 16.5rem;
  width: 50%;
  background-color: #ffffff;
  color: ${props => props.theme.colors.dark_gray};
  border: 1px solid ${props => props.theme.colors.light_gray}; ;
`;

const HistoryButton = styled(Button)`
  width: 50%;
  margin-top: 2rem;
`;

export default PointFail;
