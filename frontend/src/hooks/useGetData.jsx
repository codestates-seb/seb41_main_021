import axios from 'axios';
import instance from '../api/instance';
import { toast } from 'react-toastify';

const useGetData = async url => {
  try {
    const response = await instance.get(url);
    return response;
  } catch (error) {
    return error;
  }
};

export { useGetData };
