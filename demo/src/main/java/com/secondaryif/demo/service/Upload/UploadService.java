package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.web.dto.upload.UploadReqDto;
import com.secondaryif.demo.web.dto.upload.UploadResDto;

public interface UploadService {
    UploadResDto.PostUploadResDto postUpload(Long memberId, Long artifactId, UploadReqDto.PostUploadDto request);
    UploadResDto.GetUploadResDto postLike(Long uploadId,Long memberId);
    UploadResDto.GetUploadResDto patchUploadChild(Long uploadId, Long childId);
}
