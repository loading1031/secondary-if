package com.secondaryif.demo.domain.neo4j;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Node("Upload")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UploadGraph {
    @Id
    private Long id;

    private String content;
    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */

    @Relationship(type = "parent", direction = Relationship.Direction.INCOMING)
    public Set<FamilyRelationShip> parent;
    @Relationship(type = "children", direction = Relationship.Direction.OUTGOING)
    public Set<FamilyRelationShip> children;


    public void setToParent(UploadGraph uploadGraph, int weight) {
        if (parent == null) {
            parent = new HashSet<>();
        }
        parent.add(FamilyRelationShip.builder()
                .child(this)
                .parent(uploadGraph)
                .weight(weight)
                .build());
    }
    public void setToChildren(UploadGraph uploadGraph, int weight) {
        if (children == null) {
            children = new HashSet<>();
        }
        parent.add(FamilyRelationShip.builder()
                .child(uploadGraph)
                .parent(this)
                .weight(weight)
                .build());
    }
}