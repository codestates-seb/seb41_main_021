import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Button from '../components/common/Button';
import Input from '../components/common/Input';
import LinkTo from '../components/common/LinkTo';
import Header from '../components/Header';
import { useSignup } from '../hooks/useSignup';
import { toast } from 'react-toastify';
import { useEmailAuth, useEmailAuthConfirm, useEmailExistingConfirm } from '../hooks/useEmail';

export default function SignUp() {
  const [name, setName] = useState('');
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [authCode, setAuthCode] = useState('');
  const [password, setPassword] = useState('');
  const [pwCheck, setPwCheck] = useState('');
  const [number, setNumber] = useState('');
  const [nameValidity, setNameValidity] = useState(false);
  const [emailValidity, setEmailValidity] = useState(false);
  const [pwValidity, setPwValidity] = useState(false);
  const [pwCheckValidity, setPwCheckValidity] = useState(false);
  const [numberValidity, setNumberValidity] = useState(false);
  const [authValidity, setAuthValidity] = useState(false);
  const [emailExisting, setEmailExisting] = useState(false);

  //임시
  const isLogin = false;

  const handleSubmit = e => {
    e.preventDefault();
    if (nameValidity || nickname === '' || emailValidity || pwValidity || pwCheckValidity || numberValidity) {
      return console.log('fail');
    }
    if (!authValidity) {
      return toast.warning('이메일 인증을 해주세요.');
    }

    console.log('success');
    console.log([name, nickname, email, password, pwCheck, number]);
  };

  //인증 요청, 중복 확인
  const emailAuth = () => {
    useEmailExistingConfirm(email).then(res => {
      if (res.data.data) {
        useEmailAuth(email);
      } else {
        return toast.warning('이미 가입된 이메일입니다.');
      }
    });
  };

  //인증 확인
  const emailAuthConfirm = () => {
    const body = {
      email,
      authCode,
    };
    useEmailAuthConfirm(body).then(res => {
      setAuthValidity(res.data.data);
      return console.log(res.data.data);
    });
  };

  // 회원가입
  const signUp = () => {
    const body = {
      email,
      password,
      name,
      nickname,
      genders: 'MAN',
    };
    console.log(body);
    useSignup(body).then(res => {
      return console.log(res);
    });
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
          <Header title={'회원가입'} />
          <Container>
            <Contents>
              <Title>
                <MainTitle>Welcome!</MainTitle>
                <SubTitle>언제 어디서나 YATA와 함께하세요</SubTitle>
              </Title>
              <SignupForm onSubmit={handleSubmit}>
                <Wrapper>
                  <Input label="이름" placeholder="이름 입력" state={name} onChange={nameValidation} />
                  {nameValidity && <ErrorMsg>이름을 입력해주세요</ErrorMsg>}
                </Wrapper>
                <Wrapper>
                  <Input label="닉네임" placeholder="닉네임 입력" state={nickname} setState={setNickname} />
                </Wrapper>
                <Wrapper>
                  <EmailWrapper>
                    <Input label="이메일" placeholder="이메일 입력" state={email} onChange={emailValidation} />
                    <Button type="button" onClick={emailAuth}>
                      ㅎㅇ
                    </Button>
                  </EmailWrapper>
                  {emailValidity && <ErrorMsg>올바른 이메일 형식이 아닙니다</ErrorMsg>}
                </Wrapper>
                <Wrapper>
                  <EmailWrapper>
                    <Input
                      label="인증 코드"
                      placeholder="이메일 인증 코드"
                      state={authCode}
                      setState={setAuthCode}></Input>
                    <Button type="button" onClick={emailAuthConfirm}>
                      인증
                    </Button>
                  </EmailWrapper>
                </Wrapper>
                <Wrapper>
                  <Input
                    label="비밀번호"
                    state={password}
                    setState={setPassword}
                    type="password"
                    placeholder="영문, 숫자, 특수문자 포함 8글자"
                  />
                  {pwValidity && <ErrorMsg>영문, 숫자, 특수문자 포함 8글자 이상이어야 합니다.</ErrorMsg>}
                </Wrapper>
                <Wrapper>
                  <Input
                    label="비밀번호 확인"
                    placeholder="비밀번호 재입력"
                    state={pwCheck}
                    setState={setPwCheck}
                    type="password"
                  />
                  {pwCheckValidity && <ErrorMsg>비밀번호가 일치하지 않습니다.</ErrorMsg>}
                </Wrapper>
                <Wrapper>
                  <Input type="tel" placeholder="전화번호 입력" label="전화번호" state={number} onChange={autoHyphen} />
                  {numberValidity && <ErrorMsg>올바른 전화번호 형식이 아닙니다.</ErrorMsg>}
                </Wrapper>
                <SubmitButton onClick={signUp}>회원가입</SubmitButton>
              </SignupForm>
            </Contents>
            <LinkTo message="이미 회원이신가요?" link="/login" linkText="로그인" />
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
  position: relative;
  flex-direction: column;
`;
const EmailWrapper = styled.div`
  display: flex;
  align-items: flex-end;
  button {
    display: flex;
    height: 3rem;
    font-size: 1rem;
    align-items: center;
    justify-content: center;
  }
`;

const SubmitButton = styled(Button)`
  height: 3rem;
  margin: 2rem 0;
`;

const ErrorMsg = styled.p`
  color: #de4f55;
  padding: 0.4rem;
`;

