package com.secondaryif.server.service.Upload;

import com.secondaryif.server.domain.Upload;
import com.secondaryif.server.domain.neo4j.UploadGraph;
import com.secondaryif.server.web.dto.upload.UploadPathDto;
import com.secondaryif.server.web.dto.upload.UploadResDto;

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
