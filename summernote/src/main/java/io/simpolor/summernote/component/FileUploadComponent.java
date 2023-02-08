package io.simpolor.summernote.component;

import io.simpolor.summernote.model.FileMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Component
public class FileUploadComponent {

    @Value("${application.file.path}")
    private String filePath; //저장될 파일 경로

    public FileUpload upload(MultipartFile multipartFile){

        FileUpload fileUpload = new FileUpload(multipartFile);

        File file = new File(fileUpload.getSavedFilePath(filePath));

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, file); //파일 저장
            fileUpload.setResult(Boolean.TRUE);

        } catch (IOException e) {
            log.warn("File upload error: {}", e.getMessage());
            fileUpload.setResult(Boolean.FALSE);
        }

        return fileUpload;
    }

    @Getter
    public class FileUpload {

        private Boolean result;
        private String originFileName;
        private String extension;
        private String savedFileName;

        public FileUpload(MultipartFile multipartFile){
            this.originFileName = multipartFile.getOriginalFilename();
            this.extension = originFileName.substring(originFileName.lastIndexOf("."));
            this.savedFileName = UUID.randomUUID() + extension;
        }

        public String getSavedFilePath(String filePath){
            return new StringBuilder()
                    .append(filePath)
                    .append(java.io.File.separator)
                    .append(savedFileName)
                    .toString();
        }

        public void setResult(Boolean result){
            this.result = result;
        }
    }


}
