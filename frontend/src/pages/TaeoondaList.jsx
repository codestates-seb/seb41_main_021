import styled from 'styled-components';

export default function TaeoondaList() {
  return (
    <>
      <Container>Taeoonda List</Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100px;
  background-color: pink;

  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 768px) {
    background-color: red;
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  @media only screen and (min-width: 1200px) {
    background-color: blue;
  }
`;
