import axios from 'axios';

const useGetData = async (url, data) => {
  try {
    if (data) {
      const response = await axios.get(url, data, {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          Authorization: localStorage.getItem('ACCESS'),
        },
      });
      return response;
    }
    const response = await axios.get(url, {
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: localStorage.getItem('ACCESS'),
      },
    });
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export default useGetData;
