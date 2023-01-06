import styled from 'styled-components';
import footerLogo from '../images/footerLogo.png';

export default function Footer() {
  return (
    <>
      <Container>
        <ContentContainer>
          <Title>
            <img src={footerLogo} alt="logo" width={90} height={30} />
            <p className="text">언제 어디서든, 카 쉐어링</p>
          </Title>
          <Info>
            <p>
              <b>사업자</b> 야타
            </p>
            <p>
              <b>대표이사</b> 이현지 문재웅 위현우 유형찬 주현주 김채은
            </p>
            <p>
              <b>주소</b> 서울특별시 용산구 한강대로 405
            </p>
          </Info>
          <Bottom>
            <p>
              <b>서비스 약관</b>
            </p>
            <p>
              <b>개인정보 처리방침</b>
            </p>
            <p>
              <b>업데이트 소식</b>
            </p>
            <p>
              <b>문의하기</b>
            </p>
          </Bottom>
        </ContentContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100%;

  @media only screen and (min-width: 768px) {
    div {
      position: absolute;
      bottom: 0;
    }
  }

  @media only screen and (min-width: 1200px) {
    div {
      position: absolute;
      bottom: 0;
    }
  }
`;

const ContentContainer = styled.div`
  width: 100%;
  height: 150px;
  padding: 40px 182px 72px;
  /* position: absolute;
  bottom: 0; */
  color: ${({ theme }) => theme.colors.main_blue};
  background-color: #fff;

  .text {
    margin-left: 20px;
    margin-top: 6px;
  }
`;

const Title = styled.div`
  width: 70%;
  height: 30%;
  margin: 8px 0;
  padding-bottom: 8px;
  display: flex;
  flex-direction: row;
  position: relative;
  top: 20px;
  color: #797e81;
  font-size: 15px;
  border-bottom: 1px solid #d3d6da;
`;
const Info = styled.div`
  display: flex;
  color: #656b71;
  font-size: 13px;
  position: relative;
  top: 100px;
  p {
    margin-right: 10px;
  }
`;
const Bottom = styled.div`
  display: flex;
  color: #656b71;
  font-size: 13px;
  p {
    margin-right: 10px;
  }
`;
