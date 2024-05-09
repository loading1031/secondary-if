package com.secondaryif.demo.service.Artifact;

import com.secondaryif.demo.web.dto.artifact.ArtifactReqDto;
import com.secondaryif.demo.web.dto.artifact.ArtifactResDto;

public interface ArtifactService {
    ArtifactResDto.PostResDto postArtifact(ArtifactReqDto.PostDto request, Long memberId);
}
