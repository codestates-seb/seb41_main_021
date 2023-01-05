import styled from 'styled-components';

export default function Navbar() {
  return (
    <>
      <Container>Navbar</Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 50px;
  background-color: gray;
  position: absolute;
  bottom: 0;

  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 768px) {
    width: 100%;
    height: 50px;
    background-color: gray;
    position: absolute;
    top: 0;
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  @media only screen and (min-width: 1200px) {
    width: 100%;
    height: 50px;
    background-color: gray;
    position: absolute;
    top: 0;
  }
`;