package io.simpolor.upload.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@Component
public class NewFileComponent {

    @Value("${application.upload.path}")
    private String uploadPath;

    public FileUpload create(MultipartFile multipartFile){

        FileUpload fileUpload = new FileUpload(multipartFile);

        try {
            File filePath = new File(StringUtils.joinWith(File.separator, uploadPath));
            if(!filePath.exists()){
                filePath.mkdir();
            }

            File file = new File(StringUtils.joinWith(File.separator, filePath, fileUpload.getSavedFileName()));

            InputStream inputStream = multipartFile.getInputStream();
            OutputStream outputStream = new FileOutputStream(file);

            int readByte = 0;
            byte[] buffer = new byte[1024];

            while ((readByte = inputStream.read(buffer, 0, 1024)) != -1){
                outputStream.write(buffer, 0, readByte); // 파일 생성
            }

            // FileUtils를 copyInputStreamToFile 메소드를 이용한 방법
            // FileUtils.copyInputStreamToFile(inputStream, saveFile);

            fileUpload.setResult(Boolean.TRUE);

        }catch (IOException ioe) {
            log.error("NewFileUploadComponent.create error: {}", ioe.getMessage());
            fileUpload.setResult(Boolean.FALSE);
        }

        return fileUpload;
    }
}
