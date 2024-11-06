package com.secondaryif.server.web.controller;

import com.secondaryif.server.global.apiPayload.ApiResult;
import com.secondaryif.server.global.security.dto.JwtTokenDTO;
import com.secondaryif.server.service.Artifact.ArtifactService;
import com.secondaryif.server.service.Member.MemberService;
import com.secondaryif.server.web.dto.artifact.ArtifactReqDto;
import com.secondaryif.server.web.dto.artifact.ArtifactResDto;
import com.secondaryif.server.web.dto.member.MemberReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name="Member",description = "Member Rest API")
public class MemberRestController {
    private final MemberService memberService;
    private final ArtifactService artifactService;

    @PostMapping("")
    @Operation(description = "로그인 및 회원가입")
    ApiResult<JwtTokenDTO> loginAndJoin(@RequestBody @Valid MemberReqDto.JoinDto request) {
        JwtTokenDTO jwtTokenDTO = memberService.login(request);

        return ApiResult.onSuccess(jwtTokenDTO);
    }

    @PostMapping("/{memberId}/artifact")
    @Operation(description = "작품 생성")
    ApiResult<ArtifactResDto.PostResDto> createArtifact(
            @PathVariable(name = "memberId") Long memberId,
            @RequestBody @Valid ArtifactReqDto.PostDto request){
        return ApiResult.onSuccess(artifactService.postArtifact(request, memberId));
    }
    @GetMapping("")
    @Operation(description = "유저 리스트 조회")
    ApiResult<?> getMemberList(){
        return ApiResult.onSuccess(memberService.getMemberList());
    }

}
