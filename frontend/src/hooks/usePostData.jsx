import axios from 'axios';
import instance from '../api/instance';
import { toast } from 'react-toastify';

const usePostData = async (url, data) => {
  try {
    if (data) {
      const response = await instance.post(url, data);
      return response;
    }
    const response = await instance.post(url);
    return response;
  } catch (error) {
    console.log(error.response.data.message);
    // return error;
    if (error.response.data.message === 'PAYMENT_NOT_ENOUGH_POINT') {
      toast.warning('포인트가 부족합니다.');
    } else {
      toast.warning('잘못 된 요청입니다.');
    }
  }
};

export default usePostData;
