import { configureStore } from '@reduxjs/toolkit';
import userSlice from './slice/UserSlice';
import tayoSlice from './slice/TayoSlice';

const store = configureStore({
  reducer: {
    user: userSlice.reducer,
    tayoController: tayoSlice.reducer,
  },
});

export default store;
