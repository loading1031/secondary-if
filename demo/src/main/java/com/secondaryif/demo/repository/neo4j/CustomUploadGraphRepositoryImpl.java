package com.secondaryif.demo.repository.neo4j;

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
        String query = "MATCH (start:Upload {id: $startNodeId}), (end:Upload {id: $endNodeId}) " +
                "CALL apoc.path.expandConfig(start, { " +
                "   relationshipFilter: 'childRelationShip>', " +
                "   minLevel: 1, " +
                "   endNodes: [end], " +
                "   weightProperty: 'weight', " +
                "   limit: 1 " +
                "}) YIELD path as expandedPath " +
                "RETURN expandedPath, reduce(weight = 0, r in relationships(expandedPath) | weight + coalesce(r.weight, 0)) as totalWeight " +
                "ORDER BY totalWeight DESC " +
                "LIMIT 1";

        return neo4jClient.query(query)
                .bind(startNodeId).to("startNodeId")
                .bind(endNodeId).to("endNodeId")
                .fetchAs(Path.class)
                .mappedBy((typeSystem, record) -> record.get("expandedPath").asPath())
                .one();
    }
}
