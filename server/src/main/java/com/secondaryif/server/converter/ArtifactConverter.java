package com.secondaryif.server.converter;

import com.secondaryif.server.domain.Artifact;
import com.secondaryif.server.domain.Member;
import com.secondaryif.server.web.dto.artifact.ArtifactReqDto;
import com.secondaryif.server.web.dto.artifact.ArtifactResDto;
import com.secondaryif.server.web.dto.upload.UploadResDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class ArtifactConverter {

    public static Artifact toPost(ArtifactReqDto.PostDto request, Member member){
        return Artifact.builder()
                .title(request.getTitle())
                .member(member)
                .build();
    }
    public static ArtifactResDto.PostResDto toPostResDto(Artifact artifact){
        return ArtifactResDto.PostResDto.builder()
                .artifactId(artifact.getId())
                .title(artifact.getTitle())
                .author(artifact.getMember().getName())
                .createdAt(artifact.getCreatedAt())
                .build();
    }
    public static List<ArtifactResDto.PostResDto> getArtDtoList(List<Artifact> artifactList){
        return artifactList.stream()
                .map(ArtifactConverter::toPostResDto)
                .toList();
    }

    public static ArtifactResDto.GetDetailsDto getDetailsDto(
            List<UploadResDto.GetUploadResDto> uploadResDtos, Artifact artifact){
        return ArtifactResDto.GetDetailsDto.builder()
                .artifactId(artifact.getId())
                .title(artifact.getTitle())
                .author(artifact.getMember().getName())
                .getUploadResDtoList(uploadResDtos)
                .build();
    }

    public static ArtifactResDto.SearchArtifacts geSearchList(Page<Artifact> artifacts){
        return ArtifactResDto.SearchArtifacts.builder()
                .postResDtos(getArtDtoList(artifacts.toList()))
                .isFirst(artifacts.isFirst())
                .isLast(artifacts.isLast())
                .listSize(artifacts.getTotalPages())
                .totalElements(artifacts.getTotalElements())
                .build();
    }
}
