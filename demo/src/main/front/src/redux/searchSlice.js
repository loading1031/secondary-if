import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  finalSearchRes: "",
};

export const searchSlice = createSlice({
  name: "search",
  initialState,
  reducers: {
    setFinalSearchRes: (state, action) => {
      state.finalSearchRes = action.payload;
    },
  },
});

// 액션 생성자 내보내기
export const { setFinalSearchRes } = searchSlice.actions;

// 리듀서 내보내기
export default searchSlice.reducer;
