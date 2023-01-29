import axios from 'axios';
import { toast } from 'react-toastify';

const header = {
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Accept: 'application/json',
  },
};

const usePostData = async (url, data) => {
  try {
    if (data) {
      const response = await axios.post(url, data, {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          Authorization: localStorage.getItem('ACCESS'),
        },
      });
      return response;
    }
    const response = await axios.post(url, header);
    return response;
  } catch (error) {
    console.log(error.response.data.message);
    // return error;
    if (error.response.data.message === 'PAYMENT_NOT_ENOUGH_POINT') {
      toast.warning('포인트가 부족합니다.');
    } else {
      toast.warning('수락이 불가능한 유저입니다.');
    }
  }
};

export default usePostData;
