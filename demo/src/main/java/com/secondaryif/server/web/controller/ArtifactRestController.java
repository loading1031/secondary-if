package com.secondaryif.server.web.controller;

import com.secondaryif.server.apiPayload.ApiResult;
import com.secondaryif.server.service.Artifact.ArtifactQueryService;
import com.secondaryif.server.service.Artifact.ArtifactService;
import com.secondaryif.server.service.Upload.UploadQueryService;
import com.secondaryif.server.service.Upload.UploadService;
import com.secondaryif.server.web.dto.artifact.ArtifactResDto;
import com.secondaryif.server.web.dto.upload.UploadReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artifacts")
public class ArtifactRestController {
    private final ArtifactService artifactService;
    private final ArtifactQueryService artifactQueryService;
    private final UploadService uploadService;
    private final UploadQueryService uploadQueryService;
    @GetMapping("")
    ApiResult<List<ArtifactResDto.PostResDto>>getArtList(){
        return ApiResult.onSuccess(artifactQueryService.getArtifactList());
    }
    @PostMapping("/{artifactId}/upload")
    ApiResult<?>postUpload(
            @RequestParam(name = "memberId")Long memberId,
            @PathVariable(name = "artifactId") Long artifactId,
            @RequestBody UploadReqDto.PostUploadDto request) {
       return ApiResult.onSuccess(uploadService.postUpload(memberId, artifactId, request));
    }
    @GetMapping("/{artifactId}/original")
    ApiResult<?>getArtWithOriginUploadList(@PathVariable(name = "artifactId") Long artifactId){
        return ApiResult.onSuccess(artifactService.getArtifactWithOriginUploads(artifactId));
    }
    @GetMapping("/{artifactId}")
    ApiResult<?>getArtWithTotalUploadList(@PathVariable(name = "artifactId") Long artifactId){
        return ApiResult.onSuccess(artifactService.getArtifactWithTotalUploads(artifactId));
    }
    @GetMapping("/{artifactId}/graph")
    ApiResult<?>getArtWithTotalUploadGraphList(@PathVariable(name = "artifactId") Long artifactId){
        return ApiResult.onSuccess(artifactService.getArtifactWithTotalUploadGraphs(artifactId));
    }
    @GetMapping("/{artifactId}/uploads/{endUploadId}/weight")
    ApiResult<?> getMaxWeightGraph(
            @PathVariable(name="artifactId") Long artifactId,
            @PathVariable(name="endUploadId") Long endUploadId){
        return ApiResult.onSuccess(uploadQueryService.getMaxWeightPathDto(artifactId,endUploadId));
    }
    @GetMapping("/search")
    ApiResult<?> searchArtifacts(
            @RequestParam(name="title",required = false) String title,
            @RequestParam(name="author",required = false) String author,
            @RequestParam(name="page", defaultValue= "0") Integer page){
        return ApiResult.onSuccess(artifactQueryService.searchArtifactList(title,author,page));
    }

}
