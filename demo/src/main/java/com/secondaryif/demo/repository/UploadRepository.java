package com.secondaryif.demo.repository;

import com.secondaryif.demo.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadRepository extends JpaRepository<Upload,Long> {
    List<Upload> findAllByArtifactId(Long artifactId);
}
