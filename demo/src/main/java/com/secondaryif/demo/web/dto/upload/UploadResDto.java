package com.secondaryif.demo.web.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
