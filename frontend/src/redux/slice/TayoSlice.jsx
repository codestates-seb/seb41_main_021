import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  title: '',
  specifics: '',
  departureTime: '',
  timeOfArrival: '',
  maxWaitingTime: 0,
  maxPeople: 0,
  yataStatus: '',
  amount: '',
  carModel: '',
  strPoint: {
    longitude: 0,
    latitude: 0,
    address: '',
  },
  destination: {
    longitude: 0,
    latitude: 0,
    address: '',
  },
};

const tayoSlice = createSlice({
  name: 'tayoController',
  initialState,
  reducers: {
    createTayo: (state, action) => {
      state.title = action.payload.title;
      state.specifics = action.payload.specifics;
      state.departureTime = action.payload.departureTime;
      state.timeOfArrival = action.payload.timeOfArrival;
      state.maxWaitingTime = action.payload.maxWaitingTime;
      state.maxPeople = action.payload.maxPeople;
      state.yataStatus = action.payload.yataStatus;
      state.memberStatus = action.payload.memberStatus;
      state.amount = action.payload.amount;
      state.carModel = action.payload.carModel;
      state.strPoint = action.payload.strPoint;
      state.destination = action.payload.destination;
    },

    editTayo: (state, action) => {
      state.title = action.payload.title;
      state.specifics = action.payload.specifics;
      state.departureTime = action.payload.departureTime;
      state.timeOfArrival = action.payload.timeOfArrival;
      state.maxWaitingTime = action.payload.maxWaitingTime;
      state.maxPeople = action.payload.maxPeople;
      state.yataStatus = action.payload.yataStatus;
      state.memberStatus = action.payload.memberStatus;
      state.amount = action.payload.amount;
      state.carModel = action.payload.carModel;
      state.strPoint = action.payload.strPoint;
      state.destination = action.payload.destination;
    },

    deleteTayo: (state, action) => {
      state.number = state.number - action.payload;
    },
  },
});

// 액션크리에이터는 컴포넌트에서 사용하기 위해 export 하고
export const { createTayo, editTayo, deleteTayo } = tayoSlice.actions;
// reducer 는 configStore에 등록하기 위해 export default 합니다.
export default tayoSlice;
