import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  number: 0,
};

const counterSlice = createSlice({
  //     createSlice 안에 인자로 다음 세가지는 필수로 작성해야 한다.
  // name: "counter"
  // initialState
  // reducers: {}

  name: 'counter',
  initialState,
  reducers: {
    addNumber: (state, action) => {
      state.number = state.number + action.payload;
    },

    minusNumber: (state, action) => {
      state.number = state.number - action.payload;
    },
  },
});

// 액션크리에이터는 컴포넌트에서 사용하기 위해 export 하고
export const { addNumber, minusNumber } = counterSlice.actions;
// reducer 는 configStore에 등록하기 위해 export default 합니다.
export default counterSlice.reducer;
