package com.secondaryif.server.converter;

import com.secondaryif.server.domain.Artifact;
import com.secondaryif.server.domain.Member;
import com.secondaryif.server.domain.Upload;
import com.secondaryif.server.web.dto.upload.UploadResDto;

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
                .content(upload.getContent())
                .createdAt(upload.getCreatedAt())
                .build();
    }

    public static UploadResDto.GetUploadResDto toGetResDto(Upload upload, Integer likeCount){
        List<UploadResDto.PostUploadResDto> postResDto = upload.getNextUploads().stream()
                .map(UploadConverter::toPostResDto).toList();


        return UploadResDto.GetUploadResDto.builder()
                .writer(upload.getMember().getName())
                .uploadId(upload.getId())
                .likeCount(likeCount)
                .content(upload.getContent())
                .children(postResDto)
                .createdAt(upload.getCreatedAt())
                .build();
    }

    public static UploadResDto.GetUploadListResDto toGetListResDto(List<UploadResDto.GetUploadResDto> uploadList){
        return UploadResDto.GetUploadListResDto.builder()
                .getUploadResDtoList(uploadList)
                .build();
    }
}
