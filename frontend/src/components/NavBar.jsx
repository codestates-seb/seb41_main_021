import styled from 'styled-components';
import { NavLink } from 'react-router-dom';
import { AiOutlineUser } from 'react-icons/ai';
import { FaCarSide } from 'react-icons/fa';
import { RiSteeringLine } from 'react-icons/ri';
import { TbHeartHandshake } from 'react-icons/tb';

export default function Navbar() {
  return (
    <>
      <Container>
        <NavContainer>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/taeoonda-list">
            <RiSteeringLine />
            <p>태웁니다</p>
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/tabnida-list">
            <FaCarSide />
            <p>탑니다</p>
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/register-list">
            <TbHeartHandshake />
            <p>요청 내역</p>
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/my-page">
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
  height: 5rem;
  display: flex;
  align-items: center;

  svg {
    font-size: 2rem;
  }

  a {
    flex: 1;
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
      font-size: 1rem;
    }
  }

  @media only screen and (min-width: 470px) {
    width: 470px;
    display: flex;
    align-items: center;
  }
`;
