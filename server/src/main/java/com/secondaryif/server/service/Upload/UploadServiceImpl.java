package com.secondaryif.server.service.Upload;

import com.secondaryif.server.global.apiPayload.code.status.ErrorStatus;
import com.secondaryif.server.global.apiPayload.exception.GeneralException;
import com.secondaryif.server.converter.UploadConverter;
import com.secondaryif.server.converter.UploadGraphConverter;
import com.secondaryif.server.domain.Artifact;
import com.secondaryif.server.domain.Member;
import com.secondaryif.server.domain.Upload;
import com.secondaryif.server.domain.mapping.UserLike;
import com.secondaryif.server.domain.neo4j.UploadGraph;
import com.secondaryif.server.repository.UploadRepository;
import com.secondaryif.server.repository.mapping.UserLikeRepository;
import com.secondaryif.server.repository.neo4j.UploadGraphRepository;
import com.secondaryif.server.service.Artifact.ArtifactQueryService;
import com.secondaryif.server.service.Member.MemberService;
import com.secondaryif.server.web.dto.upload.UploadReqDto;
import com.secondaryif.server.web.dto.upload.UploadResDto;
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
    private final UploadGraphRepository uploadGraphRepository;
    @Override
    @Transactional("chainedTransactionManager")
    public UploadResDto.PostUploadResDto postUpload(Long memberId, Long artifactId, UploadReqDto.PostUploadDto request) {
        Member writer = memberService.getMember(memberId);
        Artifact artifact = artifactQueryService.getArtifact(artifactId);
        // h2 저장
        Upload newUpload = UploadConverter.toPost(writer, artifact, request.getContent());
        Upload prev = getUpload(request.getPrevId());
        newUpload.addParentNextUpload(prev);
        newUpload.setUpload(artifact,writer);
        Upload h2Upload = uploadRepository.save(newUpload);
        //  neo4j 저장
        UploadGraph newUploadGraph = UploadGraphConverter.toPost(getUpload(h2Upload.getId()));
        UploadGraph parent = getUploadGraph(request.getPrevId());
        parent.setToChildRelationShip(newUploadGraph, 0); // weight는 조회량으로 늘어남
        uploadGraphRepository.save(parent);

        return UploadConverter.toPostResDto(newUpload);

    }
    @Override
    @Transactional
    public UploadResDto.GetUploadResDto postLike(Long uploadId,Long memberId){
        Member reader = memberService.getMember(memberId);
        Upload upload = getUpload(uploadId);
        UserLike userLike = UserLike.builder()
                .upload(upload)
                .member(reader)
                .build();
        userLike.setLike(reader);
        userLikeRepository.save(userLike);
        return UploadConverter.toGetResDto(upload, userLikeRepository.countByUpload(upload));
    }

    @Override
    @Transactional("chainedTransactionManager")
    public UploadResDto.GetUploadResDto patchUploadChild(Long uploadId, Long childId) {
        UploadGraph uploadGraph = getUploadGraph(uploadId);
        UploadGraph childGraph = getUploadGraph(childId);

        uploadGraph.setToChildRelationShip(childGraph,0);
        uploadGraphRepository.save(uploadGraph);

        Upload upload = getUpload(uploadId);
        getUpload(childId).addParentNextUpload(upload);
        return UploadConverter.toGetResDto(upload, userLikeRepository.countByUpload(upload));
    }

    private Upload getUpload(Long uploadId) {
        return uploadRepository.findById(uploadId).orElseThrow(
                ()-> new GeneralException(ErrorStatus._BAD_REQUEST));
    }
    private UploadGraph getUploadGraph(Long uploadGraphId){
        return uploadGraphRepository.findById(uploadGraphId).orElseThrow(()
                -> new GeneralException(ErrorStatus._NOT_FOUND));
    }


}
