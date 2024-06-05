package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.domain.neo4j.UploadGraph;
import com.secondaryif.demo.web.dto.upload.UploadResDto;

import java.util.List;

public interface UploadQueryService {
    Upload getUpload(Long uploadId);
    UploadResDto.GetUploadListResDto getUploadList(Long artifactId);
    UploadResDto.GetUploadListResDto getOriginUploadList(Long artifactId);
    UploadGraph getUploadGraph(Long uploadGraphId);
}
