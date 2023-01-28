import axios from 'axios';
import instance from '../api/instance';

const header = {
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Authorization: localStorage.getItem('ACCESS'),
  },
};

const useChargeList = async url => {
  try {
    const response = await instance.get(url, header);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export default useChargeList;
