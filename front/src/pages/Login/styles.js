import styled from "styled-components";

export const FormContainer = styled.form`
  display: flex;
  flex-direction: column;
  gap: 10px;
  justify-content: center; // 세로 방향 중앙 정렬
  text-align: center;
`;

export const StyledFieldset = styled.fieldset`
  border: none; // 테두리 제거
  position: relative; // 상대 위치 설정
  margin-top: 5%;
  margin-left: 35%;
  margin-right: 35%;
  padding-top: 3%; // 상단 테두리와 legend 사이의 공간 확보
  padding-left: 0;
  padding-right: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
  justify-content: center; // 세로 방향 중앙 정렬
  text-align: left;
`;

export const StyledLegend = styled.legend`
  padding: 0 5px; // 텍스트 주변 패딩
  width: auto;
  margin: 0px auto; // 자동 마진으로 중앙 정렬, absolute 없이
  color: white;
  font-size: 30px;
`;

export const StyledInput = styled.input`
  margin: 5px;
  padding: 15px;
  box-sizing: border-box;
  border-radius: 20px;
`;

export const WarningP = styled.small`
  margin-left: 3%;
  color: red;
`;

export const StyledButton = styled.button`
  margin: 15px 1vw;
  padding: 20px;
  font-size: 20px;
  font-color: #22264c;
  box-sizing: border-box;
  border-radius: 20px;
  background-color: ${(props) => (props.disabled ? "" : "yellow")};
`;