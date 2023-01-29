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

  departureTime: '',
  amount: '',
  maxPeople: '',
  specifics: '',
  carModel: '',
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
    setDepartureTime: (state, action) => {
      state.departureTime = action.payload.departureTime;
    },
    setAmount: (state, action) => {
      state.amount = action.payload.amount;
    },
    setMaxPeople: (state, action) => {
      state.maxPeople = action.payload.maxPeople;
    },
    setCarModel: (state, action) => {
      state.carModel = action.payload.carModel;
    },
    setSpecifics: (state, action) => {
      state.specifics = action.payload.specifics;
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
    setAll: (state, action) => {
      state.departure = action.payload.departure;
      state.destination = action.payload.destination;
      state.departurePoint = action.payload.departurePoint;
      state.destinationPoint = action.payload.destinationPoint;
      state.isDeparture = action.payload.isDeparture;
      state.isDestination = action.payload.isDestination;
      state.departureTime = action.payload.departureTime;
      state.amount = action.payload.amount;
      state.maxPeople = action.payload.maxPeople;
      state.specifics = action.payload.specifics;
      state.carModel = action.payload.carModel;
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
      state.departureTime = '';
      state.amount = '';
      state.maxPeople = '';
      state.specifics = '';
      state.carModel = '';
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
  setCarModel,
  clearAll,
  setDepartureTime,
  setAmount,
  setMaxPeople,
  setSpecifics,
  setAll,
} = DestinationSlice.actions;
// reducer 는 configStore에 등록하기 위해 export default 합니다.
export default DestinationSlice;
