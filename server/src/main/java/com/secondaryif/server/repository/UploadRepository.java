package com.secondaryif.server.repository;

import com.secondaryif.server.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UploadRepository extends JpaRepository<Upload,Long> {
    List<Upload> findAllByArtifactId(Long artifactId);
    Optional<Upload> findFirstByArtifactId(Long artifactId);
}
