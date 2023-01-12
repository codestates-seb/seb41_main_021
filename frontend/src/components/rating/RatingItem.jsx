import { useState } from 'react';
import styled, { css } from 'styled-components';
import { MdDone } from 'react-icons/md';

const RatingItem = props => {
  const { text } = props;
  const [check, setCheck] = useState(false);
  return (
    <Item onClick={() => setCheck(!check)}>
      <CheckCircle check={check}>{check && <MdDone />}</CheckCircle>
      {text}
    </Item>
  );
};

const Item = styled.div`
  width: 100%;
  height: 20%;
  display: flex;
  align-items: center;
  font-size: 1.1rem;
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
