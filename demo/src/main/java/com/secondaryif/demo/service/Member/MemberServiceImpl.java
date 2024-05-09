package com.secondaryif.demo.service.Member;

import com.secondaryif.demo.apiPayload.code.status.ErrorStatus;
import com.secondaryif.demo.apiPayload.exception.GeneralException;
import com.secondaryif.demo.converter.Member.MemberConverter;
import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.repository.MemberRepository;
import com.secondaryif.demo.web.dto.member.MemberReqDto;
import com.secondaryif.demo.web.dto.member.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    @Override
    public MemberResDto.JoinResDto join(MemberReqDto.JoinDto request) {
        Member newMember = MemberConverter.toMember(request);

        return MemberConverter.toJoinResDto(memberRepository.save(newMember));
    }

    @Override
    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow(
                ()-> new GeneralException(ErrorStatus._BAD_REQUEST));
    }
}
