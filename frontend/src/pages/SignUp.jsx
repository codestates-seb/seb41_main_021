import { useState, useEffect } from 'react';
import styled from 'styled-components';
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
          <Container>
            <Header title={'회원가입'} />
            <Contents>
              <Title>
                <MainTitle>Welcome!</MainTitle>
                <SubTitle>언제 어디서나 YATA와 함께하세요</SubTitle>
              </Title>
              <SignupForm onSubmit={handleSubmit}>
                <Wrapper>
                  <Input label="이름" placeholder="이름 입력" state={name} onChange={nameValidation} />
                  {/* {nameValidity || <ErrorMsg>아이디를 입력해주세요</ErrorMsg>} */}
                  {nameValidity && <Validity type={'name'} />}
                </Wrapper>
                <Wrapper>
                  <Input label="닉네임" placeholder="닉네임 입력" state={nickname} setState={setNickname} />
                </Wrapper>
                <Wrapper>
                  <Input label="Email" placeholder="이메일 입력" state={email} onChange={emailValidation} />
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
                  <Input
                    label="비밀번호 확인"
                    placeholder="비밀번호 재입력"
                    state={pwCheck}
                    setState={setPwCheck}
                    type="password"
                  />
                  {pwCheckValidity && <Validity type={'passwordCheck'} />}
                </Wrapper>
                <Wrapper>
                  <Input type="tel" placeholder="전화번호 입력" label="전화번호" state={number} onChange={autoHyphen} />
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

const SignupForm = styled.form`
  width: 100%;
  max-width: 800px;
  height: 80%;
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

const ErrorMsg = styled.p`
  color: #de4f55;
  padding: 0.4rem;
`;
