package com.secondaryif.demo.web.controller;

import com.secondaryif.demo.apiPayload.ApiResult;
import com.secondaryif.demo.service.Artifact.ArtifactQueryService;
import com.secondaryif.demo.service.Artifact.ArtifactService;
import com.secondaryif.demo.service.Upload.UploadService;
import com.secondaryif.demo.web.dto.artifact.ArtifactResDto;
import com.secondaryif.demo.web.dto.upload.UploadReqDto;
import com.secondaryif.demo.web.dto.upload.UploadResDto;
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
    @GetMapping("")
    ApiResult<List<ArtifactResDto.PostResDto>>getArtList(){
        return ApiResult.onSuccess(artifactQueryService.getArtifactList());
    }
    @PostMapping("/{artifactId}/upload")
    ApiResult<UploadResDto.PostUploadResDto>postUpload(
            @RequestParam(name = "memberId")Long memberId,
            @PathVariable(name = "artifactId") Long artifactId,
            @RequestBody UploadReqDto.PostUploadDto request) {
        return ApiResult.onSuccess(uploadService.postUpload(memberId, artifactId, request));
    }
    @GetMapping("/{artifactId}")
    ApiResult<?>getArtWithOriginUploadList(@PathVariable(name = "artifactId") Long artifactId){
        return ApiResult.onSuccess(artifactService.getArtifactWithOriginUploads(artifactId));
    }
}
