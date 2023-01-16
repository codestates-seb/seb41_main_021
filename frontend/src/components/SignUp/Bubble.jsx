import styled from 'styled-components';

const Bubble = ({ type }) => {
  const typeKey = type;
  const errors = {
    password: '영문, 숫자, 특수문자 포함 8글자 이상이어야 합니다.',
    passwordCheck: '비밀번호가 일치하지 않습니다.',
    name: '2글자 이상 5글자 이하로 입력해주세요.',
    number: '올바른 전화번호 형식이 아닙니다.',
    email: '올바른 이메일 형식이 아닙니다',
  };
  return <StyledBubble className="bubble">{errors[typeKey]}</StyledBubble>;
};

const StyledBubble = styled.div`
  max-width: 10rem;
  min-width: 10rem;
  width: 10rem;
  height: 4rem;
  text-align: center;
  line-height: 1.15rem;
  background-color: ${props => props.theme.colors.main_blue};
  border: ${props => props.theme.colors.dark_blue} 1px solid;
  color: white;
  padding: 5px;
  border-radius: 5px;
  position: relative;
  top: 3rem;
  right: 8rem;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1;
`;

export default Bubble;
