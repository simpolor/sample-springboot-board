package io.simpolor.summernote.component;

import io.simpolor.summernote.model.FileMessage;
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

    public FileMessage upload(MultipartFile multipartFile){

        FileMessage fileMessage = new FileMessage(multipartFile);

        File file = new File(fileMessage.getSavedFilePath(filePath));

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, file); //파일 저장
            fileMessage.setResult(Boolean.TRUE);

        } catch (IOException e) {
            fileMessage.setResult(Boolean.FALSE);
        }

        return fileMessage;
    }


}
