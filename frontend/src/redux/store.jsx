import { configureStore } from '@reduxjs/toolkit';
import userSlice from './slice/UserSlice';
import tayoSlice from './slice/TayoSlice';
import dataSlice from './slice/DataSlice';
import DestinationSlice from './slice/DestinationSlice';

const store = configureStore({
  reducer: {
    user: userSlice.reducer,
    tayoController: tayoSlice.reducer,
    data: dataSlice.reducer,
    destination: DestinationSlice.reducer,
  },
});

export default store;
