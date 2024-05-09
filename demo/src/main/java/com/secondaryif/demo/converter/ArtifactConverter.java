package com.secondaryif.demo.converter;

import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.web.dto.artifact.ArtifactReqDto;
import com.secondaryif.demo.web.dto.artifact.ArtifactResDto;

public class ArtifactConverter {

    public static Artifact toPost(ArtifactReqDto.PostDto request, Member member){
        return Artifact.builder()
                .title(request.getTitle())
                .member(member)
                .build();
    }
    public static ArtifactResDto.PostResDto toPostResDto(Artifact artifact){
        return ArtifactResDto.PostResDto.builder()
                .title(artifact.getTitle())
                .author(artifact.getMember().getName())
                .createdAt(artifact.getCreatedAt())
                .build();
    }
}
