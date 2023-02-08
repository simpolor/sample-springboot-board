package io.simpolor.upload.model;

import io.simpolor.upload.component.FileUpload;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class FileDto {

    @Getter
    @Builder
    public static class FileResponse {

        private String originFileName;
        private String savedFileName;
        private String extension;
        private Long size;

        public static FileResponse of(FileUpload fileUpload){

            return FileResponse.builder()
                    .originFileName(fileUpload.getOriginFileName())
                    .savedFileName(fileUpload.getSavedFileName())
                    .extension(fileUpload.getExtension())
                    .size(fileUpload.getSize())
                    .build();
        }

        public static List<FileResponse> of(List<FileUpload> fileUploads){
            return fileUploads.stream()
                    .map(FileResponse::of)
                    .collect(Collectors.toList());
        }
    }

}
