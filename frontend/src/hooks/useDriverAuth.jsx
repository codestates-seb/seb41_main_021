import axios from 'axios';

const useDriverAuth = async body => {
  try {
    const response = await axios.patch(`https://server.yata.kro.kr/api/v1/validation/driver-license`, body, {
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: localStorage.getItem('ACCESS'),
      },
    });
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export { useDriverAuth };
