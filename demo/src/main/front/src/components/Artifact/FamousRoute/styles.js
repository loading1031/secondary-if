import styled from "styled-components";

export const Route = styled.div`
  width: 50%;
  background: rgba(255, 255, 255, 0.9); // 반투명 배경
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 4px;
  margin: 1rem;
  padding: 1rem;
  overflow-y: auto;
`;

export const ResetButton = styled.button`
  position: sticky;
  top: 0rem; // 상단에 고정
  z-index: 10; // 다른 컨텐츠 위에 표시되도록 z-index 설정

  background-color: #c92a2a;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  margin-bottom: 20px;

  &:hover {
    background-color: #a61e25;
  }
`;

export const Node = styled.li`
  font-size: 0.7rem;
  padding: 10px 1rem;
  list-style: none;

  border-bottom: 1px solid #ccc;

  &:last-child {
    border-bottom: none;
    margin-bottom: 1rem;
  }

  &:hover {
    background-color: #f7f7f7;
    cursor: pointer;
  }
`;

export const Content = styled.div`
  margin: 1rem;
`;
