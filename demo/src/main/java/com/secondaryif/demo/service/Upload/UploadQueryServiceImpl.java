package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.apiPayload.code.status.ErrorStatus;
import com.secondaryif.demo.apiPayload.exception.GeneralException;
import com.secondaryif.demo.converter.UploadConverter;
import com.secondaryif.demo.converter.UploadGraphConverter;
import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.domain.neo4j.UploadGraph;
import com.secondaryif.demo.repository.UploadRepository;
import com.secondaryif.demo.repository.UserLikeRepository;
import com.secondaryif.demo.repository.neo4j.CustomUploadGraphRepository;
import com.secondaryif.demo.repository.neo4j.UploadGraphRepository;
import com.secondaryif.demo.service.Artifact.ArtifactQueryService;
import com.secondaryif.demo.web.dto.upload.UploadPathDto;
import com.secondaryif.demo.web.dto.upload.UploadResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadQueryServiceImpl implements UploadQueryService{
    private final ArtifactQueryService artifactQueryService;
    private final UploadRepository uploadRepository;
    private final UserLikeRepository userLikeRepository;
    private final UploadGraphRepository uploadGraphRepository;
    private final CustomUploadGraphRepository customUploadGraphRepository;
    @Override
    public UploadResDto.GetUploadListResDto getOriginUploadList(Long artifactId) {
        return UploadConverter.toGetListResDto(
                uploadRepository.findAllByArtifactId(artifactId).stream()
                        .filter(upload -> upload.getMember() != null &&
                                upload.getMember().getId().equals(upload.getArtifact().getMember().getId()))
                        .map(upload ->
                            UploadConverter.toGetResDto(upload,userLikeRepository.countByUpload(upload))
                        )
                        .toList()
        );
    }
    @Override
    public UploadGraph getUploadGraph(Long uploadGraphId) {
        return uploadGraphRepository.findById(uploadGraphId).orElseThrow(
                ()->new GeneralException(ErrorStatus._NOT_FOUND));
    }

    @Override
    public UploadPathDto.PathDto getMaxWeightPathDto(Long artifactId) {
        Map<String, UploadPathDto.NodeDto> nodeDtoMap = new HashMap<>();
        List<UploadPathDto.RelationshipDto> relationshipDtos = new ArrayList<>();
        Path path = getMaxWeightPath(artifactId);

        for (Node node : path.nodes()) {
            // node의 properties에서 uploadId 추출 (예시에서는 'id' 키 사용)
            Map<String, Object> properties = node.asMap();
            Long uploadId = (Long) properties.get("id");  // 적절한 키로 변경 필요
            // userLikeRepository를 사용해 해당 uploadId의 좋아요 수 조회
            Integer likeCount = userLikeRepository.countByUpload(getUpload(uploadId));
            // NodeDto 객체 생성
            nodeDtoMap.put(String.valueOf(node.id()),
                    UploadGraphConverter.toNodeDto(node, likeCount));
        }
        for (Relationship relationship : path.relationships()){
            String startNodeId = relationship.startNodeElementId(); // Relationship의 시작 Node ID
            String endNodeId = relationship.endNodeElementId();     // Relationship의 종료 Node ID
            UploadPathDto.NodeDto startNodeDto = nodeDtoMap.get(startNodeId.substring(startNodeId.lastIndexOf(':') + 1));
            UploadPathDto.NodeDto endNodeDto = nodeDtoMap.get(endNodeId.substring(startNodeId.lastIndexOf(':') + 1));

            if (startNodeDto == null || endNodeDto == null) {
                throw new RuntimeException("노드 정보가 누락되었습니다: " + startNodeId + " 또는 " + endNodeId);
            }

            relationshipDtos.add(UploadGraphConverter.toRelationshipDto(startNodeDto, endNodeDto, relationship));
        }
        return UploadGraphConverter.convertPathToDto(nodeDtoMap,relationshipDtos);
    }


    @Override
    public Upload getUpload(Long uploadId) {
        return uploadRepository.findById(uploadId).orElseThrow(
                ()-> new GeneralException(ErrorStatus._BAD_REQUEST));
    }
    @Override
    public UploadResDto.GetUploadListResDto getUploadList(Long artifactId) {
        return UploadConverter.toGetListResDto(
                uploadRepository.findAllByArtifactId(artifactId).stream()
                        .filter(upload -> upload.getMember() != null)
                        .map(upload -> UploadConverter.toGetResDto(upload, userLikeRepository.countByUpload(upload)))
                        .toList()
        );
    }
    private Path getMaxWeightPath(Long artifactId) {
        Artifact artifact = artifactQueryService.getArtifact(artifactId);
        List<Upload> uploadList = artifact.getUploadList();

        return customUploadGraphRepository.findMaxWeightPath(
                uploadList.get(0).getId(),uploadList.get(uploadList.size() - 1).getId()
        ).orElseThrow(()-> new GeneralException(ErrorStatus._NOT_FOUND));
    }
}
