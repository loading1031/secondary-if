package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.apiPayload.code.status.ErrorStatus;
import com.secondaryif.demo.apiPayload.exception.GeneralException;
import com.secondaryif.demo.converter.UploadConverter;
import com.secondaryif.demo.converter.UploadGraphConverter;
import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.domain.mapping.UserLike;
import com.secondaryif.demo.domain.neo4j.UploadGraph;
import com.secondaryif.demo.repository.UploadRepository;
import com.secondaryif.demo.repository.UserLikeRepository;
import com.secondaryif.demo.repository.neo4j.UploadGraphRepository;
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

        UploadGraph newUploadGraph = UploadGraphConverter.toPost(h2Upload);
        UploadGraph parent = getUploadGraph(request.getPrevId());
        newUploadGraph.setToParentRelationShip(parent, 0); // weight는 조회량으로 늘어남

        uploadGraphRepository.save(newUploadGraph).getParentRelationShips().
                forEach(relationShip-> log.info("########Setting parent########: Parent ID = {}, Child ID = {}",
                    relationShip.getParent().getId(), relationShip.getChild().getId()));
        return UploadConverter.toPostResDto(newUpload);
    }
    @Transactional("chainedTransactionManager")
    public UploadGraph postUpload2(Long memberId, Long artifactId, UploadReqDto.PostUploadDto request) {
        Member writer = memberService.getMember(memberId);
        Artifact artifact = artifactQueryService.getArtifact(artifactId);
        // h2 저장
        Upload newUpload = UploadConverter.toPost(writer, artifact, request.getContent());
        Upload prev = getUpload(request.getPrevId());
        newUpload.addParentNextUpload(prev);
        newUpload.setUpload(artifact,writer);
        Upload h2Upload = uploadRepository.save(newUpload);
        //  neo4j 저장
        UploadGraph newUploadGraph = UploadGraphConverter.toPost(h2Upload);
        UploadGraph parent = getUploadGraph(request.getPrevId());
        newUploadGraph.setToParentRelationShip(parent, 0); // weight는 조회량으로 늘어남
        UploadGraph newNode = uploadGraphRepository.save(newUploadGraph);
        newNode.getParentRelationShips().forEach(relationShip-> log.info("########Setting parent########: Parent ID = {}, Child ID = {}",
                        relationShip.getParent().getId(), relationShip.getChild().getId()));
        return getUploadGraph(newNode.getId());
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
    private Upload getUpload(Long uploadId) {
        return uploadRepository.findById(uploadId).orElseThrow(
                ()-> new GeneralException(ErrorStatus._BAD_REQUEST));
    }
    private UploadGraph getUploadGraph(Long uploadGraphId){
        return uploadGraphRepository.findById(uploadGraphId).orElseThrow(()
                -> new GeneralException(ErrorStatus._NOT_FOUND));
    }


}
