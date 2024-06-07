package com.secondaryif.demo.repository.mapping;

import com.secondaryif.demo.domain.mapping.ArtifactRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtifactRecordRepository extends JpaRepository<ArtifactRecord, Long> {
}
