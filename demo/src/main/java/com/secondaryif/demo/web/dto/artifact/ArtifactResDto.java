package com.secondaryif.demo.web.dto.artifact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ArtifactResDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResDto{
        Long ArtifactId;
        String title;
        String author;
        LocalDateTime createdAt;
    }
}
