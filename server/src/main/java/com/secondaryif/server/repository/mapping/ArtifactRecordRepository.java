package com.secondaryif.server.repository.mapping;

import com.secondaryif.server.domain.mapping.ArtifactRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtifactRecordRepository extends JpaRepository<ArtifactRecord, Long> {
}
