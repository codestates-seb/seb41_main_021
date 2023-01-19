import axios from 'axios';

const header = {
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Accept: 'application/json',
  },
};

const useGetData = async (url, data) => {
  try {
    if (data) {
      const response = await axios.get(url, data, header);
      return response;
    }
    const response = await axios.get(url, header);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export default useGetData;
