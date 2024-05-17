package com.secondaryif.demo.service.Artifact;

import com.secondaryif.demo.apiPayload.code.status.ErrorStatus;
import com.secondaryif.demo.apiPayload.exception.GeneralException;
import com.secondaryif.demo.converter.ArtifactConverter;
import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.repository.ArtifactRepository;
import com.secondaryif.demo.web.dto.artifact.ArtifactResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtifactQueryServiceImpl implements ArtifactQueryService{
    private final ArtifactRepository artifactRepository;
    @Override
    public Artifact getArtifact(Long artifactId) {
        return artifactRepository.findById(artifactId).orElseThrow(
                () -> new GeneralException(ErrorStatus._BAD_REQUEST));
    }
    @Override
    public List<ArtifactResDto.PostResDto> getArtifactList() {
        return ArtifactConverter.getArtDtoList(artifactRepository.findAll());
    }
}
