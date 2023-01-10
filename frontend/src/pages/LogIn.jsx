import { useState } from 'react';
import styled from 'styled-components';
import NavBar from '../components/NavBar';
import Button from '../components/common/Button';
import Input from '../components/common/Input';
import LinkTo from '../components/common/LinkTo';
import Logo from '../images/Logo.svg';
import yata from '../images/name.png';
import backgroundImg from '../images/login_bg.webp';
import backgroundImgPC from '../images/login_bg_pc.webp';
import facebook from '../images/facebook_icon.svg';
import google from '../images/google_icon.svg';
import kakao from '../images/kakao_icon.png';

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
            <LogoContainer>
              <Img src={Logo} alt="logo" />
              <TitleImg src={yata} alt="title" />
            </LogoContainer>
            <Contents>
              <Title>
                <MainTitle>Welcome back!</MainTitle>
                <SubTitle>언제 어디서나 YATA와 함께하세요</SubTitle>
              </Title>
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
              <SNSLoginContainer>
                <LineText>SNS LOGIN</LineText>
                <SNSButtonContainer>
                  <SNSContent>
                    <SNSImage src={facebook} alt="facebook" />
                    <SNSText>Facebook</SNSText>
                  </SNSContent>
                  <SNSContent>
                    <SNSImage src={google} alt="google" />
                    <SNSText>google</SNSText>
                  </SNSContent>
                  <SNSContent>
                    <SNSImage src={kakao} alt="kakao" />
                    <SNSText>kakaotalk</SNSText>
                  </SNSContent>
                </SNSButtonContainer>
              </SNSLoginContainer>
            </Contents>
            <LinkTo message="회원이 아니신가요?" link="/signup" linkText="회원가입" />
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
  background-image: url(${backgroundImg});
  background-size: cover;
  @media only screen and (max-height: 800px) {
    justify-content: center;
  }
`;

const LogoContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  @media only screen and (max-height: 800px) {
    display: none;
  }
`;

const Img = styled.img`
  width: 30%;
  background-color: white;
  border-radius: 2rem;
  margin-top: 2rem;
`;

const TitleImg = styled.img`
  width: 30%;
  margin-bottom: ${props => props.theme.margins.xxxl};
`;
const Title = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const MainTitle = styled.span`
  font-weight: bold;
  margin-top: 1rem;
  margin-bottom: 1rem;
  font-size: 2.7rem;
`;

const SubTitle = styled.span`
  font-size: ${props => props.theme.fontSizes.xl};
  color: ${props => props.theme.colors.dark_gray};
  margin-bottom: 1.5rem;
`;
const Contents = styled.div`
  width: 80%;
  max-width: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: white;
  padding: 2rem;
  border-radius: 5rem;
  box-shadow: -20px 10px 30px rgba(0, 0, 0, 0.3);
  margin-bottom: 1rem;
`;

const LoginForm = styled.form`
  width: 100%;
  height: 22rem;
  min-width: 22rem;
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

const LineText = styled.div`
  font-weight: 700;
  display: flex;
  flex-basis: 100%;
  align-items: center;
  color: black;
  margin: 8px;
  &::before {
    content: '';
    flex-grow: 1;
    background: black;
    height: 1px;
    font-size: 0px;
    line-height: 0px;
    margin-right: 16px;
  }
  &::after {
    content: '';
    flex-grow: 1;
    background: black;
    height: 1px;
    font-size: 0px;
    line-height: 0px;
    margin-left: 16px;
  }
`;

const SNSLoginContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

const SNSButtonContainer = styled.div`
  display: flex;
`;

const SNSContent = styled.div`
  display: flex;
  flex: 1;
  align-items: center;
  flex-direction: column;
`;

const SNSImage = styled.img`
  width: 50%;
  margin: 1rem;
`;

const SNSText = styled.span`
  font-weight: 700;
`;
