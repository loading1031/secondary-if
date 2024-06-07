package com.secondaryif.demo.web.controller;

import com.secondaryif.demo.apiPayload.ApiResult;
import com.secondaryif.demo.service.Artifact.ArtifactQueryService;
import com.secondaryif.demo.service.Artifact.ArtifactService;
import com.secondaryif.demo.service.Upload.UploadQueryService;
import com.secondaryif.demo.service.Upload.UploadService;
import com.secondaryif.demo.web.dto.artifact.ArtifactResDto;
import com.secondaryif.demo.web.dto.upload.UploadReqDto;
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
    @PostMapping("/uploads/{uploadId}/like")
    ApiResult<?>likeUpload(
            @PathVariable(name = "uploadId") Long uploadId,
            @RequestParam(name = "memberId") Long memberId){
        return ApiResult.onSuccess(uploadService.postLike(uploadId,memberId));
    }
    @GetMapping("/uploadGraphs/{uploadGraphId}")
    ApiResult<?>getUploadGraph(@PathVariable(name = "uploadGraphId") Long uploadGraphId){
        return ApiResult.onSuccess(uploadQueryService.getUploadGraph(uploadGraphId));
    }
    @GetMapping("/{artifactId}/uploads/famous")
    ApiResult<?> getMaxWeightGraph(@PathVariable(name="artifactId") Long artifactId){
        return ApiResult.onSuccess(uploadQueryService.getMaxWeightPathDto(artifactId));
    }
    @GetMapping("/{artifactId}/uploads/{uploadId}")
    ApiResult<?> getUploadDto(
            @PathVariable(name="artifactId") Long artifactId,
            @PathVariable(name="uploadId") Long uploadId,
            @RequestParam(name="prevUploadId",required=false, defaultValue="0") Long prevUploadId){

        if(prevUploadId != 0)
            return ApiResult.onSuccess(uploadQueryService.getUploadDtoByFetchWeight(prevUploadId,uploadId));
        else return ApiResult.onSuccess(uploadQueryService.getUploadDto(uploadId));
    }

}
