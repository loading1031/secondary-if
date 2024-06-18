package com.secondaryif.demo.web.controller;

import com.secondaryif.demo.apiPayload.ApiResult;
import com.secondaryif.demo.service.Upload.UploadQueryService;
import com.secondaryif.demo.service.Upload.UploadService;
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
    @GetMapping("/{uploadGraphId}")
    ApiResult<?>getUploadGraph(@PathVariable(name = "uploadGraphId") Long uploadGraphId){
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

}
