package com.secondaryif.demo.domain;

import com.secondaryif.demo.domain.common.BaseEntity;
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

    public void setUpload(Artifact artifact){
        if(this.artifact != null)
            this.artifact.getUploadList().remove(this);
        this.artifact = artifact;
        this.artifact.getUploadList().add(this);
    }

    public void addParentNextUpload(Upload parent) {
        if(!parent.nextUploads.contains(this))
            parent.nextUploads.add(this);
    }

    public void removeParentNextUpload(Upload parent) {
        if(parent.nextUploads.contains(this)) {
            parent.nextUploads.addAll(this.nextUploads);
            parent.nextUploads.remove(this);
        }
    }
}
