package com.secondaryif.server.service.Upload;

import com.secondaryif.server.web.dto.upload.UploadReqDto;
import com.secondaryif.server.web.dto.upload.UploadResDto;

public interface UploadService {
    UploadResDto.PostUploadResDto postUpload(Long artifactId, UploadReqDto.PostUploadDto request);
    UploadResDto.GetUploadResDto postLike(Long uploadId);
    UploadResDto.GetUploadResDto patchUploadChild(Long uploadId, Long childId);
}
