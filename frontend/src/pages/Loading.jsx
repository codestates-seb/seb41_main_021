import styled, { keyframes } from 'styled-components';
import { TbLoader } from 'react-icons/tb';

export default function Loading() {
  return (
    <>
      <Container>
        <Spinner>
          <TbLoader />
        </Spinner>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const spin = keyframes`
    0% {
        transform: rotate(0deg);
      }
    100% {
        transform: rotate(360deg);
      }
  `;

const Spinner = styled.div`
  margin: 4rem;
  animation: ${spin} infinite 5s linear;

  svg {
    font-size: 8rem;
    color: gray;
  }
`;
