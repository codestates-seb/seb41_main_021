import axios from 'axios';
import { toast } from 'react-toastify';

const header = {
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Authorization:
      'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjcxNjg5Nzg4fQ.eFeEyh5F5ilhUfK28DzIxNPscqrlo5d9kNcOZYgbsUs',
  },
};

const usePayment = async data => {
  try {
    if (!data) {
      toast.warning('결제정보를 확인해주세요');
      return;
    } else if (data) {
      const response = await axios.post('https://server.yata.kro.kr/api/v1/payments/toss', data, header);
      return response;
    }
  } catch (error) {
    return console.log(error);
  }
};

export default usePayment;
