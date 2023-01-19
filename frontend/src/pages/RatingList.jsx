import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Header from '../components/Header';

export default function RatingList() {
  return (
    <>
      <Container>
        <Header title={'매너 평가'}></Header>
        <GoodContainer>
          <Title>
            <div>👍 좋았던 점</div>
          </Title>
          <List>
            <Item>
              <div>운전을 잘해요.</div>
              <div>3</div>
            </Item>
            <Item>
              <div>응답이 빨라요.</div>
              <div>2</div>
            </Item>
          </List>
        </GoodContainer>
        <BadContainer>
          <Title>
            <div>👎 아쉬웠던 점</div>
          </Title>
          <List>
            <Item>
              <div>불친절해요.</div>
              <div>2</div>
            </Item>
            <Item>
              <div>운전을 못해요.</div>
              <div>1</div>
            </Item>
          </List>
        </BadContainer>
        <BadContainer></BadContainer>
      </Container>
      <Navbar />
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;
`;
const GoodContainer = styled.div`
  width: 90%;
  height: auto;
  margin-top: 3rem;
`;
const BadContainer = styled.div`
  width: 90%;
  height: auto;
  margin-top: 3rem;
`;
const Title = styled.div`
  width: 100%;
  height: 3rem;
  display: flex;
  align-items: center;
  padding: 0 2rem 0 2rem;
  div {
    font-size: 1.4rem;
    font-weight: 600;
    color: ${props => props.theme.colors.dark_blue};
  }
`;
const List = styled.div`
  width: 100%;
  height: auto;
`;
const Item = styled.div`
  width: 100%;
  height: 4rem;
  box-shadow: 0px 2px 2px -2px ${props => props.theme.colors.light_gray};
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 2rem 0 2rem;
  margin: 0.5rem 0 0.5rem 0;
  div {
    font-size: 1.3rem;
  }
`;
