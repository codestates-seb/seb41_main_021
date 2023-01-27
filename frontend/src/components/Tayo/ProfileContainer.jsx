import styled from 'styled-components';
import { IoIosArrowRoundForward, IoIosArrowForward } from 'react-icons/io';
import { VscAccount } from 'react-icons/vsc';

export default function ProfileContainer(props) {
  const { data } = props;
  return (
    <ContentContainer>
      <ProfileInfoContainer>
        <h2>작성자 정보</h2>
        <ProfileInfo>
          <VscAccount />
          <ProfileText>
            <div className="username">{data.nickName}</div>
            <div>기름통 레벨 {70}L</div>
          </ProfileText>
        </ProfileInfo>
      </ProfileInfoContainer>
      <IoIosArrowForward />
    </ContentContainer>
  );
}

const ContentContainer = styled.div`
  width: 95%;
  margin-top: 1rem;
  padding: 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-radius: 0.3rem;
  background-color: #f6f6f6;
  color: #202020;

  svg {
    font-size: 1.2rem;
    margin-right: 0.5rem;
  }

  .username {
    font-weight: bold;
    margin-bottom: 0.3rem;
  }
`;

const ProfileInfoContainer = styled.div`
  display: flex;
  flex-direction: column;

  svg {
    font-size: 2rem;
  }
`;

const ProfileInfo = styled.div`
  margin: 1rem 0;
  display: flex;
  align-items: center;
`;

const ProfileText = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 0.3rem;
`;
