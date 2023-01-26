import axios from 'axios';

const header = {
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Authorization: localStorage.ACCESS, // 토큰 추가
  },
};

const useDriverAuth = async body => {
  try {
    const response = await axios.patch(`https://server.yata.kro.kr/api/v1/validation/driver-license`, body, header);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export { useDriverAuth };
