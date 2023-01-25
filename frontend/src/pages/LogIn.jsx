import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Button from '../components/common/Button';
import Input from '../components/common/Input';
import LinkTo from '../components/common/LinkTo';
import facebook from '../images/facebook_icon.svg';
import google from '../images/google_icon.svg';
import kakao from '../images/kakao_icon.png';
import Header from '.././components/Header';
import { useLogin, useGetUserInfo } from '../hooks/useLogin';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser } from '../redux/slice/UserSlice';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isValidEmail, setIsValidEmail] = useState(true);
  const [isValidPW, setIsValidPW] = useState(true);
  const dispatch = useDispatch();
  const info = useSelector(state => {
    return state.user;
  });

  const isLogin = false;

  const navigate = useNavigate();

  const handleSubmit = e => {
    e.preventDefault();
    const data = {
      email: email,
      password: password,
    };

    if (email === '') setIsValidEmail(false);
    else setIsValidEmail(true);
    if (password === '') setIsValidPW(false);
    else setIsValidPW(true);
    if (email === '' || password === '') return;

    useLogin('https://server.yata.kro.kr/api/v1/auth/login', data).then(res => {
      if (res === 200) {
        useGetUserInfo().then(res => dispatch(loginUser(res)));
        navigate('/tabnida-list');
        console.log(info);
      }
    });
  };

  return (
    <>
      {isLogin || (
        <>
          <Header title="로그인" />
          <Container className="container">
            <Contents className="contents">
              <Title>
                <MainTitle>Welcome back!</MainTitle>
                <SubTitle>언제 어디서나 YATA와 함께하세요</SubTitle>
              </Title>
              <LoginForm onSubmit={handleSubmit}>
                <IdWrapper>
                  <Input label="이메일" placeholder="이메일 입력" state={email} setState={setEmail} />
                  {isValidEmail || <ErrorMsg>이메일을 입력해주세요</ErrorMsg>}
                </IdWrapper>
                <PwWrapper>
                  <Input
                    label="비밀번호"
                    placeholder="비밀번호 입력"
                    state={password}
                    setState={setPassword}
                    type="password"
                  />
                  {isValidPW || <ErrorMsg>비밀번호를 입력해주세요</ErrorMsg>}
                </PwWrapper>
                <SubmitButton>로그인</SubmitButton>
              </LoginForm>
              <SNSLoginContainer>
                <LineText>SNS LOGIN</LineText>
                <SNSButtonContainer>
                  <SNSContent>
                    <SNSImage src={facebook} alt="facebook" />
                  </SNSContent>
                  <SNSContent>
                    <SNSImage src={google} alt="google" />
                  </SNSContent>
                  <SNSContent>
                    <SNSImage src={kakao} alt="kakao" />
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
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Contents = styled.div`
  width: 90%;
  height: 90%;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
`;

const Title = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const MainTitle = styled.span`
  font-weight: bold;
  margin-top: 2rem;
  margin-bottom: 1rem;
  font-size: 2.7rem;
`;

const SubTitle = styled.span`
  font-size: ${props => props.theme.fontSizes.xl};
  color: ${props => props.theme.colors.dark_gray};
  margin-bottom: 2rem;
`;

const LoginForm = styled.form`
  width: 100%;
  height: 24rem;
  min-width: 20rem;
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
  color: gray;
  margin: 8px;

  &::before {
    content: '';
    flex-grow: 1;
    background: lightgray;
    height: 1px;
    font-size: 0px;
    line-height: 0px;
    margin-right: 16px;
  }
  &::after {
    content: '';
    flex-grow: 1;
    background: lightgray;
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
  align-items: center;
`;

const SNSContent = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
`;

const SNSImage = styled.img`
  width: 3rem;
  margin: 1rem;
`;
