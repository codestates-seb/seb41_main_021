import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Button from '../components/common/Button';
import Input from '../components/common/Input';
import LinkTo from '../components/common/LinkTo';
import Header from '../components/Header';
import Validity from '../components/SignUp/Validity';

export default function SignUp() {
  const [name, setName] = useState('');
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [pwCheck, setPwCheck] = useState('');
  const [number, setNumber] = useState('');
  const [nameValidity, setNameValidity] = useState(false);
  const [emailValidity, setEmailValidity] = useState(false);
  const [pwValidity, setPwValidity] = useState(false);
  const [pwCheckValidity, setPwCheckValidity] = useState(false);
  const [numberValidity, setNumberValidity] = useState(false);

  //임시
  const isLogin = false;

  const handleSubmit = e => {
    e.preventDefault();
    if (nameValidity || nickname === '' || emailValidity || pwValidity || pwCheckValidity || numberValidity) {
      return console.log('fail');
    }

    console.log('success');
    console.log([name, nickname, email, password, pwCheck, number]);
  };

  // 이름 유효성 검사
  const nameValidation = e => {
    setName(e.target.value.replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g, ''));
    if (name.length < 2 || name.length > 5) {
      setNameValidity(true);
    } else {
      setNameValidity(false);
    }
  };
  // 이메일 유효성 검사
  const emailValidation = e => {
    setEmail(e.target.value);
    const emailRegex = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    if (e.target.value && !emailRegex.test(e.target.value)) {
      setEmailValidity(true);
    } else {
      setEmailValidity(false);
    }
  };
  //오토 하이픈, 유효성 검사
  const autoHyphen = e => {
    setNumber(
      e.target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, '$1-$2-$3')
        .replace(/(\-{1,2})$/g, ''),
    );
    if (e.target.value && e.target.value.length !== 13) {
      setNumberValidity(true);
    } else {
      setNumberValidity(false);
    }
  };
  // 비밀번호, 비밀번호 확인 유효성 검사
  useEffect(() => {
    const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    if (password && !passwordRegex.test(password)) {
      setPwValidity(true);
    } else {
      setPwValidity(false);
    }

    if (password !== pwCheck) {
      setPwCheckValidity(true);
    } else {
      setPwCheckValidity(false);
    }
  }, [password, pwCheck]);
  return (
    <>
      {isLogin || (
        <>
          <Navbar />
          <Container>
            <Header title={'회원가입'} />
            <Contents>
              <SignupForm onSubmit={handleSubmit}>
                <Wrapper>
                  <Input label="이름" state={name} onChange={nameValidation} />
                  {nameValidity && <Validity type={'name'} />}
                </Wrapper>
                <Wrapper>
                  <Input label="닉네임" state={nickname} setState={setNickname} />
                </Wrapper>
                <Wrapper>
                  <Input label="Email" state={email} onChange={emailValidation} />
                  {emailValidity && <Validity type={'email'} />}
                </Wrapper>
                <Wrapper>
                  <Input
                    label="비밀번호"
                    state={password}
                    setState={setPassword}
                    type="password"
                    placeholder="영문, 숫자, 특수문자 포함 8글자"
                  />
                  {pwValidity && <Validity type={'password'} />}
                </Wrapper>
                <Wrapper>
                  <Input label="비밀번호 확인" state={pwCheck} setState={setPwCheck} type="password" />
                  {pwCheckValidity && <Validity type={'passwordCheck'} />}
                </Wrapper>
                <Wrapper>
                  <Input type="tel" label="전화번호" state={number} onChange={autoHyphen} />
                  {numberValidity && <Validity type={'number'} />}
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
  width: 80%;
  max-width: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 3rem;
  @media screen and (min-width: 800px) {
    margin-top: 10rem;
  }
`;

const SignupForm = styled.form`
  width: 100%;
  max-width: 800px;
  display: flex;
  flex-direction: column;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: flex-end;
  position: relative;
`;

const SubmitButton = styled(Button)`
  height: 3rem;
  margin: 2rem 0;
`;
