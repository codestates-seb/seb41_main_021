import axios from 'axios';

const header = {
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
  },
};

const useEmailAuth = async email => {
  try {
    const response = await axios.post(`https://server.yata.kro.kr/api/v1/validation/email/${email}`, header);
    return console.log(response);
  } catch (error) {
    return console.log(error);
  }
};

const useEmailAuthConfirm = async body => {
  try {
    const response = await axios.post(`https://server.yata.kro.kr/api/v1/validation/auth-code`, body, header);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

const useEmailExistingConfirm = async email => {
  try {
    const response = await axios.get(`https://server.yata.kro.kr/api/v1/validation/email/${email}`);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export { useEmailAuth, useEmailAuthConfirm, useEmailExistingConfirm };
