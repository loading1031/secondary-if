package com.secondaryif.server.service.Member;

import com.secondaryif.server.global.apiPayload.code.status.ErrorStatus;
import com.secondaryif.server.global.apiPayload.exception.GeneralException;
import com.secondaryif.server.converter.MemberConverter;
import com.secondaryif.server.domain.Member;
import com.secondaryif.server.repository.MemberRepository;
import com.secondaryif.server.web.dto.member.MemberReqDto;
import com.secondaryif.server.web.dto.member.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public MemberResDto.JoinResDto join(MemberReqDto.JoinDto request) {
        Member newMember = MemberConverter.toMember(request);

        return MemberConverter.toJoinResDto(memberRepository.save(newMember));
    }

    @Override
    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow(
                ()-> new GeneralException(ErrorStatus._NOT_FOUND));
    }
    @Override
    public List<MemberResDto.JoinResDto> getMemberList() {
        return memberRepository.findAll().stream()
                .map(MemberConverter::toJoinResDto)
                .toList();
    }
}
