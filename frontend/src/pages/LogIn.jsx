import { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import NavBar from '../components/NavBar';
import Button from '../components/common/Button';
import Input from '../components/common/Input';
import Logo from '../images/Logo.svg';

export default function Login() {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [isValidID, setIsValidID] = useState(true);
  const [isValidPW, setIsValidPW] = useState(true);

  const isLogin = false;
  const handleSubmit = e => {
    e.preventDefault();

    if (id === '') setIsValidID(false);
    else setIsValidID(true);
    if (password === '') setIsValidPW(false);
    else setIsValidPW(true);
    if (id === '' || password === '') return console.log('fail');

    console.log('success');
    console.log(id, password);
  };
  return (
    <>
      {isLogin || (
        <>
          <NavBar />
          <Container>
            <Contents>
              <Img src={Logo} alt="logo" />
              <LoginForm onSubmit={handleSubmit}>
                <IdWrapper>
                  <Input label="아이디" state={id} setState={setId} />
                  {isValidID || <ErrorMsg>아이디를 입력해주세요</ErrorMsg>}
                </IdWrapper>
                <PwWrapper>
                  <Input label="비밀번호" state={password} setState={setPassword} type="password" />
                  {isValidPW || <ErrorMsg>비밀번호를 입력해주세요</ErrorMsg>}
                </PwWrapper>
                <SubmitButton>로그인</SubmitButton>
              </LoginForm>
              <LinkToSign message="회원이 아니신가요?" link="/signup" linkText="회원가입" />
            </Contents>
          </Container>
        </>
      )}
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 768px) {
    background-color: red;
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  /* @media only screen and (min-width: 1200px) {
    background-color: blue;
  } */
`;

const Img = styled.img`
  width: 100%;
`;

const Contents = styled.div`
  width: 60%;
  max-width: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  @media screen and (min-width: 800px) {
    margin-top: 10rem;
  }
`;

const LoginForm = styled.form`
  width: 100%;
  min-width: 22rem;
  height: 300px;
  display: grid;
`;

const IdWrapper = styled.div``;
const PwWrapper = styled.div``;
const ErrorMsg = styled.p`
  color: #de4f55;
  padding: 0.4rem;
`;

const SubmitButton = styled(Button)`
  height: 3rem;
  margin: 2rem 0;
`;

const LinkToSign = props => {
  const { message, link, linkText } = props;
  return (
    <StyledLinkToSign>
      {message}{' '}
      <Link to={link}>
        <StyledSpan>{linkText}</StyledSpan>
      </Link>
    </StyledLinkToSign>
  );
};

const StyledLinkToSign = styled.div`
  margin: 1.5rem;
  text-align: center;
`;

const StyledSpan = styled.span`
  color: ${props => props.theme.colors.dark_blue};
  font-weight: bold;
`;
