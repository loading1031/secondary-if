package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.apiPayload.code.status.ErrorStatus;
import com.secondaryif.demo.apiPayload.exception.GeneralException;
import com.secondaryif.demo.converter.UploadConverter;
import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.domain.mapping.UserLike;
import com.secondaryif.demo.repository.UploadRepository;
import com.secondaryif.demo.repository.UserLikeRepository;
import com.secondaryif.demo.service.Artifact.ArtifactQueryService;
import com.secondaryif.demo.service.Member.MemberService;
import com.secondaryif.demo.web.dto.upload.UploadReqDto;
import com.secondaryif.demo.web.dto.upload.UploadResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadServiceImpl implements UploadService{
    private final MemberService memberService;
    private final ArtifactQueryService artifactQueryService;
    private final UploadRepository uploadRepository;
    private final UserLikeRepository userLikeRepository;
    @Override
    @Transactional
    public UploadResDto.PostUploadResDto postUpload(Long memberId, Long artifactId, UploadReqDto.PostUploadDto request) {
        Member writer = memberService.getMember(memberId);
        Artifact artifact = artifactQueryService.getArtifact(artifactId);

        Upload newUpload = UploadConverter.toPost(writer, artifact, request.getContent());
        Upload prev = getUpload(request.getPrevId());

        newUpload.addParentNextUpload(prev);
        newUpload.setUpload(artifact,writer);

        return UploadConverter.toPostResDto(uploadRepository.save(newUpload));
    }
    @Override
    @Transactional
    public UploadResDto.GetUploadResDto postLike(Long uploadId,Long memberId){
        log.info("테스트1");
        //Todo: memberId를 못찾는 버그 있음
        Member reader = memberService.getMember(memberId);
        Upload upload = getUpload(uploadId);
        log.info("테스트2");
        UserLike userLike = UserLike.builder()
                .upload(upload)
                .member(reader)
                .build();
        log.info("테스트4");
        userLike.setLike(reader);
        userLikeRepository.save(userLike);
        log.info("테스트5");
        return UploadConverter.toGetResDto(upload, userLikeRepository.countByUpload(upload));
    }
    private Upload getUpload(Long uploadId) {
        return uploadRepository.findById(uploadId).orElseThrow(
                ()-> new GeneralException(ErrorStatus._BAD_REQUEST));
    }


}
