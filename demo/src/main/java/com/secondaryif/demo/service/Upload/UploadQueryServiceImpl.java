package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.apiPayload.code.status.ErrorStatus;
import com.secondaryif.demo.apiPayload.exception.GeneralException;
import com.secondaryif.demo.converter.UploadConverter;
import com.secondaryif.demo.converter.UploadGraphConverter;
import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.domain.neo4j.UploadGraph;
import com.secondaryif.demo.domain.neo4j.UploadRelationship;
import com.secondaryif.demo.repository.UploadRepository;
import com.secondaryif.demo.repository.mapping.UserLikeRepository;
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
import org.springframework.transaction.annotation.Transactional;

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
    public List<UploadResDto.GetUploadResDto> getOriginUploadList(Long artifactId) {
        return uploadRepository.findAllByArtifactId(artifactId).stream()
                        .filter(upload -> upload.getMember() != null &&
                                upload.getMember().getId().equals(upload.getArtifact().getMember().getId()))
                        .map(upload ->
                            UploadConverter.toGetResDto(upload,userLikeRepository.countByUpload(upload))
                        )
                        .toList();
    }

    @Override
    public List<UploadResDto.GetUploadResDto> getTotalUploadGraphList(Long artifactId) {
        Long firstUploadId = uploadRepository.findFirstByArtifactId(artifactId).orElseThrow(
                () -> new GeneralException(ErrorStatus._NOT_FOUND)).getId();
        log.info("firstUploadId:{}",firstUploadId);
        return uploadGraphRepository.findAllConnectedNodes(firstUploadId).stream()
                .map(uploadGraph -> {
                    log.info("uploadGraph:{}",uploadGraph.getId());
            Upload upload = getUpload(uploadGraph.getId());
            return UploadConverter.toGetResDto(upload, userLikeRepository.countByUpload(upload));
            }).toList();
    }

    @Override
    public UploadGraph getUploadGraph(Long uploadGraphId) {
        return uploadGraphRepository.findById(uploadGraphId).orElseThrow(
                ()->new GeneralException(ErrorStatus._NOT_FOUND));
    }
    @Override
    public UploadPathDto.PathDto getMaxWeightPathDto(Long artifactId, Long endUploadId) {
        Map<String, UploadPathDto.NodeDto> nodeDtoMap = new HashMap<>();
        List<UploadPathDto.RelationshipDto> relationshipDtos = new ArrayList<>();
        Path path = getMaxWeightPath(artifactId, endUploadId);

        for (Node node : path.nodes()) {
            log.info("nodeId:{}",node.id());
            // node의 properties에서 uploadId 추출 (예시에서는 'id' 키 사용)
            //Todo:
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
    public UploadResDto.GetUploadResDto getUploadDto(Long startId) {
        Upload upload = getUpload(startId);
        return UploadConverter.toGetResDto(upload, userLikeRepository.countByUpload(upload));
    }
    @Override
    @Transactional("neo4jTransactionManager")
    public UploadResDto.GetUploadResDto getUploadDtoByFetchWeight(Long startId, Long endId) {
        UploadGraph startNode = getUploadGraph(startId);
        Upload end = getUpload(endId);

        UploadRelationship relationship = getRelationship(startNode,endId);

        log.info("변경전: {}",relationship.getWeight());
        relationship.setWeight(relationship.getWeight()+1);
        //Todo: 직접 set을 안쓰려고했는데, 조회량으로 계속 바뀌어야해서 일단 set으로 설정.
        // 삭제하고 다시 간선을 만드는건 비효율적이고, 일단 이놈의 간선은 삭제가 안됨. remove를 써도 안먹힘.
        uploadGraphRepository.save(startNode);
        //uploadRelationshipRepository.save(relationship); -> 간선을 바로 저장하면, 아무 변화 없음.. 무조건 노드를 통해 접근해야함
        log.info("변경후: {}",getRelationship(startNode,endId).getWeight());

        return UploadConverter.toGetResDto(end, userLikeRepository.countByUpload(end));
    }
    @Override
    public List<UploadResDto.GetUploadResDto> getUploadList(Long artifactId) {
        return uploadRepository.findAllByArtifactId(artifactId).stream()
                        //.filter(upload -> upload.getMember() != null)
                        .map(upload -> UploadConverter.toGetResDto(upload, userLikeRepository.countByUpload(upload)))
                        .toList();
    }
    private Path getMaxWeightPath(Long artifactId, Long endUploadId) {
        Artifact artifact = artifactQueryService.getArtifact(artifactId);
        List<Upload> uploadList = artifact.getUploadList();
        log.info("uploadList.size():{}",uploadList.size());
        return customUploadGraphRepository.findMaxWeightPath(
                uploadList.get(0).getId(),endUploadId
        ).orElseThrow(()-> new GeneralException(ErrorStatus._NOT_FOUND));
    }
    private UploadRelationship getRelationship(UploadGraph node,Long endId ){
        return node.getChildRelationShips().stream()
                .filter(rel -> rel.getChild().getId().equals(endId))
                .findFirst()
                .orElseThrow(()->new RuntimeException("가중치를 조정할 간선을 못찾았습니다."));  // 혹은 적절한 예외 처리

    }
}
