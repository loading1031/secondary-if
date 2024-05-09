package com.secondaryif.demo.web.controller.Member;

import com.secondaryif.demo.apiPayload.ApiResult;
import com.secondaryif.demo.service.Artifact.ArtifactService;
import com.secondaryif.demo.service.Member.MemberService;
import com.secondaryif.demo.web.dto.artifact.ArtifactReqDto;
import com.secondaryif.demo.web.dto.artifact.ArtifactResDto;
import com.secondaryif.demo.web.dto.member.MemberReqDto;
import com.secondaryif.demo.web.dto.member.MemberResDto;
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

}
