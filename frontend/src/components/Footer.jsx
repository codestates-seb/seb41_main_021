import styled from 'styled-components';

export default function Footer() {
  return (
    <>
      <Container>
        <ContentContainer>
            <p>언제 어디서든, 카 쉐어링</p>
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
    height: 60px;
    margin: 50px 0;
    position: absolute;
    bottom: 0;
    color: gray;
    background-color: #f7f7f7;
`