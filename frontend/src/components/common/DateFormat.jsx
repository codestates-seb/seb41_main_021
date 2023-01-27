import moment from 'moment';
import 'moment/locale/ko';
import styled from 'styled-components';

const dateFormat = date => {
  const nowTime = moment(date).format('YYYY-MM-DD HH:mm');
  //   2023-09-11T20:11:0
  return nowTime;
};

export { dateFormat };
