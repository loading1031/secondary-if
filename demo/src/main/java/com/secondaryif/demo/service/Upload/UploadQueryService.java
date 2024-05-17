package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.web.dto.upload.UploadResDto;

import java.util.List;

public interface UploadQueryService {
    Upload getUpload(Long uploadId);
    List<UploadResDto.PostUploadResDto> getOriginUploadList(Long artifactId);
}
