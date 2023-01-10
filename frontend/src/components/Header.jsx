import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { AiOutlineArrowLeft } from 'react-icons/ai';

export default function Header() {
  const navigate = useNavigate();

  return (
    <>
      <Container>
        <BackButtonContainer>
          <AiOutlineArrowLeft onClick={() => navigate(-1)} />
          <h1>Header</h1>
        </BackButtonContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 50px;
`;

const BackButtonContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;

  div {
    background-color: #fff;
    position: absolute;
    top: 0;
    box-shadow: 0 -5px 10px -8px lightgrey;
  }

  svg {
    font-size: 2rem;
    cursor: pointer;
    margin: 1rem;
  }

  @media only screen and (min-width: 470px) {
    width: 470px;
  }
`;
