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
  fuelTank: '',
  roles: '',
  point: '',
  yataCount: '',
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
      state.fuelTank = action.payload.fuelTank;
      state.roles = action.payload.roles;
      state.point = action.payload.point;
      state.yataCount = action.payload.yataCount;
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
      state.fuelTank = '';
      state.roles = '';
      state.point = '';
      state.yataCount = '';
      state.isLogin = false;
    },
  },
});

// 액션크리에이터는 컴포넌트에서 사용하기 위해 export 하고
export const { loginUser, clearUser } = userSlice.actions;
// reducer 는 configStore에 등록하기 위해 export default 합니다.
export default userSlice;
