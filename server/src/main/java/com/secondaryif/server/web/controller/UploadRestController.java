package com.secondaryif.server.web.controller;

import com.secondaryif.server.global.apiPayload.ApiResult;
import com.secondaryif.server.service.Upload.UploadQueryService;
import com.secondaryif.server.service.Upload.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/uploads")
public class UploadRestController {
    private final UploadService uploadService;
    private final UploadQueryService uploadQueryService;

    @PostMapping("/{uploadId}/like")
    ApiResult<?> likeUpload(
            @PathVariable(name = "uploadId") Long uploadId,
            @RequestParam(name = "memberId") Long memberId){
        return ApiResult.onSuccess(uploadService.postLike(uploadId,memberId));
    }
    @GetMapping("/{uploadId}/graph")
    ApiResult<?>getUploadGraph(@PathVariable(name = "uploadId") Long uploadGraphId){
        return ApiResult.onSuccess(uploadQueryService.getUploadGraph(uploadGraphId));
    }
    @GetMapping("/{uploadId}")
    ApiResult<?> getUploadDto(
            @PathVariable(name="uploadId") Long uploadId,
            @RequestParam(name="prevUploadId",required=false, defaultValue="0") Long prevUploadId){

        if(prevUploadId != 0)
            return ApiResult.onSuccess(uploadQueryService.getUploadDtoByFetchWeight(prevUploadId,uploadId));
        else return ApiResult.onSuccess(uploadQueryService.getUploadDto(uploadId));
    }
    @PatchMapping("/{uploadId}")
    ApiResult<?> patchUploadWithChild(
            @PathVariable(name="uploadId") Long uploadId,
            @RequestParam(name="nextUploadId") Long nextUploadId
    ){
        return ApiResult.onSuccess(uploadService.patchUploadChild(uploadId,nextUploadId));
    }
}
