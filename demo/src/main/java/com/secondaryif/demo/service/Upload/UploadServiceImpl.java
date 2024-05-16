package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.apiPayload.code.status.ErrorStatus;
import com.secondaryif.demo.apiPayload.exception.GeneralException;
import com.secondaryif.demo.converter.UploadConverter;
import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.repository.UploadRepository;
import com.secondaryif.demo.service.Artifact.ArtifactService;
import com.secondaryif.demo.service.Member.MemberService;
import com.secondaryif.demo.web.dto.upload.UploadReqDto;
import com.secondaryif.demo.web.dto.upload.UploadResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadServiceImpl implements UploadService{
    private final MemberService memberService;
    private final ArtifactService artifactService;
    private final UploadRepository uploadRepository;
    @Override
    public UploadResDto.PostUploadResDto postUpload(Long memberId, Long artifactId, UploadReqDto.PostUploadDto request) {
        Member writer = memberService.getMember(memberId);
        Artifact artifact = artifactService.getArtifact(artifactId);

        Upload newUpload = UploadConverter.toPost(writer, artifact, request.getContent());
        Upload prev = getUpload(request.getPrevId());

        newUpload.addParentNextUpload(prev);
        newUpload.setUpload(artifact,writer);

        return UploadConverter.toPostResDto(uploadRepository.save(newUpload));
    }

    @Override
    public Upload getUpload(Long uploadId) {
        return uploadRepository.findById(uploadId).orElseThrow(
                ()-> new GeneralException(ErrorStatus._BAD_REQUEST));
    }
}
