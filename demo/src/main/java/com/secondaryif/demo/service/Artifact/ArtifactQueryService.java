package com.secondaryif.demo.service.Artifact;

import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.web.dto.artifact.ArtifactResDto;

import java.util.List;

public interface ArtifactQueryService {
    Artifact getArtifact(Long artifactId);
    List<ArtifactResDto.PostResDto> getArtifactList();
}
