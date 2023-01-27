import { configureStore } from '@reduxjs/toolkit';
import userSlice from './slice/UserSlice';
import tayoSlice from './slice/TayoSlice';
import dataSlice from './slice/DataSlice';

const store = configureStore({
  reducer: {
    user: userSlice.reducer,
    tayoController: tayoSlice.reducer,
    data: dataSlice.reducer,
  },
});

export default store;
