package com.secondaryif.server.service.Member;

import com.secondaryif.server.domain.Member;
import com.secondaryif.server.web.dto.member.MemberReqDto;
import com.secondaryif.server.web.dto.member.MemberResDto;

import java.util.List;

public interface MemberService {
    MemberResDto.JoinResDto join(MemberReqDto.JoinDto request);
    Member getMember(Long id);
    List<MemberResDto.JoinResDto> getMemberList();
}
