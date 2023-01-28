import styled from 'styled-components';
import { VscAccount } from 'react-icons/vsc';
import { useState } from 'react';
import Button from '../common/Button';
import { useNavigate, useParams } from 'react-router';
import { useSearchParams } from 'react-router-dom';

export default function MemberListItem(props) {
  const { state, nickname, yataMemberId } = props;
  const navigate = useNavigate();
  const params = useParams();
  const yataId = params.yataId;

  const [isPay, setisPay] = useState(true);
  const [searchParams, setSearchParams] = useSearchParams();

  const reviewHandler = () => {
    setSearchParams(`yataMemberId: '${yataMemberId}'`);
    navigate(`/rating-add-passenger/${yataId}?yataMemberId=${yataMemberId}`);
  };

  return (
    <Container>
      <ProfileContainer>
        <VscAccount />
        <Username>{nickname}</Username>
        {isPay ? (
          <ReviewBtn onClick={reviewHandler}>리뷰 남기기</ReviewBtn>
        ) : (
          <TagContainer state={state}>{state === true ? '결제 완료' : '결제 대기'}</TagContainer>
        )}
      </ProfileContainer>
    </Container>
  );
}

const Container = styled.div`
  width: 100%;
`;

const ProfileContainer = styled.div`
  height: 3rem;
  display: flex;
  align-items: center;

  svg {
    margin-right: 0.5rem;
    font-size: 1.5rem;
  }
`;

const Username = styled.div`
  font-size: 1.1rem;
`;

const TagContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 0.5rem;
  padding: 0.5rem;
  height: 1.5rem;
  color: white;
  border-radius: 0.2rem;
  font-size: 0.9rem;

  background-color: ${props => (props.state === true ? props.theme.colors.main_blue : props.theme.colors.light_gray)};
`;

const ReviewBtn = styled(Button)`
  padding: 5px 10px;
  font-size: 0.9rem;
`;
