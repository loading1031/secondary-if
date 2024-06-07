package com.secondaryif.demo.domain.mapping;

import com.secondaryif.demo.domain.Upload;
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
public class UploadRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArtifactRecord artifactRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    private Upload upload;
}
