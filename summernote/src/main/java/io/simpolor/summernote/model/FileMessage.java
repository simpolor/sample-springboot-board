package io.simpolor.summernote.model;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Getter
public class FileMessage {

    private Boolean result;
    private String originFileName;
    private String extension;
    private String savedFileName;

    public FileMessage(MultipartFile multipartFile){
        this.originFileName = multipartFile.getOriginalFilename();
        this.extension = originFileName.substring(originFileName.lastIndexOf("."));
        this.savedFileName = UUID.randomUUID() + extension;
    }

    public String getSavedFilePath(String filePath){
        return new StringBuilder()
                .append(filePath)
                .append(File.separator)
                .append(savedFileName)
                .toString();
    }

    public void setResult(Boolean result){
        this.result = result;
    }
}
