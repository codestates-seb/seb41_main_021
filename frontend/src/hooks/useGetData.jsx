import axios from 'axios';
import instance from '../api/instance';
import { toast } from 'react-toastify';

const useGetData = async url => {
  try {
    const response = await instance.get(url);
    return response;
  } catch (error) {
    toast.warning('유저 정보를 불러오지 못했습니다.');
  }
};

export { useGetData };
