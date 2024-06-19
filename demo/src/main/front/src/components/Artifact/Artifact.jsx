import styled from "styled-components";
import LastWatching from "./LastWatching/LastWatching";
import FamousRoute from "./FamousRoute/FamousRoute";

const ArtifactInform = styled.div`
  display: flex;
  padding: 20px;
  background-color: #f0f0f0;
  border-radius: 8px;
  margin: 1rem;
  height: 40vh;
`;

function Artifact() {
  return (
    <ArtifactInform>
      <LastWatching />
      <FamousRoute />
    </ArtifactInform>
  );
}
export default Artifact;
