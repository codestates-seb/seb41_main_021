import axios from 'axios';
import instance from '../api/instance';
const useDriverAuth = async body => {
  try {
    const response = await instance.patch(`/api/v1/validation/driver-license`, body);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export { useDriverAuth };
