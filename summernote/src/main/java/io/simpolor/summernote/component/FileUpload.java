package io.simpolor.summernote.component;

import com.google.gson.JsonObject;
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
public class FileUpload {

    @Value("${application.files.path}")
    private String filePath; //저장될 파일 경로

    public JsonObject upload(MultipartFile multipartFile){

        JsonObject jsonObject = new JsonObject();

        String orgFileName = multipartFile.getOriginalFilename();	// 원본 파일명
        String extension = orgFileName.substring(orgFileName.lastIndexOf("."));	// 파일 확장자

        // 랜덤 UUID+확장자로 저장될 savedFileName
        String savedFileName = UUID.randomUUID() + extension;

        File targetFile = new File(filePath + File.separator + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장

            jsonObject.addProperty("url", "/upload/"+savedFileName);
            jsonObject.addProperty("message", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	// 실패시 저장된 파일 삭제
            jsonObject.addProperty("message", "error");
            log.error("FileUpload.upload error : {}", e.getMessage(), e);
        }

        return jsonObject;
    }


}
