import { useSelector } from "react-redux";
import axios from "axios";
import {
  Content,
  LastWatchingContainer,
  NextBox,
  NextItem,
  ResetButton,
  Title,
} from "./styles";
import { useEffect, useState } from "react";

function LastWatching() {
  const artifact = useSelector((state) => state.search.finalSearchRes);
  const [uploadList, setUploadList] = useState([]);
  const [lastWatching, setLastWatching] = useState(null);
  const [records, setRecords] = useState([]);

  useEffect(() => {
    if (!artifact) {
      return; // artifact가 없는 경우 아무것도 하지 않음
    }

    const fetchUploads = async () => {
      try {
        const response = await axios.get(
          `/api/artifacts/${artifact.artifactId}`
        );
        setUploadList(response.data.result.getUploadResDtoList); // API 호출 결과로 상태 업데이트
        setLastWatching(response.data.result.getUploadResDtoList[0]); // 초기값 설정
      } catch (error) {
        console.error("API 요청 중 오류가 발생했습니다:", error);
      }
    };

    fetchUploads();
  }, [artifact]); // artifact가 변경될 때마다 실행

  const handleSearch = async (uploadId) => {
    if (!uploadId) {
      console.error("Invalid uploadId provided:", uploadId);
      return; // 유효하지 않은 ID가 주어지면 함수 실행 중단
    }
    try {
      const response = await axios.get(
        `/api/uploads/${uploadId}?prevUploadId=${lastWatching.uploadId}`
      );
      setRecords((prevRecords) => [...prevRecords, response.data.result]); // Todo: lastWatching 배열 추가
      setLastWatching(response.data.result); // API 호출 결과로 상태 업데이트
    } catch (error) {
      console.error("API 요청 중 오류가 발생했습니다:", error);
    }
    handleSearch();
  };

  console.log("artifact:", artifact);
  console.log("Upload List:", uploadList);
  console.log("Records:", records);
  console.log("lastWatching:", lastWatching);

  const resetView = () => {
    if (uploadList.length > 0) {
      setLastWatching(uploadList[0]);
    }
  };

  return (
      <LastWatchingContainer>
        <ResetButton onClick={resetView}>Reset</ResetButton>
        {artifact && lastWatching ? (
          <>
            <Title>{artifact.title}</Title>
            <Content>{lastWatching.content}</Content>
            {lastWatching.children && (
              <NextBox>
                {lastWatching.children.map((next) => (
                  <NextItem
                    key={next.uploadId}
                    onClick={() => handleSearch(next.uploadId)}
                  >
                    {next.content}
                  </NextItem>
                ))}
              </NextBox>
            )}
          </>
        ) : (
          <>
            <Title>소설 제목</Title>
            <Content>내용</Content>
          </>
        )}
      </LastWatchingContainer>
  );
}
export default LastWatching;