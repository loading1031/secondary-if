package com.secondaryif.demo.repository;

import com.secondaryif.demo.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepository extends JpaRepository<Upload,Long> {
}
