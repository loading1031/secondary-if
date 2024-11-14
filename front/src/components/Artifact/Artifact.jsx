import styled from "styled-components";
import LastWatching from "./LastWatching/LastWatching";
import TheMostWatchingRoute from "./FamousRoute/TheMostWatchingRoute";
import CreateArtifact from "./CreateArtifact/CreateArtifact";
import CreateUpload from "./CreateUpload/CreateUpload";

const ArtifactInform = styled.div`
  display: flex;
  justify-content: space-around;
  background-color: #f0f0f0;
  border-radius: 8px;
  margin: 1rem;
  height: 40vh;
`;

function Artifact() {
  return (
    <div>
    <ArtifactInform>
      <LastWatching />
      <TheMostWatchingRoute/>
    </ArtifactInform>
    <ArtifactInform>
      <CreateArtifact/>
      <CreateUpload/>
    </ArtifactInform>
    </div>
  );
}
export default Artifact;
