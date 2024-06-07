package com.secondaryif.demo.domain.neo4j;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Slf4j
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

    @Relationship(type = "childRelationShip", direction = Relationship.Direction.OUTGOING)
    public Set<FamilyRelationShip> childRelationShips;

    public void setToChildRelationShip(UploadGraph child, int weight) {
        if (this.childRelationShips == null) {
            this.childRelationShips = new HashSet<>();
        }
        FamilyRelationShip relationShip = FamilyRelationShip.builder()
                .child(child)
                .weight(weight)
                .build();
        this.childRelationShips.add(relationShip);
    }

}