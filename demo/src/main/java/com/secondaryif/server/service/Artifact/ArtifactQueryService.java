package com.secondaryif.server.service.Artifact;

import com.secondaryif.server.domain.Artifact;
import com.secondaryif.server.web.dto.artifact.ArtifactResDto;

import java.util.List;

public interface ArtifactQueryService {
    Artifact getArtifact(Long artifactId);
    List<ArtifactResDto.PostResDto> getArtifactList();
    ArtifactResDto.SearchArtifacts searchArtifactList(String title, String author, Integer page);
}
