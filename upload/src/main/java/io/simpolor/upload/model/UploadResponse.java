package io.simpolor.upload.model;

import io.simpolor.upload.component.FileUploader;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {

    private String orgFileName;
    private String savedFileName;
    private String fileExt;
    private long fileSize;

    public static UploadResponse of(FileUploader.FileInfo fileInfo){
        return UploadResponse.builder()
                .orgFileName(fileInfo.getOrgFileName())
                .savedFileName(fileInfo.getSavedFileName())
                .fileExt(fileInfo.getFileExt())
                .fileSize(fileInfo.getFileSize())
                .build();
    }

    public static List<UploadResponse> of(List<FileUploader.FileInfo> fileInfos){
        return fileInfos.stream()
                .map(UploadResponse::of)
                .collect(Collectors.toList());
    }
}
