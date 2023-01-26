import axios from 'axios';
import { toast } from 'react-toastify';

const header = {
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Accept: 'application/json',
  },
};

const useDeleteData = async url => {
  try {
    const response = await axios.delete(url, {
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: localStorage.getItem('ACCESS'),
      },
    });
    return response;
  } catch (error) {
    console.log(error);
    // toast.warning('삭제에 실패했습니다.');
  }
};

export default useDeleteData;
