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
public class Upload extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;

    @ManyToMany
    @JoinTable(
            name = "upload_next_uploads", // 조인 테이블 이름
            joinColumns = @JoinColumn(name = "upload_id"), // 현재 엔티티의 외래 키 컬럼
            inverseJoinColumns = @JoinColumn(name = "next_upload_id") // 반대쪽 엔티티의 외래 키 컬럼
    )
    private List<Upload> nextUploads = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    private Artifact artifact;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void setUpload(Artifact artifact, Member member) {
        if (this.artifact != null)
            this.artifact.getUploadList().remove(this);
        this.artifact = artifact;
        this.artifact.getUploadList().add(this);

        if (this.member != null)
            this.member.getUploadList().remove(this);
        this.member = member;
        this.member.getUploadList().remove(this);
    }

    public void addParentNextUpload(Upload parent) {
        if (!parent.nextUploads.contains(this))
            parent.nextUploads.add(this);
    }

    public void removeParentNextUpload(Upload parent) {
        if (parent.nextUploads.contains(this)) {
            // todo: 부모가 없는 경우, addAll -> 부모가 하나라도 있으면, 제거만하고 끝.
            parent.nextUploads.addAll(this.nextUploads);
            parent.nextUploads.remove(this);
        }
    }
}
