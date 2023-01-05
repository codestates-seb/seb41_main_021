import styled from 'styled-components';
import NavBar from '../components/NavBar';

export default function ChattingList() {
  return (
    <>
      <Container>
        <NavBar/>
        <ChattingListContainer>
          Messages
        </ChattingListContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;

  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 768px) {
    width: 100%;
    height: 100vh;
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  @media only screen and (min-width: 1200px) {
    width: 100%;
    height: 100vh;
  }
`;

const ChattingListContainer = styled.div`
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
`