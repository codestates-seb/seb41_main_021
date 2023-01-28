import moment from 'moment';
import 'moment/locale/ko';

const dateFormat = date => {
  const nowTime = moment(date).format('YYYY-MM-DD HH:mm');
  return nowTime;
};

export { dateFormat };
