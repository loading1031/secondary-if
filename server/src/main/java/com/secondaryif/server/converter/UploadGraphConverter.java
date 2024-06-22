package com.secondaryif.server.converter;

import com.secondaryif.server.domain.Upload;
import com.secondaryif.server.domain.neo4j.UploadGraph;
import com.secondaryif.server.web.dto.upload.UploadPathDto;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import java.util.List;
import java.util.Map;

public class UploadGraphConverter {
    public static UploadGraph toPost(Upload upload){
        return UploadGraph.builder()
                .id(upload.getId())
                .content(upload.getContent())
                .build();
    }
    public static UploadPathDto.NodeDto toNodeDto(Node node, Integer likeCount){
        return UploadPathDto.NodeDto.builder()
                .id(node.id())
                .properties(node.asMap())
                .likeCount(likeCount)
                .build();
    }
    public static UploadPathDto.RelationshipDto toRelationshipDto(
            UploadPathDto.NodeDto startNodeDto, UploadPathDto.NodeDto endNodeDto, Relationship relationship){
        return UploadPathDto.RelationshipDto.builder()
                .id(relationship.id())
                .type(relationship.type())
                .properties(relationship.asMap())
                .startNode(startNodeDto)
                .endNode(endNodeDto)
                .build();
    }
    public static UploadPathDto.PathDto convertPathToDto(
            Map<String,UploadPathDto.NodeDto> nodeDtoMap, List<UploadPathDto.RelationshipDto> relationshipDtos) {

        return UploadPathDto.PathDto.builder()
                .nodes(nodeDtoMap)
                .relationships(relationshipDtos)
                .build();
    }
}
