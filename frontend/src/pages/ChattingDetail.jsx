import styled from 'styled-components';

export default function ChattingDetail() {
  return (
    <>
      <Container>Chatting Detail</Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100px;

  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 768px) {
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  @media only screen and (min-width: 1200px) {
  }
`;
