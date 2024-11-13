import styled from "styled-components";
import { NavLink } from 'react-router-dom';

export const StyledNavi = styled.nav`
  position: fixed;
  top: 0;
  left: 0;
  z-index: 10;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.25rem;
  width: 100%;
`;
export const NaviTitle = styled.h3`
  font-size: 1.5rem;
  color: black;
  margin-left: 10vw;
`;

export const StyledUl = styled.ul`
  display: flex;
  list-style: none;
  padding: 10px;
  margin: 0;
  li {
    color: black;
    margin-right: 20px;
  }
`;

export const StyledLink = styled(NavLink)`
  color: black;
  text-decoration: none;
  &.active {
    color: red; // 활성 링크 색상
  }
  &:hover {
    font-size: 1.1em; // 호버 시 글자 크기 증가
    cursor: pointer; // 커서 모양 변경
  }
`;

