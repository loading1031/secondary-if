package com.secondaryif.server.web.dto.member;

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
        String name;
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
