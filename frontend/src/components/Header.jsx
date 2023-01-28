import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { AiOutlineArrowLeft } from 'react-icons/ai';
import { useDispatch, useSelector } from 'react-redux';
import { setIsFilled } from '../redux/slice/DestinationSlice';

const Header = props => {
  const { title } = props;
  const navigate = useNavigate();

  const dispatch = useDispatch();
  const des = useSelector(state => {
    return state.destination;
  });

  const prev = () => {
    if (des.isFilled) {
      dispatch(setIsFilled({ isFilled: false }));
    } else {
      navigate(-1);
    }
  };

  return (
    <>
      <Container>
        <div className="content-container">
          <div className="back-btn-container">
            <AiOutlineArrowLeft onClick={prev} />
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
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 5rem;
  box-shadow: 0 5px 10px -8px lightgrey;

  @media only screen and (min-width: 470px) {
    width: 470px;
  }
  .content-container {
    flex: 1;
    display: flex;
    align-items: center;
  }

  .back-btn-container {
    position: absolute;
    svg {
      font-size: 2rem;
      cursor: pointer;
      margin: 1rem;
    }
  }

  .title-container {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;

    h1 {
      font-size: 1.3rem;
    }
  }
`;

export default Header;
