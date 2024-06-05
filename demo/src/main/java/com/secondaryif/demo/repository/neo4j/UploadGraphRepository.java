package com.secondaryif.demo.repository.neo4j;

import com.secondaryif.demo.domain.neo4j.UploadGraph;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UploadGraphRepository extends Neo4jRepository<UploadGraph, Long> {
}
