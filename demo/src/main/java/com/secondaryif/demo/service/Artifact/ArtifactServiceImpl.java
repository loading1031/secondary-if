package com.secondaryif.demo.service.Artifact;

import com.secondaryif.demo.apiPayload.code.status.ErrorStatus;
import com.secondaryif.demo.apiPayload.exception.GeneralException;
import com.secondaryif.demo.converter.ArtifactConverter;
import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.repository.ArtifactRepository;
import com.secondaryif.demo.repository.UploadRepository;
import com.secondaryif.demo.service.Member.MemberService;
import com.secondaryif.demo.service.Upload.UploadQueryService;
import com.secondaryif.demo.web.dto.artifact.ArtifactReqDto;
import com.secondaryif.demo.web.dto.artifact.ArtifactResDto;
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

    @Override
    @Transactional
    public ArtifactResDto.PostResDto postArtifact(ArtifactReqDto.PostDto request, Long memberId) {
        Member getMember = memberService.getMember(memberId);
        Artifact newArtifact = ArtifactConverter.toPost(request,getMember);
        newArtifact.setArtifact(getMember);

        uploadRepository.save(Upload.builder()
                .artifact(newArtifact)
                .content("루트 페이지입니다.").build());
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

    private Artifact getArtifact(Long artifactId) {
        return artifactRepository.findById(artifactId).orElseThrow(
                () -> new GeneralException(ErrorStatus._BAD_REQUEST));
    }
}
