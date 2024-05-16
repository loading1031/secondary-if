package com.secondaryif.demo.domain;

import com.secondaryif.demo.domain.common.BaseEntity;
import com.secondaryif.demo.domain.mapping.Like;
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
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Artifact> ArtifactList = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Like> likeList = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Upload> uploadList = new ArrayList<>();
}
