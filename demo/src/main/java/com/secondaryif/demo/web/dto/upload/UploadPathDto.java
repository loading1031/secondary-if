package com.secondaryif.demo.web.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public class UploadPathDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PathDto{
        private Map<String,NodeDto> nodes;
        private List<RelationshipDto> relationships;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NodeDto{
        private Long id;
        private Map<String, Object> properties;
        private Integer likeCount;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RelationshipDto {
        private Long id;
        private String type;
        private NodeDto startNode;
        private NodeDto endNode;
        private Map<String, Object> properties;
    }
}
