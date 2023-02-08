package io.simpolor.upload.component;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Getter
public class FileUpload {

    private Boolean result;
    private String originFileName;
    private String extension;
    private String savedFileName;
    private Long size;

    public FileUpload(MultipartFile multipartFile){
        this.originFileName = multipartFile.getOriginalFilename();
        this.extension = originFileName.substring(originFileName.lastIndexOf(".") + 1);
        this.savedFileName = StringUtils.joinWith(".", UUID.randomUUID(), extension);
        this.size = multipartFile.getSize();
    }

    public String getThumbnailFile(String filePath){
        return new StringBuilder()
                .append(filePath)
                .append(File.separator)
                .append("thumb")
                .append(File.separator)
                .append(savedFileName)
                .toString();
    }

    public void setResult(Boolean result){
        this.result = result;
    }
}
