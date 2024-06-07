package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.domain.neo4j.UploadGraph;
import com.secondaryif.demo.web.dto.upload.UploadPathDto;
import com.secondaryif.demo.web.dto.upload.UploadResDto;

public interface UploadQueryService {
    Upload getUpload(Long uploadId);
    UploadResDto.GetUploadResDto getUploadDto(Long startId);
    UploadResDto.GetUploadResDto getUploadDtoByFetchWeight(Long startId, Long endId);
    UploadResDto.GetUploadListResDto getUploadList(Long artifactId);
    UploadResDto.GetUploadListResDto getOriginUploadList(Long artifactId);
    UploadGraph getUploadGraph(Long uploadGraphId);
    UploadPathDto.PathDto getMaxWeightPathDto(Long artifactId);
}
