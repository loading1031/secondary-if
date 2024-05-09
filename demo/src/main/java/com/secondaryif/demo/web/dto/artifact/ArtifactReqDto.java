package com.secondaryif.demo.web.dto.artifact;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ArtifactReqDto {
    @Getter
    public static class PostDto{
        @NotBlank
        String title;
    }
}
