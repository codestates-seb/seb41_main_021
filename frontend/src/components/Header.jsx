import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { AiOutlineArrowLeft } from 'react-icons/ai';

const Header = props => {
  const { title } = props;
  const navigate = useNavigate();

  return (
    <>
      <Container>
        <div className="content-container">
          <div className="back-btn-container">
            <AiOutlineArrowLeft onClick={() => navigate(-1)} />
          </div>
          <div className="title-container">
            <h1>{title}</h1>
          </div>
        </div>
      </Container>
    </>
  );
};

const Container = styled.div`
  width: 100%;
  height: 5rem;
  box-shadow: 0 5px 10px -8px lightgrey;

  .content-container {
    width: 100%;
    height: 5rem;
    position: absolute;
    top: 0;
    display: flex;
    align-items: center;
  }

  .back-btn-container {
    svg {
      font-size: 2rem;
      cursor: pointer;
      margin: 1rem;
    }

    @media only screen and (min-width: 470px) {
      width: 470px;
    }
  }

  .title-container {
    width: 100%;
    height: 100%;
    position: absolute;
    display: flex;
    align-items: center;
    justify-content: center;

    h1 {
      font-size: 1.3rem;
    }
  }
`;

export default Header;
