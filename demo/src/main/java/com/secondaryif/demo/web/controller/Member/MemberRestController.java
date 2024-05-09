package com.secondaryif.demo.web.controller.Member;

import com.secondaryif.demo.apiPayload.ApiResult;
import com.secondaryif.demo.service.Member.MemberService;
import com.secondaryif.demo.web.dto.member.MemberReqDto;
import com.secondaryif.demo.web.dto.member.MemberResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {
    private final MemberService memberService;

    @PostMapping("")
    ApiResult<MemberResDto.JoinResDto>
    join(@RequestBody @Valid MemberReqDto.JoinDto request) {
        return ApiResult.onSuccess(memberService.join(request));
    }



}
