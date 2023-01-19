import { configureStore } from '@reduxjs/toolkit';
import TayoSlice from './slice/TayoSlice';

const store = configureStore({
  reducer: {
    counter: TayoSlice.reducer,
  },
});

export default store;
