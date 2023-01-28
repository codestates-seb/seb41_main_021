import axios from 'axios';
import { toast } from 'react-toastify';
import instance from '../api/instance';

const useLogin = async (url, data) => {
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
    toast.warning('유저 정보를 불러오지 못했습니다.');
  }
};

const useLogout = async () => {
  try {
    const response = await axios.post('https://server.yata.kro.kr/api/v1/auth/logout');
    if (response) {
      localStorage.clear();
    }
  } catch (error) {}
  return null;
};

const useTokenRefresh = async () => {
  try {
    const response = await axios.post(
      'https://server.yata.kro.kr/api/v1/auth/refresh',
      {},
      {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          Authorization: localStorage.getItem('ACCESS'),
          RefreshToken: localStorage.getItem('REFRESH'),
        },
      },
    );
    return response;
    if (response) {
      // localStorage.setItem('ACCESS', response.headers.authorization);
    }
  } catch (error) {
    console.log(error);
    // toast.warning('유저 정보를 불러오지 못했습니다.');
  }
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

export { useLogin, useLogout, useGetUserInfo, useTokenRefresh, checkIfLogined };
