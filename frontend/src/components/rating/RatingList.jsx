import styled from 'styled-components';
import RatingItem from './RatingItem';
import { useParams } from 'react-router';
import { useState, useEffect } from 'react';
import { useGetData } from '../../hooks/useGetData';

const RatingList = props => {
  const { title, Items, isChecked, setIsChecked } = props;
  const params = useParams();
  const email = params.email;

  const [loading, setLoading] = useState(true);
  const [data, setData] = useState([]);

  useEffect(() => {
    if (email) {
      useGetData(`https://server.yata.kro.kr/api/v1/review/${email}`).then(res => {
        console.log(res.data.data);
        setData(res.data.data, setLoading(false));
      });
    } else {
      return;
    }
  }, []);

  return (
    <RatingContainer>
      <TitleContainer>
        <div className="title"> {title}</div>
      </TitleContainer>
      <ListContainer>
        {Items.map(el => {
          return (
            <RatingItem
              key={el.checklistId}
              id={el.checklistId}
              text={el.checkContent}
              isChecked={isChecked}
              setIsChecked={setIsChecked}
            />
          );
        })}
      </ListContainer>
    </RatingContainer>
  );
};

const RatingContainer = styled.div`
  width: 100%;
  padding: 1rem;
  border-top: 5px solid #f6f6f6;
`;
const TitleContainer = styled.div`
  width: 100%;
  margin-top: 1rem;
  display: flex;
  justify-content: center;

  .title {
    font-size: 1.3rem;
    color: ${props => props.theme.colors.dark_blue};
    font-weight: bold;
  }
`;

const ListContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 1rem;
`;

export default RatingList;
