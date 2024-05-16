package com.secondaryif.demo.converter;

import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.web.dto.upload.UploadResDto;

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
}