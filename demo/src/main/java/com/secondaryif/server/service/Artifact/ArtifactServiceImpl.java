package com.secondaryif.server.service.Artifact;

import com.secondaryif.server.apiPayload.code.status.ErrorStatus;
import com.secondaryif.server.apiPayload.exception.GeneralException;
import com.secondaryif.server.converter.ArtifactConverter;
import com.secondaryif.server.domain.Artifact;
import com.secondaryif.server.domain.Member;
import com.secondaryif.server.domain.Upload;
import com.secondaryif.server.domain.neo4j.UploadGraph;
import com.secondaryif.server.repository.ArtifactRepository;
import com.secondaryif.server.repository.UploadRepository;
import com.secondaryif.server.repository.neo4j.UploadGraphRepository;
import com.secondaryif.server.service.Member.MemberService;
import com.secondaryif.server.service.Upload.UploadQueryService;
import com.secondaryif.server.web.dto.artifact.ArtifactReqDto;
import com.secondaryif.server.web.dto.artifact.ArtifactResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtifactServiceImpl implements ArtifactService{
    private final MemberService memberService;
    private final UploadQueryService uploadQueryService;
    private final ArtifactRepository artifactRepository;
    private final UploadRepository uploadRepository;
    private final UploadGraphRepository uploadGraphRepository;
    @Override
    @Transactional("chainedTransactionManager")
    public ArtifactResDto.PostResDto postArtifact(ArtifactReqDto.PostDto request, Long memberId) {
        Member getMember = memberService.getMember(memberId);
        Artifact newArtifact = ArtifactConverter.toPost(request,getMember);
        newArtifact.setArtifact(getMember);

        Upload root = Upload.builder()
                .artifact(newArtifact)
                .member(getMember)
                .content("루트 페이지입니다.")
                .build();
        uploadRepository.save(root);

        uploadGraphRepository.save(
                UploadGraph.builder()
                        .id(root.getId())
                        .content(root.getContent())
                        .build()
        );
        return ArtifactConverter.toPostResDto(artifactRepository.save(newArtifact));
    }

    @Override
    public ArtifactResDto.GetDetailsDto getArtifactWithOriginUploads(Long artifactId) {
        return ArtifactConverter.getDetailsDto(
                uploadQueryService.getOriginUploadList(artifactId),
                getArtifact(artifactId));
    }

    @Override
    public ArtifactResDto.GetDetailsDto getArtifactWithTotalUploads(Long artifactId) {
        return ArtifactConverter.getDetailsDto(
                uploadQueryService.getUploadList(artifactId),
                getArtifact(artifactId));
    }

    @Override
    public ArtifactResDto.GetDetailsDto getArtifactWithTotalUploadGraphs(Long artifactId) {
        return ArtifactConverter.getDetailsDto(
                uploadQueryService.getTotalUploadGraphList(artifactId),
                getArtifact(artifactId));
    }

    private Artifact getArtifact(Long artifactId) {
        return artifactRepository.findById(artifactId).orElseThrow(
                () -> new GeneralException(ErrorStatus._BAD_REQUEST));
    }
}
