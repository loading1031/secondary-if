package com.secondaryif.demo.repository;

import com.secondaryif.demo.domain.Artifact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtifactRepository extends JpaRepository<Artifact,Long> {
    Page<Artifact> findByTitleContainingOrMemberNameContaining(String title, String author, Pageable pageable);
}
