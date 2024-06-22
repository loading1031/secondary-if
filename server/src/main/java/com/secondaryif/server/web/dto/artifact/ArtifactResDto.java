package com.secondaryif.server.web.dto.artifact;

import com.secondaryif.server.web.dto.upload.UploadResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ArtifactResDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResDto{
        Long artifactId;
        String title;
        String author;
        LocalDateTime createdAt;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetDetailsDto {
        Long artifactId;
        String title;
        String author;
        List<UploadResDto.GetUploadResDto> getUploadResDtoList;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchArtifacts{
        List<PostResDto> postResDtos;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

}
