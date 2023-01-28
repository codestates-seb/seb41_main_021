import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  departure: '',
  destination: '',
  departurePoint: {
    longitude: 0,
    latitude: 0,
    address: '',
  },
  destinationPoint: {
    longitude: 0,
    latitude: 0,
    address: '',
  },
  isDeparture: false,
  isDestination: false,

  places: [],
  isFilled: false,
};

const DestinationSlice = createSlice({
  name: 'destination',
  initialState,
  reducers: {
    setDeparture: (state, action) => {
      state.departure = action.payload.departure;
    },
    setDestination: (state, action) => {
      state.destination = action.payload.destination;
    },
    setPlaces: (state, action) => {
      state.places = action.payload.places;
    },
    setIsFilled: (state, action) => {
      state.isFilled = action.payload.isFilled;
    },
    addDeparture: (state, action) => {
      state.departurePoint = action.payload.departurePoint;
      state.isDeparture = action.payload.isDeparture;
    },
    addDestination: (state, action) => {
      state.destinationPoint = action.payload.destinationPoint;
      state.isDestination = action.payload.isDestination;
    },

    clearDeparture: state => {
      state.departurePoint = {
        longitude: 0,
        latitude: 0,
        address: '',
      };
      state.isDeparture = false;
    },
    clearDestination: state => {
      state.destinationPoint = {
        longitude: 0,
        latitude: 0,
        address: '',
      };
      state.isDestination = false;
    },
    clearAll: state => {
      state.departure = '';
      state.destination = '';
      state.isFilled = false;
      state.departurePoint = {
        longitude: 0,
        latitude: 0,
        address: '',
      };
      state.destinationPoint = {
        longitude: 0,
        latitude: 0,
        address: '',
      };
      state.places = [];
      state.isFilled = false;
      state.isDeparture = false;
      state.isDestination = false;
    },
  },
});

// 액션크리에이터는 컴포넌트에서 사용하기 위해 export 하고
export const {
  addDeparture,
  addDestination,
  clearDeparture,
  clearDestination,
  setDeparture,
  setDestination,
  setIsFilled,
  setPlaces,
  clearAll,
} = DestinationSlice.actions;
// reducer 는 configStore에 등록하기 위해 export default 합니다.
export default DestinationSlice;
