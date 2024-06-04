package com.secondaryif.demo.service.Member;

import com.secondaryif.demo.apiPayload.code.status.ErrorStatus;
import com.secondaryif.demo.apiPayload.exception.GeneralException;
import com.secondaryif.demo.converter.MemberConverter;
import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.repository.MemberRepository;
import com.secondaryif.demo.web.dto.member.MemberReqDto;
import com.secondaryif.demo.web.dto.member.MemberResDto;
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
