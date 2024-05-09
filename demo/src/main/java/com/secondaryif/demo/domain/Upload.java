package com.secondaryif.demo.domain;

import com.secondaryif.demo.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Artifact artifact;

    public void setUpload(Artifact artifact){
        if(this.artifact != null)
            this.artifact.getUploadList().remove(this);
        this.artifact = artifact;
        this.artifact.getUploadList().add(this);
    }
}
