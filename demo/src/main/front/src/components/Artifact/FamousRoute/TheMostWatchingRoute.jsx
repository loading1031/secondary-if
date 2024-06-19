import { useSelector } from "react-redux";
import { useState, useEffect } from "react";
import axios from "axios";
import { Route, Node, Content,ResetButton } from "./styles";

function TheMostWatchingRoute() {
  const artifact = useSelector((state) => state.search.finalSearchRes);
  const [endUploadId, setEndUploadId] = useState(null);
  const [maxWeightRoute, setMaxWeightRoute] = useState({ nodes: {} }); // Initialize nodes as an empty object
  const [totalUploads, setTotalUploads] = useState([]);

  useEffect(() => {
    if (!artifact || !endUploadId) {
      return; // artifact가 없거나 endUploadId가 없으면 아무것도 하지 않음
    }

    const fetchUploads = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/artifacts/${artifact.artifactId}/uploads/${endUploadId}/weight?`
        );
        setMaxWeightRoute(response.data.result); // API 호출 결과로 상태 업데이트
        setTotalUploads([]);
      } catch (error) {
        console.error("API 요청 중 오류가 발생했습니다:", error);
        setMaxWeightRoute({ nodes: {} }); // Reset to default on error
      }
    };

    fetchUploads();
  }, [artifact, endUploadId]);

  useEffect(() => {
    if (!artifact || endUploadId) {
      return; // artifact가 없거나 endUploadId가 있으면 아무것도 하지 않음
    }

    const fetchTotalUploads = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/artifacts/${artifact.artifactId}`
        );
        setTotalUploads(response.data.result.getUploadResDtoList); // API 호출 결과로 상태 업데이트
      } catch (error) {
        console.error("API 요청 중 오류가 발생했습니다:", error);
      }
    };

    fetchTotalUploads();
  }, [artifact, endUploadId]);

  console.log("maxWeightRoute:", maxWeightRoute);
  console.log("totalUploads:", totalUploads);

  return (
    <Route>
      <ResetButton
        onClick={() => {
          setEndUploadId(null);
          setTotalUploads([]);
          setMaxWeightRoute({ nodes: {} }); // Reset to default on error
        }}
      >
        Reset
      </ResetButton>
      <Content>
        {maxWeightRoute &&
          Object.keys(maxWeightRoute.nodes).length > 0 &&
          Object.entries(maxWeightRoute.nodes).map(([key, node]) => (
            <Node
              key={node.properties.id}
              onClick={() => setEndUploadId(node.properties.id)}
            >
              {node.properties.content}
            </Node>
          ))}
        {totalUploads &&
          totalUploads.map((upload) => (
            <Node
              key={upload.uploadId}
              onClick={() => setEndUploadId(upload.uploadId)}
            >
              {upload.content}
            </Node>
          ))}
      </Content>
    </Route>
  );
}

export default TheMostWatchingRoute;
