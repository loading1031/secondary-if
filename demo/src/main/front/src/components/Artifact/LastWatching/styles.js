import styled from "styled-components";

export const LastWatchingContainer = styled.section`
  background-color: #ffffff;
  padding: 15px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

export const Title = styled.h1`
  color: #333333;
  font-size: 24px;
`;

export const Content = styled.p`
  color: #666666;
  font-size: 16px;
`;

export const NextBox = styled.div`
  width: 100%;
  background: rgba(255, 255, 255, 0.9); // 반투명 배경
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 4px;
  margin-top: 0.5rem;
  z-index: 100; // z-index를 높게 설정하여 다른 요소 위에 표시
`;

export const NextItem = styled.li`
  font-size: 0.7rem;
  padding: 10px 1rem;
  list-style: none;
  
  border-bottom: 1px solid #ccc;

  &:last-child {
    border-bottom: none;
  }

  &:hover {
    background-color: #f7f7f7;
    cursor: pointer;
  }
`;

export const ResetButton = styled.button`
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
