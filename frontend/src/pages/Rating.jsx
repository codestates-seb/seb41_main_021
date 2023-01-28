import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Header from '../components/Header';
import useGetData from '../hooks/useGetData';
import { useSelector } from 'react-redux';

export default function Rating() {
  const [positiveList, setPositiveList] = useState([]);
  const [negativeList, setNegativeList] = useState([]);

  const info = useSelector(state => {
    return state.user;
  });

  useEffect(() => {
    useGetData(`https://server.yata.kro.kr/api/v1/review/${info.email}`).then(res => {
      if (res.data.data.length !== 0) {
        if (res.data.checklistResponse.checkpn === true) {
          setPositiveList(res.data);
        } else {
          setNegativeList(res.data);
        }
      } else {
        return;
      }
    });
  }, []);

  return (
    <>
      <Header title={'매너 평가'}></Header>
      <Container>
        <GoodContainer>
          <Title>
            <div>👍 좋았던 점</div>
          </Title>
          <List>
            {positiveList.map(el => {
              return (
                <Item key={el.checklistId}>
                  <div>{el.checkContent}</div>
                  <div>3</div>
                </Item>
              );
            })}
          </List>
        </GoodContainer>
        <BadContainer>
          <Title>
            <div>👎 아쉬웠던 점</div>
          </Title>
          <List>
            {negativeList.map(el => {
              return (
                <Item key={el.checklistId}>
                  <div>{el.checkContent}</div>
                  <div>3</div>
                </Item>
              );
            })}
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
  margin-top: 2rem;
  width: 90%;
  height: auto;
`;
const BadContainer = styled.div`
  margin-top: 2rem;
  width: 90%;
  height: auto;
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
