package com.secondaryif.server.web.controller;

import com.secondaryif.server.global.apiPayload.ApiResult;
import com.secondaryif.server.service.Upload.UploadQueryService;
import com.secondaryif.server.service.Upload.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/uploads")
@Tag(name="Upload", description = "Upload Rest API")
public class UploadRestController {
    private final UploadService uploadService;
    private final UploadQueryService uploadQueryService;

    @PostMapping("/{uploadId}/like")
    @Operation(description = "로그인 및 회원가입")
    ApiResult<?> likeUpload(
            @PathVariable(name = "uploadId") Long uploadId,
            @RequestParam(name = "memberId") Long memberId){
        return ApiResult.onSuccess(uploadService.postLike(uploadId,memberId));
    }
    @GetMapping("/{uploadId}/graph")
    @Operation(description = "그래프DB 업로드 조회")
    ApiResult<?>getUploadGraph(@PathVariable(name = "uploadId") Long uploadGraphId){
        return ApiResult.onSuccess(uploadQueryService.getUploadGraph(uploadGraphId));
    }
    @GetMapping("/{uploadId}")
    @Operation(description = "SQLDB 업로드 조회")
    ApiResult<?> getUploadDto(
            @PathVariable(name="uploadId") Long uploadId,
            @RequestParam(name="prevUploadId",required=false, defaultValue="0") Long prevUploadId){

        if(prevUploadId != 0)
            return ApiResult.onSuccess(uploadQueryService.getUploadDtoByFetchWeight(prevUploadId,uploadId));
        else return ApiResult.onSuccess(uploadQueryService.ge