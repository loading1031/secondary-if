package com.secondaryif.demo.repository.neo4j;

import com.secondaryif.demo.domain.neo4j.UploadGraph;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface UploadGraphRepository extends Neo4jRepository<UploadGraph, Long> {
    @Query("MATCH (start:Upload {id: $startNodeId})-[*]->(connected) RETURN DISTINCT connected")
    List<UploadGraph> findAllConnectedNodes(Long startNodeId);
}
