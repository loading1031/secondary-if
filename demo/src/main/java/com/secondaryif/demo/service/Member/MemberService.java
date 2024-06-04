package com.secondaryif.demo.service.Member;

import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.web.dto.member.MemberReqDto;
import com.secondaryif.demo.web.dto.member.MemberResDto;

import java.util.List;

public interface MemberService {
    MemberResDto.JoinResDto join(MemberReqDto.JoinDto request);
    Member getMember(Long id);
    List<MemberResDto.JoinResDto> getMemberList();
}
