import axios from 'axios';

// const getErrorType = status => {
//   if (status === 403) {
//     return 'incorrectPassword';
//   }
//   if (status === 504) {
//     return 'invalidEmail';
//   }
//   return 'regular';
// };

const useLogin = async (url, data) => {
  try {
    const response = await axios.post('https://server.yata.kro.kr/api/v1/auth/login', data);
    if (response.headers.authorization) {
      //   setToken({
      //     ACCESS: `Bearer ${response.headers.authorization}`,
      //     REFRESH: response.headers.refreshtoken,
      //   });
    }
    await localStorage.setItem('ACCESS', response.headers.authorization);
    await localStorage.setItem('REFRESH', response.headers.refreshtoken);

    // setUserMbti(response.data.mbti);
    // setIsLogIn(true);
    // invalidate();
  } catch (error) {
    // const validationType = getErrorType(error.response.data.status);
    // if (validationType !== 'regular') return setErrorException(validationType);
  }
  return null;
};

const useLogout = async () => {
  try {
    const response = await axios.post('https://server.yata.kro.kr/api/v1/auth/logout');
    if (response.headers.authorization) {
      // localStorage remove 토큰
    }
  } catch (error) {}
  return null;
};

export { useLogin, useLogout };
