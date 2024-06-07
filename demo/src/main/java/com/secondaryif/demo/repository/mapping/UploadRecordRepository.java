package com.secondaryif.demo.repository.mapping;

import com.secondaryif.demo.domain.mapping.UploadRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRecordRepository extends JpaRepository<UploadRecord, Long> {
}
