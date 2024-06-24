package com.secondaryif.server.domain;

import com.secondaryif.server.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Artifact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "artifact", cascade = CascadeType.ALL)
    private List<Upload> uploadList = new ArrayList<>();

    public void setArtifact(Member member){
        if(this.member != null)
            this.member.getArtifactList().remove(this);
        this.member = member;
        this.member.getArtifactList().add(this);
    }
}
