package com.secondaryif.server.converter;

import com.secondaryif.server.domain.Member;
import com.secondaryif.server.web.dto.member.MemberReqDto;
import com.secondaryif.server.web.dto.member.MemberResDto;

public class MemberConverter {

    public static Member toMember(MemberReqDto.JoinDto request) {

        return Member.builder()
                .name(request.getName())
                .build();
    }

    public static MemberResDto.JoinResDto toJoinResDto(Member member){

        return MemberResDto.JoinResDto.builder()
                .name(member.getName())
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
