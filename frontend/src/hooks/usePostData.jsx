import axios from 'axios';

const header = {
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Accept: 'application/json',
  },
};

const usePostData = async (url, data) => {
  try {
    if (data) {
      const response = await axios.post(url, data, header);
      return response;
    }
    const response = await axios.post(url, header);
    return response;
  } catch (error) {
    console.log(error);
    return error;
  }
};

export default usePostData;
