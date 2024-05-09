package com.secondaryif.demo.converter.Member;

import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.web.dto.member.MemberReqDto;
import com.secondaryif.demo.web.dto.member.MemberResDto;

public class MemberConverter {

    public static Member toMember(MemberReqDto.JoinDto request) {

        return Member.builder()
                .name(request.getName())
                .build();
    }

    public static MemberResDto.JoinResDto toJoinResDto(Member member){

        return MemberResDto.JoinResDto.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
