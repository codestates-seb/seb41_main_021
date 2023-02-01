import axios from 'axios';
import { toast } from 'react-toastify';
import instance from '../api/instance';

const useLogin = async data => {
  const url = `${process.env.REACT_APP_SERVER_URL}/api/v1/auth/login`;
  try {
    const response = await axios.post(url, data);
    if (response.headers.authorization) {
      localStorage.setItem('ACCESS', response.headers.authorization);
      localStorage.setItem('REFRESH', response.headers.refreshtoken);

      return response.status;
    }
  } catch (error) {
    toast.warning('로그인에 실패했습니다.');
  }
  return null;
};

const useGetUserInfo = async () => {
  try {
    const response = await instance.get('/api/v1/members');
    return response.data.data;
  } catch (error) {
    return error;
    toast.warning('유저 정보를 불러오지 못했습니다.');
  }
};

const useLogout = async () => {
  try {
    const response = await instance.post('/api/v1/auth/logout');
    if (response) {
      localStorage.clear();
    }
  } catch (error) {}
  return null;
};

import { useSelector } from 'react-redux';

const checkIfLogined = () => {
  const isLogin = useSelector(state => state.user.isLogin);
  if (isLogin) {
  } else {
    toast.warning('로그인이 필요한 페이지 입니다.');
  }
  return isLogin;
};

export { useLogin, useLogout, useGetUserInfo, checkIfLogined };
