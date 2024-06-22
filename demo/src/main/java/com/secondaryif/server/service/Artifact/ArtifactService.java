package com.secondaryif.server.service.Artifact;

import com.secondaryif.server.web.dto.artifact.ArtifactReqDto;
import com.secondaryif.server.web.dto.artifact.ArtifactResDto;

public interface ArtifactService {
    ArtifactResDto.PostResDto postArtifact(ArtifactReqDto.PostDto request, Long memberId);
    ArtifactResDto.GetDetailsDto getArtifactWithOriginUploads(Long artifactId);
    ArtifactResDto.GetDetailsDto getArtifactWithTotalUploads(Long artifactId);
    ArtifactResDto.GetDetailsDto getArtifactWithTotalUploadGraphs(Long artifactId);
}
