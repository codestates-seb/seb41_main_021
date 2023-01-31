import axios from 'axios';
import instance from '../api/instance';

const useJourneyState = async url => {
  try {
    const response = await instance.get(url);
    return response;
  } catch (error) {
    return console.log(error);
  }
};

export default useJourneyState;
