package com.secondaryif.server.repository.mapping;

import com.secondaryif.server.domain.mapping.UploadRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRecordRepository extends JpaRepository<UploadRecord, Long> {
}
