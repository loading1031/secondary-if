import styled from "styled-components";

// HeadForm: 전체 폼을 감싸는 컴포넌트, 폼의 기본 스타일 설정
export const HeadForm = styled.form`
  display: flex;
  flex-direction: column;
  background-color: #666666;
  align-items: center;
  width: 100vw;
  height: 35vw;
  box-sizing: border-box; // 패딩을 포함한 너비 설정
`;

// HeadFieldset: 폼 내의 필드셋, 요소들을 가로로 정렬
export const HeadFieldset = styled.fieldset`
  display: flex;
  flex-direction: column;
  align-items: center;
  border: none;
  margin-top: 7rem;
  padding: 2rem;
  width: 80vw;
`;

// TitleLegend: 제목 레전드, 큰 글자로 표시
export const TitleLegend = styled.legend`
  color: white;
  font-size: 1.5rem;
  font-weight: bold;
  margin: 0px auto; // 자동 마진으로 중앙 정렬, absolute 없이
`;

export const Descript = styled.small`
  color: white;
`;

// Search: 검색 입력 필드
export const Search = styled.input`
  width: 50vw;
  padding: 8px;
  margin: 1.5rem 0; // 상하 여백
  border: 1px solid #ddd;
  border-radius: 4px; // 경계선 둥글게
`;

// SubmitBtn: 제출 버튼
export const SubmitBtn = styled.button`
  padding: 10px 20px;
  background-color: black;
  width: 30vw;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer; // 마우스 오버 시 포인터 변경
  transition: background-color 0.3s ease; // 색상 변화 애니메이션

  &:hover {
    background-color: darkblue; // 마우스 오버 시 배경색 변경
  }
`;
