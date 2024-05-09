package com.secondaryif.demo.service.Artifact;

import com.secondaryif.demo.converter.ArtifactConverter;
import com.secondaryif.demo.domain.Artifact;
import com.secondaryif.demo.domain.Member;
import com.secondaryif.demo.repository.ArtifactRepository;
import com.secondaryif.demo.service.Member.MemberService;
import com.secondaryif.demo.web.dto.artifact.ArtifactReqDto;
import com.secondaryif.demo.web.dto.artifact.ArtifactResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtifactServiceImpl implements ArtifactService{
    private final MemberService memberService;
    private final ArtifactRepository artifactRepository;
    @Override
    @Transactional
    public ArtifactResDto.PostResDto postArtifact(ArtifactReqDto.PostDto request, Long memberId) {
        Member getMember = memberService.getMember(memberId);
        Artifact newArtifact = ArtifactConverter.toPost(request,getMember);
        newArtifact.setArtifact(getMember);

        return ArtifactConverter.toPostResDto(artifactRepository.save(newArtifact));
    }

    @Override
    public List<ArtifactResDto.PostResDto> getArtifactList() {

        return ArtifactConverter.getArtDtoList(artifactRepository.findAll());
    }
}
