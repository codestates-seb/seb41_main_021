import axios from 'axios';
import { toast } from 'react-toastify';

const useDeleteData = async url => {
  try {
    const response = await axios.delete(url, {
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: localStorage.getItem('ACCESS'),
      },
    });
    toast.warning('삭제 완료.');
    return response;
  } catch (error) {
    if (error.response.status === 403) {
      toast.warning('삭제 권한이 없습니다.');
    } else toast.warning('삭제에 실패했습니다.');
  }
};

export default useDeleteData;
