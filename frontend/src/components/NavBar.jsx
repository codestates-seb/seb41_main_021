import styled from 'styled-components';
import { NavLink } from "react-router-dom";
import {AiOutlineHome, AiOutlineComment, AiOutlineBell, AiOutlineUser} from "react-icons/ai";
import Logo from '../images/Full_Logo.svg'

export default function Navbar() {
  return (
    <>
      <Container>
        <NavContainer>
          <img src={Logo} alt="logo"/>
          <NavLink className={({ isActive }) => (isActive ? "active" : "not")} to="/">
            <AiOutlineHome className='home-icon'/>
            <span className='home-txt'>Home</span>
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/chattinglist">
            <AiOutlineComment className="msg-icon" />
            <span className="msg-txt">Messages</span>
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/notification">
            <AiOutlineBell className="noti-icon" />
            <span className="noti-txt">Notification</span>
          </NavLink>
          <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/mypage">
            <AiOutlineUser className="mypage-icon" />
            <span className="mypage-txt">My Page</span>
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

  @media only screen and (min-width: 768px) {
    div {
      position: absolute;
      top: 0;
      box-shadow: 0 5px 10px -8px lightgrey;
    }

  }

  @media only screen and (min-width: 1200px) {
    div {
      position: absolute;
      top: 0;
    }
  }
`;

const NavContainer = styled.div`
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-around;

  img {
    display: none;
  }

  svg {
    font-size: 30px;
  }

  a {
    color: ${({ theme }) => theme.colors.main_blue};
  }

		&:hover {
      color: ${({ theme }) => theme.colors.darker_blue};
		}
		&.active {
      color: ${({ theme }) => theme.colors.dark_blue};
		}
  }

    .home-txt, .msg-txt, .noti-txt, .mypage-txt {
      display: none;
  }

  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 768px) {
    display: flex;
    align-items: center;
    justify-content: space-evenly; 
    
    .home-icon, .msg-icon, .noti-icon, .mypage-icon {
      display: none;
    }
    
    .home-txt, .msg-txt, .noti-txt, .mypage-txt {
      display: block;
    }

    img {
      display: block;
      width: 170px;
    }
  }

  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  /* @media only screen and (min-width: 1200px) {
    display: flex;
    align-items: center;
    justify-content: space-around; 
    
    .home-icon, .msg-icon, .noti-icon, .mypage-icon {
      display: none;
    }
    
    .home-txt, .msg-txt, .noti-txt, .mypage-txt {
      display: block;
    }
  } */
`;
