import styled from 'styled-components';
import { IoEllipseOutline, IoEllipse } from 'react-icons/io5';
export default function DestinationInput() {
  // const startHan = e => {
  //   setStart(prev => ({
  //     ...prev,
  //     address: e.target.value,
  //   }));
  // };

  return (
    <IputContainer>
      <InputContent>
        <InputForm>
          <IoEllipseOutline />
          <StyleInput placeholder="출발지"></StyleInput>
        </InputForm>
        <InputForm>
          <IoEllipse />
          <StyleInput placeholder="도착지"></StyleInput>
        </InputForm>
      </InputContent>
    </IputContainer>
  );
}

const IputContainer = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  padding: 2rem 1rem;
  border-bottom: 5px solid #f6f6f6;
`;

const InputContent = styled.form`
  width: 95%;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: white;
  padding: 0 1rem;
  border: 1px solid ${props => props.theme.colors.light_gray};
  border-radius: 1rem;
  /* background-color: ${props => props.theme.colors.dark_blue}; */
  svg {
    color: black;
    font-weight: bold;
    font-size: 0.5rem;
  }
  div:last-child {
    svg {
      color: #ca4943;
      font-weight: bold;
      font-size: 0.5rem;
    }
  }
`;

const InputForm = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  &:first-child {
    border-bottom: 1px solid ${props => props.theme.colors.light_gray};
  }
`;

const StyleInput = styled.input`
  width: 100%;
  padding: 1rem 1.25rem;
  :focus {
    outline: none;
  }
`;
