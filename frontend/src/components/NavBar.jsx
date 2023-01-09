import styled from 'styled-components';
import { NavLink } from 'react-router-dom';
import { AiOutlineHome, AiOutlineComment, AiOutlineBell, AiOutlineUser } from 'react-icons/ai';

export default function Navbar() {
  return (
    <>
      <Container>
        <NavContainer>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/">
            <AiOutlineHome className="home-icon" />
          </NavLink>
          {/* <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/chattinglist">
            <AiOutlineComment className="msg-icon" />
          </NavLink> */}
          {/* 임시용 fath login */}
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/login">
            <AiOutlineComment className="msg-icon" />
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/notification">
            <AiOutlineBell className="noti-icon" />
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/mypage">
            <AiOutlineUser className="mypage-icon" />
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
    color: ${({ theme }) => theme.colors.main_blue};

    &:hover {
      color: ${({ theme }) => theme.colors.darker_blue};
    }
    &.active {
      color: ${({ theme }) => theme.colors.dark_blue};
    }
  }

  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 470px) {
    width: 470px;
    display: flex;
    align-items: center;
    justify-content: space-evenly;
  }
`;
