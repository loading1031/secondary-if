import styled from "styled-components";
import LastWatching from "./LastWatching/LastWatching";
import TheMostWatchingRoute from "./FamousRoute/TheMostWatchingRoute";

const ArtifactInform = styled.div`
  display: flex;
  justify-content: space-between;
  background-color: #f0f0f0;
  border-radius: 8px;
  margin: 1rem;
  height: 40vh;
`;

function Artifact() {
  return (
    <ArtifactInform>
      <LastWatching />
      <TheMostWatchingRoute/>
    </ArtifactInform>
  );
}
export default Artifact;
