package com.secondaryif.server.service.Member;

import com.secondaryif.server.global.apiPayload.code.status.ErrorStatus;
import com.secondaryif.server.global.apiPayload.exception.GeneralException;
import com.secondaryif.server.converter.MemberConverter;
import com.secondaryif.server.domain.Member;
import com.secondaryif.server.global.security.dto.JwtTokenDTO;
import com.secondaryif.server.global.security.dto.RefreshToken;
import com.secondaryif.server.global.security.service.JwtTokenProvider;
import com.secondaryif.server.repository.MemberRepository;
import com.secondaryif.server.repository.RefreshTokenRepository;
import com.secondaryif.server.web.dto.member.MemberReqDto;
import com.secondaryif.server.web.dto.member.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public JwtTokenDTO login(MemberReqDto.JoinDto request) {
        Member member = memberRepository.getMemberByName(request.getName()).orElseGet(() ->
                memberRepository.save(MemberConverter.toMember(request))
        );

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getName(),"N/A");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtTokenDTO jwtTokenDTO = jwtTokenProvider.generateToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(jwtTokenDTO.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        return jwtTokenDTO;
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
