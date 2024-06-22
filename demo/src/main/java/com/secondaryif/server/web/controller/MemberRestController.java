package com.secondaryif.server.web.controller;

import com.secondaryif.server.apiPayload.ApiResult;
import com.secondaryif.server.service.Artifact.ArtifactService;
import com.secondaryif.server.service.Member.MemberService;
import com.secondaryif.server.web.dto.artifact.ArtifactReqDto;
import com.secondaryif.server.web.dto.artifact.ArtifactResDto;
import com.secondaryif.server.web.dto.member.MemberReqDto;
import com.secondaryif.server.web.dto.member.MemberResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {
    private final MemberService memberService;
    private final ArtifactService artifactService;

    @PostMapping("")
    ApiResult<MemberResDto.JoinResDto>
    join(@RequestBody @Valid MemberReqDto.JoinDto request) {
        return ApiResult.onSuccess(memberService.join(request));
    }
    @PostMapping("/{memberId}/artifact")
    ApiResult<ArtifactResDto.PostResDto> createArtifact(
            @PathVariable(name = "memberId") Long memberId,
            @RequestBody @Valid ArtifactReqDto.PostDto request){
        return ApiResult.onSuccess(artifactService.postArtifact(request, memberId));
    }
    @GetMapping("")
    ApiResult<?> getMemberList(){
        return ApiResult.onSuccess(memberService.getMemberList());
    }

}
