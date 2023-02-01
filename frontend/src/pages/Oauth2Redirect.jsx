import styled from 'styled-components';
import { useEffect } from 'react';

import { useNavigate, useSearchParams } from 'react-router-dom';

import Loading from './Loading';
import { toast } from 'react-toastify';

export default function Oauth2Redirect() {
  const navigate = useNavigate();
  const [SearchParams, setSearchParams] = useSearchParams();
  const ACCESS = SearchParams.get('token');
  const REFRESH = SearchParams.get('refresh_token');
  const ERROR = SearchParams.get('error');
  useEffect(() => {
    if (ERROR) {
      toast.warning('이미 회원가입 된 아이디입니다.');
      return navigate('/');
    }
    localStorage.setItem('ACCESS', `Bearer ${ACCESS}`);
    localStorage.setItem('REFRESH', `Bearer ${REFRESH}`);
    setTimeout(() => {
      navigate('/my-page');
    }, 300);
  }, []);
  return (
    <>
      <Loading />
    </>
  );
}
