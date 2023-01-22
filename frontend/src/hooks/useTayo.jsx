import useGetData from './useGetData';
import usePostData from './usePostData';
import usePatchData from './usePatchData';
import useDeleteData from './useDeleteData';

import { toast } from 'react-toastify';
const header = {
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Accept: 'application/json',
  },
};

// 4.4, 4.5, 5.3
const useTayoGet = async (url, data) => {
  await useGetData(url, data, header).then(res => {
    if (res.response.status === 401) {
      toast.warning('오류오류오류');
    }
  });
};

// 4.1
const useTayoCreate = async (url, data) => {
  await usePostData(url, data, header).then(res => {
    if (res.response.status === 401) {
      toast.warning('오류오류오류');
    }
  });
};

// 4.2
const useTayoEdit = async (url, data) => {
  await usePatchData(url, data, header).then(res => {
    if (res.response.status === 401) {
      toast.warning('오류오류오류');
    }
  });
};

// 4.3, 5.4
const useTayoDelete = async (url, data) => {
  await useDeleteData(url, data, header).then(res => {
    if (res.response.status === 401) {
      toast.warning('오류오류오류');
    }
  });
};

// 5.1
const useTayoRequest = async (url, data) => {
  await usePostData(url, data, header).then(res => {
    if (res.response.status === 401) {
      toast.warning('오류오류오류');
    }
  });
};

// 5.2
const useTayoInvite = async (url, data) => {
  await usePostData(url, data, header).then(res => {
    if (res.response.status === 401) {
      toast.warning('오류오류오류');
    }
  });
};

// 6.1 6.2
const useTayoAccept = async (url, data) => {
  await usePostData(url, data, header).then(res => {
    if (res.response.status === 401) {
      toast.warning('오류오류오류');
    }
  });
};

export { useTayoGet, useTayoCreate, useTayoEdit, useTayoDelete, useTayoRequest, useTayoInvite, useTayoAccept };
