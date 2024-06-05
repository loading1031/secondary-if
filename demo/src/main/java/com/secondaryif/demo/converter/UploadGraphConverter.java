package com.secondaryif.demo.converter;

import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.domain.neo4j.UploadGraph;

public class UploadGraphConverter {
    public static UploadGraph toPost(Upload upload){
        return UploadGraph.builder()
                .id(upload.getId())
                .content(upload.getContent())
                .build();
    }
}
