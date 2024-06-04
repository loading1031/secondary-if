package com.secondaryif.demo.service.Upload;

import com.secondaryif.demo.apiPayload.code.status.ErrorStatus;
import com.secondaryif.demo.apiPayload.exception.GeneralException;
import com.secondaryif.demo.converter.UploadConverter;
import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.repository.UploadRepository;
import com.secondaryif.demo.repository.UserLikeRepository;
import com.secondaryif.demo.web.dto.upload.UploadResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadQueryServiceImpl implements UploadQueryService{
    private final UploadRepository uploadRepository;
    private final UserLikeRepository userLikeRepository;
    @Override
    public UploadResDto.GetUploadListResDto getOriginUploadList(Long artifactId) {
        return UploadConverter.toGetListResDto(
                uploadRepository.findAllByArtifactId(artifactId).stream()
                        .filter(upload -> upload.getMember() != null &&
                                upload.getMember().getId().equals(upload.getArtifact().getMember().getId()))
                        .map(upload ->
                            UploadConverter.toGetResDto(upload,userLikeRepository.countByUpload(upload))
                        )
                        .toList()
        );
    }
    @Override
    public Upload getUpload(Long uploadId) {
        return uploadRepository.findById(uploadId).orElseThrow(
                ()-> new GeneralException(ErrorStatus._BAD_REQUEST));
    }
    @Override
    public UploadResDto.GetUploadListResDto getUploadList(Long artifactId) {
        return UploadConverter.toGetListResDto(
                uploadRepository.findAllByArtifactId(artifactId).stream()
                .map(upload -> UploadConverter.toGetResDto(upload, userLikeRepository.countByUpload(upload)))
                .toList()
        );
    }
}
