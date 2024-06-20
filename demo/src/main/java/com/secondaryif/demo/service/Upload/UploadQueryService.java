package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.domain.neo4j.UploadGraph;
import com.secondaryif.demo.web.dto.upload.UploadPathDto;
import com.secondaryif.demo.web.dto.upload.UploadResDto;

import java.util.List;

public interface UploadQueryService {
    Upload getUpload(Long uploadId);
    UploadResDto.GetUploadResDto getUploadDto(Long startId);
    UploadResDto.GetUploadResDto getUploadDtoByFetchWeight(Long startId, Long endId);
    List<UploadResDto.GetUploadResDto> getUploadList(Long artifactId);
    List<UploadResDto.GetUploadResDto> getOriginUploadList(Long artifactId);
    List<UploadResDto.GetUploadResDto> getTotalUploadGraphList(Long startUploadId);
    UploadGraph getUploadGraph(Long uploadGraphId);
    UploadPathDto.PathDto getMaxWeightPathDto(Long artifactId,Long endUploadId);
}
