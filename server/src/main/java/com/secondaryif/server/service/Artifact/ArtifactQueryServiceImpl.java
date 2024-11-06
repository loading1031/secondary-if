package com.secondaryif.server.service.Artifact;

import com.secondaryif.server.global.apiPayload.code.status.ErrorStatus;
import com.secondaryif.server.global.apiPayload.exception.GeneralException;
import com.secondaryif.server.converter.ArtifactConverter;
import com.secondaryif.server.domain.Artifact;
import com.secondaryif.server.repository.ArtifactRepository;
import com.secondaryif.server.web.dto.artifact.ArtifactResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
    @Override
    public ArtifactResDto.SearchArtifacts searchArtifactList(String title,String author, Integer page) {
        if(title==null&&author==null)
            throw new GeneralException(ErrorStatus._BAD_REQUEST);
        Page<Artifact> byTitleOrAuthorName = artifactRepository.findByTitleContainingOrMemberNameContaining(title, author, PageRequest.of(page, 10));
        return ArtifactConverter.geSearchList(byTitleOrAuthorName);
    }
}
