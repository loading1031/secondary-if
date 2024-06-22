import { configureStore } from "@reduxjs/toolkit";
import searchSlice from "./searchSlice";

export default configureStore({
  reducer: {
    search: searchSlice,
  },
});
