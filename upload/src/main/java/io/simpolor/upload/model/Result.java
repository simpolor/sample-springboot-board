package io.simpolor.upload.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {

    private String name;
    private String orgFileName;
    private String savedFileName;
    private String fileExt;
    private long fileSize;
}
