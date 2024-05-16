package com.secondaryif.demo.web.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class MemberReqDto {
    @Getter
    public static class JoinDto{
        @NotBlank
        String name;
    }

}
