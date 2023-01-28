import { useState } from 'react';
import styled, { css } from 'styled-components';
import { MdDone } from 'react-icons/md';

const RatingItem = props => {
  const { text, id, isChecked, setIsChecked } = props;
  const [check, setCheck] = useState(false);

  const eventHandler = () => {
    !check ? setIsChecked([...isChecked, id]) : setIsChecked([...isChecked].filter(el => id !== el));
    setCheck(!check);
  };

  return (
    <Item onClick={eventHandler}>
      <CheckCircle check={check}>{check && <MdDone />}</CheckCircle>
      {text}
    </Item>
  );
};

const Item = styled.div`
  width: 100%;
  margin: 1.2rem;
  font-size: 1.1rem;
  display: flex;
  align-items: center;
`;

const CheckCircle = styled.div`
  width: 1.5rem;
  height: 1.5rem;
  border-radius: 1.5rem;
  border: 1px solid #ced4da;
  font-size: 1.7rem;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 1.5rem;
  cursor: pointer;

  ${props =>
    props.check &&
    css`
      border: 1px solid ${props => props.theme.colors.darker_blue};
      color: ${props => props.theme.colors.darker_blue};
    `}
`;
export default RatingItem;
