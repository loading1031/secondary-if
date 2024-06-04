package com.secondaryif.demo.web.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class UploadResDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostUploadResDto{
        Long uploadId;
        String writer;
        LocalDateTime createdAt;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetUploadResDto{
        Long uploadId;
        String writer;
        String content;
        Integer likeCount;
        List<PostUploadResDto> children;
        LocalDateTime createdAt;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetUploadListResDto{
        List<GetUploadResDto> getUploadResDtoList;

    }
}
