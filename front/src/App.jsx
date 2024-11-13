import { Outlet, useLocation } from "react-router-dom";
import { useState } from "react";

import NavBar from "./components/NavBar/NavBar";
import Footer from "./components/Footer/Footer";
import Header from "./components/Header/Header";
import Artifact from "./components/Artifact/Artifact";

function App() {
  const location = useLocation();
  const showComponents = location.pathname !== "/404" && location.pathname !== "/login"; // NotFound 페이지가 아닌 경우
  const [authToken, setAuthToken] = useState(
    localStorage.getItem("token") || null
  );
  const isLoggedIn = authToken !== null;

  const handleAuthentication = (token) => {
    if (token) {
      localStorage.setItem("token", token); // 토큰을 로컬 스토리지에 저장
      setAuthToken(token); // 상태를 업데이트하여 로그인 반영
    } else {
      localStorage.removeItem("token"); // 로컬 스토리지에서 토큰 제거
      setAuthToken(null); // 상태를 업데이트하여 로그아웃 반영
    }
  };

  return (
    <div>
      {showComponents && (
        <NavBar
          isLoggedIn={isLoggedIn}
          handleAuthentication={handleAuthentication}
        />
      )}
      {showComponents && (
        <div>
          <Header />
          <Artifact />
        </div>
      )}
      <Outlet context={{ isLoggedIn, handleAuthentication }} />
      <Footer />
    </div>
  );
}

export default App;
