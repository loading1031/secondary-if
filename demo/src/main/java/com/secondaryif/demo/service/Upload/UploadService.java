package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.web.dto.upload.UploadReqDto;
import com.secondaryif.demo.web.dto.upload.UploadResDto;

public interface UploadService {
    UploadResDto.PostUploadResDto postUpload(Long memberId, Long artifactId, UploadReqDto.PostUploadDto request);

}
