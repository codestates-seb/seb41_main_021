import styled from 'styled-components';
import Input from '../components/common/Input';
import Button from './common/Button';
export default function DestinationInput() {
  return (
    <>
      <InputContainer>
        <Input label="출발지"></Input>
        <Input label="도착지"></Input>
        <AddButton>검색하기</AddButton>
      </InputContainer>
    </>
  );
}

const InputContainer = styled.form`
  width: 80%;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: white;
  padding: 0.5rem;
  border-radius: 10px;
  background-color: ${props => props.theme.colors.dark_blue};
`;

const AddButton = styled(Button)`
  width: 100%;
  height: 3rem;
  margin: 0.5rem 0;
`;
