package com.secondaryif.demo.repository.neo4j;

import org.neo4j.driver.types.Path;

import java.util.Optional;

public interface CustomUploadGraphRepository {
    Optional<Path> findMaxWeightPath(Long startNodeId, Long endNodeId);
}