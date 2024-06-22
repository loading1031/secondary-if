package com.secondaryif.server.web.dto.upload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UploadReqDto {
    @Getter
    public static class PostUploadDto{
        @NotBlank
        String content;
        @NotBlank
        Long prevId;
    }
}
