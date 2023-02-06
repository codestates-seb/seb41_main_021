import axios from 'axios';

const useSignup = async body => {
  try {
    const response = await axios.post(`${process.env.REACT_APP_SERVER_URL}/api/v1/members/signup`, body);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export { useSignup };
