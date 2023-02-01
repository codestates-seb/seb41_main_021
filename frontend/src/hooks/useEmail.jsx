import axios from 'axios';
import instance from '../api/instance';
const useEmailAuth = async email => {
  try {
    const response = await instance.post(`/api/v1/validation/email/${email}`);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

const useEmailAuthConfirm = async body => {
  try {
    const response = await instance.post(`/api/v1/validation/auth-code`, body);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

const useEmailExistingConfirm = async email => {
  try {
    const response = await instance.get(`/api/v1/validation/email/${email}`);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export { useEmailAuth, useEmailAuthConfirm, useEmailExistingConfirm };
