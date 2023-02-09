package io.simpolor.upload.component;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class FileComponent {

    private static final List<String> EXTENSIONS_IMAGE = Arrays.asList("bmp", "gif", "jpg", "png", "jpeg");

    private final static String THUMBNAIL_PATH = "thumb";
    private final static int THUMBNAIL_WIDTH = 150;
    private final static int THUMBNAIL_HEIGHT = 150;

    @Value("${application.upload.path}")
    private String uploadPath;

    /***
     * MultipartFile
     * - getName() : 파라미터 이름
     * - getOriginalFilename() : 파일 이름
     * - isEmpty() : 파일 존재 유무
     * - getBytes : 파일 데이터
     * - getInputStream() : 파일 데이터를 읽어오는 InputStream을 얻어옴
     * - transferTo(File file) : 파일 데이터를 지정한 파일로 저장
     */
    public FileUpload create(MultipartFile multipartFile){

        FileUpload fileUpload = new FileUpload(multipartFile);

        try {
            File filePath = new File(StringUtils.joinWith(File.separator, uploadPath));
            if(!filePath.exists()){
                filePath.mkdir();
            }

            File file = new File(StringUtils.joinWith(File.separator, filePath, fileUpload.getSavedFileName()));
            multipartFile.transferTo(file);

            if(EXTENSIONS_IMAGE.contains(fileUpload.getExtension())){
                thumbnail(file, multipartFile);
            }

            fileUpload.setResult(Boolean.TRUE);

        } catch (IOException e) {
            log.warn("FileUploadComponent.create error: {}", e.getMessage());
            fileUpload.setResult(Boolean.FALSE);
        }

        return fileUpload;
    }

    public List<FileUpload> create(MultipartFile[] multipartFiles){

        List<FileUpload> fileUploads = new ArrayList<>();
        for(MultipartFile multipartFile : multipartFiles){
            fileUploads.add(this.create(multipartFile));
        }

        return fileUploads;
    }

    private void thumbnail(File file, MultipartFile multipartFile) {

        FileUpload fileUpload = new FileUpload(multipartFile);

        try {
            File thumbnailPath = new File(StringUtils.joinWith(File.separator, uploadPath, THUMBNAIL_PATH));
            if(!thumbnailPath.exists()){
                thumbnailPath.mkdir();
            }

            File thumbnailFile = new File(StringUtils.joinWith(File.separator, thumbnailPath.toString(), fileUpload.getSavedFileName()));
            Thumbnails.of(file)
                    .size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                    .toFile(thumbnailFile);

        }catch (IOException ioe){
            log.error("FileUploadComponent.thumbnail error: {}", ioe.getMessage());
        }
    }
}
