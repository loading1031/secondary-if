package com.secondaryif.demo.web.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemberResDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResDto{
        Long memberId;
        LocalDateTime createdAt;
    }
    /*
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadResDto{
        Long uploadId;
        LocalDateTime createdAt;
    }
     */
}
