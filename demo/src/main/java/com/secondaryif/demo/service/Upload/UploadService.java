package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.web.dto.upload.UploadReqDto;
import com.secondaryif.demo.web.dto.upload.UploadResDto;
import org.springframework.transaction.annotation.Transactional;

public interface UploadService {
    UploadResDto.PostUploadResDto postUpload(Long memberId, Long artifactId, UploadReqDto.PostUploadDto request);

    @Transactional
    UploadResDto.GetUploadResDto postLike(Long memberId, Long artifactId);
}
