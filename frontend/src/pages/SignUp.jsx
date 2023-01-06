import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Button from '../components/common/Button';
import Input from '../components/common/Input';
import LinkTo from '../components/common/LinkTo';
import Validity from '../components/SignUp/Validity';

export default function SignUp() {
  const [name, setName] = useState('');
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [pwCheck, setPwCheck] = useState('');
  const [number, setNumber] = useState('');
  const [validity, setValidity] = useState(false);

  //임시
  const isLogin = false;

  const handleSubmit = e => {
    e.preventDefault();

    if ([name, nickname, email, password, pwCheck, number].indexOf('') !== -1) {
      return console.log('fail');
    }

    console.log('success');
    console.log([name, nickname, email, password, pwCheck, number]);
  };

  useEffect(() => {
    if (password !== pwCheck) {
      setValidity(true);
    } else {
      setValidity(false);
    }
  }, [password, pwCheck]);

  return (
    <>
      {isLogin || (
        <>
          <Navbar />
          <Container>
            <Contents>
              <Title>회원가입</Title>
              <SignupForm onSubmit={handleSubmit}>
                <Wrapper>
                  <Input label="이름" state={name} setState={setName} />
                </Wrapper>
                <Wrapper>
                  <Input label="닉네임" state={nickname} setState={setNickname} />
                </Wrapper>
                <Wrapper>
                  <Input label="email" state={email} setState={setEmail} />
                </Wrapper>
                <PasswordWrapper>
                  <Input
                    label="비밀번호"
                    state={password}
                    setState={setPassword}
                    type="password"
                    placeholder="영문, 숫자, 특수문자 포함 8글자"
                  />
                  {/* {validity && <Validity type={'password'} />} */}
                </PasswordWrapper>
                <PasswordWrapper>
                  <Input
                    label="비밀번호 확인"
                    state={pwCheck}
                    setState={setPwCheck}
                    type="password"
                    placeholder="영문, 숫자, 특수문자 포함 8글자"
                  />
                  {validity && <Validity type={'passwordCheck'} />}
                </PasswordWrapper>
                <Wrapper>
                  <Input label="전화번호" state={number} setState={setNumber} />
                </Wrapper>
                <SubmitButton>회원가입</SubmitButton>
              </SignupForm>
              <LinkTo message="이미 회원이신가요?" link="/login" linkText="로그인" />
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
    /* background-color: red; */
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  /* @media only screen and (min-width: 1200px) {
    background-color: blue;
  } */
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

const Title = styled.span`
  font-size: ${props => props.theme.fontSizes.titleSize};
`;

const SignupForm = styled.form`
  width: 100%;
  max-width: 800px;
  margin-top: 2rem;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  @media screen and (max-width: 800px) {
    gap: 0;
  }
`;

const Wrapper = styled.div``;
const PasswordWrapper = styled.div`
  display: flex;
  align-items: flex-end;
  position: relative;
`;

const SubmitButton = styled(Button)`
  height: 3rem;
  margin: 2rem 0;
`;
