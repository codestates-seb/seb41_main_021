import styled, { css } from 'styled-components';

const CircleButton = styled.button`
  background: ${props => props.theme.colors.main_blue};
  &:hover {
    background: ${props => props.theme.colors.dark_blue};
  }
  &:active {
    background: ${props => props.theme.colors.darker_blue};
  }
  z-index: 5;
  cursor: pointer;
  width: 5rem;
  height: 5rem;
  display: block;
  align-items: center;
  justify-content: center;
  font-size: 3.5rem;
  position: absolute;
  right: -1rem;
  bottom: 4rem;
  transform: translate(-50%, 50%);
  color: white;
  border-radius: 50%;
  border: none;
  outline: none;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.125s all ease-in;
  ${props =>
    props.open &&
    css`
      background: #ff6b6b;
      &:hover {
        background: #ff8787;
      }
      &:active {
        background: #fa5252;
      }
      transform: translate(-50%, 50%) rotate(45deg);
    `}
`;

export default CircleButton;
