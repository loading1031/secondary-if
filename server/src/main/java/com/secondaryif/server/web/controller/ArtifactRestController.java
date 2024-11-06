package com.secondaryif.server.web.controller;

import com.secondaryif.server.global.apiPayload.ApiResult;
import com.secondaryif.server.service.Artifact.ArtifactQueryService;
import com.secondaryif.server.service.Artifact.ArtifactService;
import com.secondaryif.server.service.Upload.UploadQueryService;
import com.secondaryif.server.service.Upload.UploadService;
import com.secondaryif.server.web.dto.artifact.ArtifactResDto;
import com.secondaryif.server.web.dto.upload.UploadReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artifacts")
@Tag(name="Artifact",description = "Artifact Rest API")
public class ArtifactRestController {
    private final ArtifactService artifactService;
    private final ArtifactQueryService artifactQueryService;
    private final UploadService uploadService;
    private final UploadQueryService uploadQueryService;
    @GetMapping("")
    @Operation(description = "작품 리스트 조회")
    ApiResult<List<ArtifactResDto.PostResDto>>getArtList(){
        return ApiResult.onSuccess(artifactQueryService.getArtifactList());
    }
    @PostMapping("/{artifactId}/upload")
    @Operation(description = "업로드 등록")
    ApiResult<?>postUpload(
            @RequestParam(name = "memberId")Long memberId,
            @PathVariable(name = "artifactId") Long artifactId,
            @RequestBody UploadReqDto.PostUploadDto request) {
       return ApiResult.onSuccess(uploadService.postUpload(memberId, artifactId, request));
    }
    @GetMapping("/{artifactId}/original")
    @Operation(description = "원작 조회")
    ApiResult<?>getArtWithOriginUploadList(@PathVariable(name = "artifactId") Long artifactId){
        return ApiResult.onSuccess(artifactService.getArtifactWithOriginUploads(artifactId));
    }
    @GetMapping("/{artifactId}")
    @Operation(description = "원작/팬아트 전체 조회")
    ApiResult<?>getArtWithTotalUploadList(@PathVariable(name = "artifactId") Long artifactId){
        return ApiResult.onSuccess(artifactService.getArtifactWithTotalUploads(artifactId));
    }
    @GetMapping("/{artifactId}/graph")
    @Operation(description = "그래프 DB에서 작품 조회")
    ApiResult<?>getArtWithTotalUploadGraphList(@PathVariable(name = "artifactId") Long artifactId){
        return ApiResult.onSuccess(artifactService.getArtifactWithTotalUploadGraphs(artifactId));
    }
    @GetMapping("/{artifactId}/uploads/{endUploadId}/weight")
    @Operation(description = "최대 가중치 그래프 탐색 방식의 업로드 조회")
    ApiResult<?> getMaxWeightGraph(
            @PathVariable(name="artifactId") Long artifactId,
            @PathVariabl