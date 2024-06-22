package com.secondaryif.server.repository.neo4j;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.types.Path;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomUploadGraphRepositoryImpl implements CustomUploadGraphRepository {
    private final Neo4jClient neo4jClient;

    @Override
    public Optional<Path> findMaxWeightPath(Long startNodeId, Long endNodeId) {
        // Correct the variable name in the RETURN clause to match the YIELD clause
        String query = "MATCH (start:Upload {id: $startNodeId}), (end:Upload {id: $endNodeId}) " +
                "CALL apoc.path.expandConfig(start, { " +
                "   relationshipFilter: 'childRelationShip>', " +
                "   minLevel: 1, " +
                "   endNodes: [end], " +
                "   weightProperty: 'weight' "+
                "}) YIELD path AS expandedPath " +
                "WHERE expandedPath IS NOT NULL " +
                "WITH expandedPath, " +
                "reduce(totalWeight = 0, r in relationships(expandedPath) | totalWeight + coalesce(r.weight, 0)) AS totalWeight " +
                "RETURN expandedPath, totalWeight " + // Corrected: use 'expandedPath' instead of 'path'
                "ORDER BY totalWeight DESC " +
                "LIMIT 1";

        log.info("query:{}",neo4jClient.query(query)
                .bind(startNodeId).to("startNodeId")
                .bind(endNodeId).to("endNodeId")
                .fetchAs(Path.class)
                .mappedBy((typeSystem, record) -> {
                    // Mapping should use the actual returned field names
                    // If 'expandedPath' is expected to be a Path, map it correctly
                    Path path = record.get("expandedPath").asPath(); // Ensure this matches what's being returned
                    return path; // Since you're returning Optional<Path>, just return the path
                })
                .one());
        // Correct the mappedBy function to use the correct variable names
        return neo4jClient.query(query)
                .bind(startNodeId).to("startNodeId")
                .bind(endNodeId).to("endNodeId")
                .fetchAs(Path.class)
                .mappedBy((typeSystem, record) -> {
                    // Mapping should use the actual returned field names
                    // If 'expandedPath' is expected to be a Path, map it correctly
                    Path path = record.get("expandedPath").asPath(); // Ensure this matches what's being returned
                    return path; // Since you're returning Optional<Path>, just return the path
                })
                .one();
    }
}

