package com.secondaryif.server.domain.neo4j;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UploadRelationship {

    @Id // import 조심하기.. persistance로 하면 에러 뜸...
    @GeneratedValue // jpa랑은 다르게 strategy가 없어도 identity로 증가
    private Long id;

    @TargetNode // EndNode가 없고, 이게 있음
    private UploadGraph child;
    private int weight;

}
