import axios from 'axios';
import instance from '../api/instance';

const usePatchData = async (url, data) => {
  try {
    if (data) {
      const response = await instance.patch(url, data);
      return response;
    }
    const response = await instance.patch(url);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export default usePatchData;
