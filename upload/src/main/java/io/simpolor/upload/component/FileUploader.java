package io.simpolor.upload.component;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileUploader {

    private final static String THUMBNAIL_PATH = "thumb";
    private final static int THUMBNAIL_WIDTH = 150;
    private final static int THUMBNAIL_HEIGHT = 150;

    @Value("${application.files.path}")
    String filePath;

    public Files createFile(MultipartFile multipartFile, String folder){

        if(!multipartFile.isEmpty() && multipartFile.getSize() != 0){

            String orgFileName = multipartFile.getOriginalFilename();
            long fileSize = multipartFile.getSize();

            String fileExt = FilenameUtils.getExtension(orgFileName);
            String savedFileName = UUID.randomUUID()+"."+fileExt;
            String savedFilePath = StringUtils.isEmpty(folder) ? filePath : filePath + File.separator + folder;

            File file = new File(savedFilePath);
            if(file.exists() == false){
                file.mkdirs();
            }

            try {
                File image = new File(savedFilePath + File.separator + savedFileName);
                multipartFile.transferTo(image);

                File thumnail = new File(savedFilePath + File.separator + THUMBNAIL_PATH + File.separator + savedFileName);
                thumnail.getParentFile().mkdirs();
                Thumbnails.of(image)
                        .size(THUMBNAIL_WIDTH,THUMBNAIL_HEIGHT)
                        .toFile(thumnail);

                return Files.builder()
                        .orgFileName(orgFileName)
                        .savedFileName(savedFileName)
                        .fileSize(fileSize)
                        .fileExt(fileExt)
                        .build();

            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }

        return null;
    }

    public Files createFileV2(MultipartFile multipartFile, String folder){

        if(!multipartFile.isEmpty() && multipartFile.getSize() != 0){

            String orgFileName = multipartFile.getOriginalFilename();
            long fileSize = multipartFile.getSize();

            String fileExt = FilenameUtils.getExtension(orgFileName);
            String savedFileName = UUID.randomUUID()+"."+fileExt;
            String savedFilePath = StringUtils.isEmpty(folder) ? filePath : filePath + File.separator + folder;

            File file = new File(savedFilePath);
            if(file.exists() == false){
                file.mkdirs();
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File image = new File(savedFilePath + File.separator + savedFileName);

                inputStream = multipartFile.getInputStream();
                outputStream = new FileOutputStream(image);

                int readByte = 0;
                byte[] buffer = new byte[8192];

                while ((readByte = inputStream.read(buffer, 0, 8120)) != -1){
                    outputStream.write(buffer, 0, readByte); // 파일 생성
                }

                return Files.builder()
                        .orgFileName(orgFileName)
                        .savedFileName(savedFileName)
                        .fileSize(fileSize)
                        .fileExt(fileExt)
                        .build();

            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return null;
    }

    public List<Files> createFiles(MultipartFile[] multipartFiles, String folder){

        List<Files> files = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty() && multipartFile.getSize() != 0){

                String orgFileName = multipartFile.getOriginalFilename();
                long fileSize = multipartFile.getSize();

                String fileExt = FilenameUtils.getExtension(orgFileName);
                String savedFileName = UUID.randomUUID()+"."+fileExt;
                String savedFilePath = StringUtils.isEmpty(folder) ? filePath : filePath + File.separator + folder;

                File file = new File(savedFilePath);
                if(file.exists() == false){
                    file.mkdirs();
                }

                try {
                    File image = new File(savedFilePath + File.separator + savedFileName);
                    multipartFile.transferTo(image);

                    File thumnail = new File(savedFilePath + File.separator + THUMBNAIL_PATH + File.separator + savedFileName);
                    thumnail.getParentFile().mkdirs();
                    Thumbnails.of(image)
                            .size(THUMBNAIL_WIDTH,THUMBNAIL_HEIGHT)
                            .toFile(thumnail);

                    files.add(Files.builder()
                            .orgFileName(orgFileName)
                            .savedFileName(savedFileName)
                            .fileSize(fileSize)
                            .fileExt(fileExt)
                            .build());

                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }

        return files;
    }

    @Data
    @Builder
    public static class Files {

        private String orgFileName;

        private String savedFileName;

        private long fileSize;

        private String fileExt;

    }

}
