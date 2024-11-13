import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import store from "./redux/store";
import { Provider } from "react-redux";
import Login from "./pages/Login/index.jsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      { path: "/login", element: <Login /> },
    ],
  },
]);

// 렌더링
ReactDOM.createRoot(document.getElementById("root")).render(
  <Provider store={store}>
    <React.StrictMode>
      <RouterProvider router={router} />
    </React.StrictMode>
  </Provider>
);