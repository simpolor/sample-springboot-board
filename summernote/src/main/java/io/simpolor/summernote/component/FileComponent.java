package io.simpolor.summernote.component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Component
public class FileComponent {

    @Value("${application.upload.path}")
    private String uploadPath;

    public FileUpload upload(MultipartFile multipartFile){

        FileUpload fileUpload = new FileUpload(multipartFile);

        try {
            File filePath = new File(StringUtils.joinWith(File.separator, uploadPath));
            if(!filePath.exists()){
                filePath.mkdir();
            }

            File file = new File(StringUtils.joinWith(File.separator, filePath, fileUpload.getSavedFileName()));

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
        private String savedFileName;
        private String extension;
        private Long size;

        public FileUpload(MultipartFile multipartFile){
            this.originFileName = multipartFile.getOriginalFilename();
            this.extension = originFileName.substring(originFileName.lastIndexOf("."));
            this.savedFileName = UUID.randomUUID() + extension;
            this.size = multipartFile.getSize();
        }

        public void setResult(Boolean result){
            this.result = result;
        }
    }


}
