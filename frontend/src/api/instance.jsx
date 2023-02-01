import axios from 'axios';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

// url 호출 시 기본 값 셋팅
const instance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
  },
});

instance.interceptors.request.use(
  function (config) {
    const accessToken = localStorage.getItem('ACCESS');
    if (accessToken) {
      config.headers['Authorization'] = accessToken;
    }
    return config;
  },
  function (error) {
    return Promise.reject(error);
  },
);

instance.interceptors.response.use(
  function (response) {
    return response;
  },
  async function (error) {
    const { response: errorResponse } = error;
    const originalRequest = error.config;

    // 인증 에러 발생시
    if (errorResponse.status === 401) {
      return await resetTokenAndReattemptRequest(error);
    }

    return Promise.reject(error);
  },
);

let subscribers = [];
async function resetTokenAndReattemptRequest(error) {
  let isAlreadyFetchingAccessToken = false;
  try {
    const { response: errorResponse } = error;

    // subscribers에 access token을 받은 이후 재요청할 함수 추가 (401로 실패했던)
    // retryOriginalRequest는 pending 상태로 있다가
    // access token을 받은 이후 onAccessTokenFetched가 호출될 때
    // access token을 넘겨 다시 axios로 요청하고
    // 결과값을 처음 요청했던 promise의 resolve로 settle시킨다.
    const retryOriginalRequest = new Promise((resolve, reject) => {
      addSubscriber(async accessToken => {
        try {
          errorResponse.config.headers['Authorization'] = accessToken;
          resolve(instance(errorResponse.config));
        } catch (err) {
          reject(err);
        }
      });
    });

    // refresh token을 이용해서 access token 요청
    if (!isAlreadyFetchingAccessToken) {
      isAlreadyFetchingAccessToken = true; // 한 번만 요청

      const response = await useTokenRefresh();
      localStorage.setItem('ACCESS', response.headers.authorization);
      console.log('refresh access-token');

      isAlreadyFetchingAccessToken = false; // 초기화

      onAccessTokenFetched(response.headers.authorization);
    }

    return retryOriginalRequest; // pending 됐다가 onAccessTokenFetched가 호출될 때 resolve
  } catch (error) {
    signOut();
    return Promise.reject(error);
  }
}

function addSubscriber(callback) {
  subscribers.push(callback);
}

function onAccessTokenFetched(accessToken) {
  subscribers.forEach(callback => callback(accessToken));
  subscribers = [];
}

function signOut() {
  localStorage.removeItem('ACCESS');
  localStorage.removeItem('REFRESH');
  toast.warning('로그인 정보가 만료되었습니다.');
  // setTimeout(() => {
  //   window.location.href = '/';
  // }, 1500);
}

const useTokenRefresh = async () => {
  try {
    const response = await axios.post(
      `${process.env.REACT_APP_SERVER_URL}/api/v1/auth/refresh`,
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
  } catch (error) {
    return Promise.reject(error);
  }
};

export default instance;
