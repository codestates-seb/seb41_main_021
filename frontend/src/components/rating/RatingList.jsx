import styled from 'styled-components';
import RatingItem from './RatingItem';

const RatingList = props => {
  const { title, Items } = props;
  return (
    <RatingContainer>
      <TitleContainer>
        <div className="title"> {title}</div>
      </TitleContainer>
      <ListContainer>
        {Items.map(el => {
          return <RatingItem key={el.id} text={el.text} />;
        })}
      </ListContainer>
    </RatingContainer>
  );
};

const RatingContainer = styled.div`
  width: 100%;
  height: 30%;
  padding: 1rem 2rem 1rem 2rem;
  margin-top: 1rem;
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
