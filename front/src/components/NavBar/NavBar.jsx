import { StyledNavi, NaviTitle, StyledUl, StyledLink } from "./styles";

const NavBar = ({ isLoggedIn, handleAuthentication }) => {
  const handleLogout = (event) => {
    event.preventDefault(); // 기본 이벤트를 막습니다.
    handleAuthentication(null); // 로그아웃을 처리합니다.
  };

    return (
      <StyledNavi>
        <NaviTitle>Seconadary-if</NaviTitle>
        <StyledUl>
        <li>
          {isLoggedIn ? (
            <StyledLink to="/" onClick={handleLogout}>
              로그아웃
            </StyledLink>
          ) : (
            <StyledLink to="/login">로그인</StyledLink>
          )}
        </li>
        </StyledUl>
      </StyledNavi>
  );
};

export default NavBar;
