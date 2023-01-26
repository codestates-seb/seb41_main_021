import styled from 'styled-components';
import { useState } from 'react';
import Header from '../components/Header';
import Input from '../components/common/Input';
import Button from '../components/common/Button';
import { toast } from 'react-toastify';
import { useDriverAuth } from '../hooks/useDriverAuth';
import { useNavigate } from 'react-router-dom';

export default function DriverAuth() {
  const [name, setName] = useState('');
  const [nameValidity, setNameValidity] = useState(false);
  const [licenseNum, setLicenseNumber] = useState('');
  const [licenseNumberValidity, setLicenseNumberValidity] = useState(false);
  const [date, setDate] = useState('');
  const [dateValidity, setDateValidity] = useState(false);

  const navigate = useNavigate();

  const handleAuth = e => {
    e.preventDefault();
    if (name === '' || nameValidity || licenseNum === '' || licenseNumberValidity || date === '' || dateValidity) {
      return toast.warning('제대로 입력했는지 확인해주세요.');
    }
    const body = {
      name,
      driverLicenseNumber: licenseNum, // 프로퍼티 이름 수정
      dateOfIssue: date, // 프로퍼티 이름 수정
    };
    useDriverAuth(body).then(res => {
      if (res.status === 204) {
        toast.success('운전자 인증에 성공했습니다.');
        navigate('/my-page');
      }
      return console.log(res);
    });
  };

  const nameValidation = e => {
    setName(e.target.value.replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g, ''));
    if (name.length < 2 || name.length > 5) {
      setNameValidity(true);
    } else {
      setNameValidity(false);
    }
  };

  const LicenseNumValidation = e => {
    setLicenseNumber(
      e.target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{0,2})(\d{0,2})(\d{0,6})(\d{0,2})$/g, '$1-$2-$3-$4')
        .replace(/(\-{1,2})$/g, ''),
    );
    if (e.target.value && e.target.value.length !== 15) {
      setLicenseNumberValidity(true);
    } else {
      setLicenseNumberValidity(false);
    }
  };

  const DateValidation = e => {
    setDate(
      e.target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{0,4})(\d{0,2})(\d{0,2})$/g, '$1-$2-$3')
        .replace(/(\-{1,2})$/g, ''),
    );
    if (e.target.value && e.target.value.length !== 10) {
      setDateValidity(true);
    } else {
      setDateValidity(false);
    }
  };

  // 운전자 인증
  const driverAuth = () => {};

  return (
    <>
      <Header title="운전자 인증하기" />
      <Container>
        <Contents>
          <DriverAuthForm onSubmit={handleAuth}>
            <Wrapper>
              <Input label="이름" placeholder="이름 입력" state={name} onChange={nameValidation}></Input>
              <Input
                label="운전면허 번호"
                placeholder="숫자만 입력 (00-11-222222-33)"
                state={licenseNum}
                onChange={LicenseNumValidation}
                maxLength={15}></Input>
              {licenseNumberValidity && <ErrorMsg>올바른 운전면허 번호 형식이 아닙니다.</ErrorMsg>}
            </Wrapper>
            <Wrapper>
              <Input
                label="발급일자"
                placeholder="숫자만 입력 (YYYY-MM-DD)"
                state={date}
                onChange={DateValidation}
                maxLength={10}></Input>
              {dateValidity && <ErrorMsg>올바른 발급일자 형식이 아닙니다.</ErrorMsg>}
            </Wrapper>
            <AuthBtn>인증하기</AuthBtn>
          </DriverAuthForm>
        </Contents>
      </Container>
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
    margin-top: 1rem;
  }
`;
const DriverAuthForm = styled.form`
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
const AuthBtn = styled(Button)`
  height: 3rem;
  margin: 2rem 0;
`;

const ErrorMsg = styled.p`
  color: #de4f55;
  padding: 0.4rem;
`;
