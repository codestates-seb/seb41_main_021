import styled from 'styled-components';
import { NavLink } from 'react-router-dom';
import { AiOutlineHome, AiOutlineComment, AiOutlineBell, AiOutlineUser, AiOutlineCar } from 'react-icons/ai';
import { TbSteeringWheel } from 'react-icons/tb';
import { FaRegHandshake } from 'react-icons/fa';

export default function Navbar() {
  return (
    <>
      <Container>
        <NavContainer>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/">
            <AiOutlineHome />
            <p>홈</p>
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/taeoondalist">
            <TbSteeringWheel />
            <p>태웁니다</p>
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/tabnidalist">
            <AiOutlineCar />
            <p>탑니다</p>
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/registerlist">
            <FaRegHandshake />
            <p>요청 내역</p>
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/mypage">
            <AiOutlineUser />
            <p>마이페이지</p>
          </NavLink>
        </NavContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  div {
    background-color: #fff;
    position: absolute;
    bottom: 0;
    box-shadow: 0 -5px 10px -8px lightgrey;
  }
`;

const NavContainer = styled.div`
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-grow: 1;
  svg {
    font-size: 30px;
  }

  a {
    display: flex;
    flex-direction: column;
    align-items: center;

    color: ${({ theme }) => theme.colors.main_blue};

    &:hover {
      color: ${({ theme }) => theme.colors.darker_blue};
    }
    &.active {
      color: ${({ theme }) => theme.colors.dark_blue};
    }

    p {
      margin-top: 0.3rem;
    }
  }

  @media only screen and (min-width: 470px) {
    width: 470px;
    display: flex;
    align-items: center;
    justify-content: space-evenly;
  }
`;
