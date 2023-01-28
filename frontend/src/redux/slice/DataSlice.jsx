import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

const tayoDataFetch = createAsyncThunk('dataSlice/tayoDataFetch', async url => {
  const res = await fetch(url);
  const data = await res.json();
  return data.data;
});

const dataSlice = createSlice({
  name: 'dataSlice',
  initialState: {
    title: 'title',
    specifics: 'specifics',
    departureTime: 'departureTime',
    timeOfArrival: 'timeOfArrival',
    maxWaitingTime: 0,
    maxPeople: 0,
    amount: 0,
    carModel: 'carModel',
    strPoint: {
      longitude: 0,
      latitude: 0,
      address: 'address',
    },
    destination: {
      longitude: 0,
      latitude: 0,
      address: 'address',
    },
  },
  reducers: {
    // up: (state, action) => {
    //   state.value = state.value + action.payload;
    // },
  },

  extraReducers: builder => {
    builder.addCase(tayoDataFetch.pending, (state, action) => {
      state.status = 'Loading';
    });
    builder.addCase(tayoDataFetch.fulfilled, (state, action) => {
      state.title = action.payload;
      state.specifics = 'complete';
    });
    builder.addCase(tayoDataFetch.rejected, (state, action) => {
      state.status = 'fail';
    });
  },
});
export default dataSlice;
export const { set } = dataSlice.actions;
export { tayoDataFetch };
