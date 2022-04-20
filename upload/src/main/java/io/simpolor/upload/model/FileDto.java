package io.simpolor.upload.model;

import io.simpolor.upload.component.FileUploader;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private MultipartFile uploadFile;
    private MultipartFile[] uploadFiles;

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileDetail {

        private String orgFileName;
        private String savedFileName;
        private String fileExt;
        private long fileSize;

        public static FileDetail of(FileUploader.FileInfo fileInfo){

            return FileDetail.builder()
                    .orgFileName(Objects.nonNull(fileInfo) ? fileInfo.getOrgFileName() : null)
                    .savedFileName(Objects.nonNull(fileInfo) ? fileInfo.getSavedFileName() : null)
                    .fileExt(Objects.nonNull(fileInfo) ? fileInfo.getFileExt() : null)
                    .fileSize(Objects.nonNull(fileInfo) ? fileInfo.getFileSize() : null)
                    .build();
        }

        public static List<FileDetail> of(List<FileUploader.FileInfo> fileInfos){
            return fileInfos.stream()
                    .map(FileDetail::of)
                    .collect(Collectors.toList());
        }
    }

}
