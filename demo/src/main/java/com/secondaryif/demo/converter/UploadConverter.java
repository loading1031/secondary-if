package com.secondaryif.demo.converter;

import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.web.dto.upload.UploadResDto;

import java.util.List;

public class UploadConverter {
    public static Upload toPost(Member writer, Artifact artifact, String content){
        return Upload.builder()
                .member(writer)
                .artifact(artifact)
                .content(content)
                .build();
    }

    public static UploadResDto.PostUploadResDto toPostResDto(Upload upload){
        return UploadResDto.PostUploadResDto.builder()
                .uploadId(upload.getId())
                .writer(upload.getMember().getName())
                .createdAt(upload.getCreatedAt())
                .build();
    }

    public static UploadResDto.GetUploadResDto toGetResDto(Upload upload){
        List<UploadResDto.PostUploadResDto> postResDto = upload.getNextUploads().stream()
                .map(UploadConverter::toPostResDto).toList();

        return UploadResDto.GetUploadResDto.builder()
                .uploadId(upload.getId())
                .content(upload.getContent())
                .children(postResDto)
                .createdAt(upload.getCreatedAt())
                .build();
    }

    public static UploadResDto.GetUploadListResDto toGetListResDto(List<Upload> uploadList){
        List<UploadResDto.GetUploadResDto> uploadResDtos =
                uploadList.stream().map(UploadConverter::toGetResDto).toList();

        return UploadResDto.GetUploadListResDto.builder()
                .getUploadResDtoList(uploadResDtos)
                .build();
    }
}
