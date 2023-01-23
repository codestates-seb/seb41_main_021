import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  email: '',
  name: '',
  nickname: '',
  genders: '',
  imgUrl: '',
  providerType: '',
  carImgUrl: '',
  memberStatus: '',
  roles: '',
  isLogin: false,
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    loginUser: (state, action) => {
      state.email = action.payload.email;
      state.name = action.payload.name;
      state.nickname = action.payload.nickname;
      state.genders = action.payload.genders;
      state.imgUrl = action.payload.imgUrl;
      state.providerType = action.payload.providerType;
      state.carImgUrl = action.payload.carImgUrl;
      state.memberStatus = action.payload.memberStatus;
      state.roles = action.payload.roles;
      state.isLogin = true;
    },

    clearUser: state => {
      state.email = '';
      state.name = '';
      state.nickname = '';
      state.genders = '';
      state.imgUrl = '';
      state.providerType = '';
      state.carImgUrl = '';
      state.memberStatus = '';
      state.roles = '';
      state.isLogin = false;
    },
  },
});
8;

// 액션크리에이터는 컴포넌트에서 사용하기 위해 export 하고
export const { loginUser, clearUser } = userSlice.actions;
// reducer 는 configStore에 등록하기 위해 export default 합니다.
export default userSlice;
