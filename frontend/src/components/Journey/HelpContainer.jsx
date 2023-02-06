import styled from 'styled-components';
import { FcIdea } from 'react-icons/fc';

export default function HelpContainer({ children }) {
  return (
    <>
      <Container>
        <FcIdea />
        {children}
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 95%;
  background-color: #f1f1ef;
  color: #37352f;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 1rem;
  border-radius: 0.5rem;
  margin: 1rem 0;

  svg {
    font-size: 1.4rem;
    margin-right: 0.3rem;
    margin-bottom: 0.3rem;
  }
`;
