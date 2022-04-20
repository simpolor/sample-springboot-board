package io.simpolor.upload.component;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Slf4j
@Component
public class FileUploader {

    private static final List<String> EXTENSIONS_IMAGE = Arrays.asList("bmp", "gif", "jpg", "png", "jpeg");
    private static final List<String> EXTENSIONS_VIDEO = Arrays.asList("mp4", "avi", "mov", "mpg", "wmv", "mpeg");
    private final static String DOT = ".";

    private final static String THUMBNAIL_PATH = "thumb";
    private final static int THUMBNAIL_WIDTH = 150;
    private final static int THUMBNAIL_HEIGHT = 150;

    @Value("${application.files.path}")
    private String rootFilePath;

    /***
     * MultipartFile
     * - getName() : 파라미터 이름
     * - getOriginalFilename() : 파일 이름
     * - isEmpty() : 파일 존재 유무
     * - getBytes : 파일 데이터
     * - getInputStream() : 파일 데이터를 읽어오는 InputStream을 얻어옴
     * - transferTo(File file) : 파일 데이터를 지정한 파일로 저장
     */
    public FileInfo createFile(MultipartFile multipartFile, String directory){

        if(Objects.nonNull(multipartFile) && !multipartFile.isEmpty() && multipartFile.getSize() > 0){

            long fileSize = multipartFile.getSize();
            String fileExt = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String saveFilePath = makeDirectory(directory);
            String saveFileName =
                    new StringBuilder(UUID.randomUUID().toString())
                            .append(".")
                            .append(fileExt)
                            .toString();
            File saveFile = createFile(saveFilePath, saveFileName);

            try {
                // MultipartFile 클래스의 transferTo 메소드를 이용한 방법
                multipartFile.transferTo(saveFile);
                if(EXTENSIONS_IMAGE.contains(fileExt)){
                    createThumbnail(saveFile, saveFilePath, saveFileName);
                }

                return FileInfo.builder()
                        .orgFileName(multipartFile.getOriginalFilename())
                        .savedFileName(saveFileName)
                        .fileSize(fileSize)
                        .fileExt(fileExt)
                        .build();

            }catch (IOException ioe){
                log.error("createFile error: {}", ioe.getMessage());
            }
        }
        return null;
    }

    public List<FileInfo> createFiles(MultipartFile[] multipartFiles, String directory) {

        List<FileInfo> fileInfos = new ArrayList<>();
        if(Objects.nonNull(multipartFiles)){
            for(MultipartFile multipartFile : multipartFiles){
                FileInfo fileInfo = createFile(multipartFile, directory);
                if(Objects.nonNull(fileInfo)){
                    fileInfos.add(fileInfo);
                }
            }
        }

        return fileInfos;
    }

    public FileInfo newCreateFile(MultipartFile multipartFile, String directory){

        if(Objects.nonNull(multipartFile) && !multipartFile.isEmpty() && multipartFile.getSize() != 0){

            String orgFileName = multipartFile.getOriginalFilename();
            long fileSize = multipartFile.getSize();
            String fileExt = FilenameUtils.getExtension(orgFileName);
            String saveFilePath = makeDirectory(directory);
            String saveFileName = UUID.randomUUID() + DOT + fileExt;
            File saveFile = createFile(saveFilePath, saveFileName);

            try {
                InputStream inputStream = multipartFile.getInputStream();
                OutputStream outputStream = new FileOutputStream(saveFile);

                int readByte = 0;
                byte[] buffer = new byte[1024];

                while ((readByte = inputStream.read(buffer, 0, 1024)) != -1){
                    outputStream.write(buffer, 0, readByte); // 파일 생성
                }

                // FileUtils를 copyInputStreamToFile 메소드를 이용한 방법
                // FileUtils.copyInputStreamToFile(inputStream, saveFile);

                return FileInfo.builder()
                        .orgFileName(orgFileName)
                        .savedFileName(saveFileName)
                        .fileSize(fileSize)
                        .fileExt(fileExt)
                        .build();

            }catch (IOException ioe) {
                log.error("newCreateFile error: {}", ioe.getMessage());
            }
        }

        return null;
    }

    private String makeDirectory(String directory){

        StringBuilder sb = new StringBuilder();
        sb.append(rootFilePath);
        if(StringUtils.isNotEmpty(directory)){
            sb.append(File.separator);
            sb.append(directory);
        }

        File makeFilePath = new File(sb.toString());
        if(Boolean.FALSE.equals(makeFilePath.exists())){
            makeFilePath.mkdirs();
        }

        return sb.toString();
    }

    private File createFile(String filePath, String fileName){

        StringBuilder sb = new StringBuilder();
        sb.append(filePath);
        sb.append(File.separator);
        sb.append(fileName);

        return new File(sb.toString());
    }

    private void createThumbnail(File file, String filePath, String fileName) {

        StringBuilder sb = new StringBuilder();
        sb.append(filePath);
        sb.append(File.separator);
        sb.append(THUMBNAIL_PATH);

        File makeFilePath = new File(sb.toString());
        if(Boolean.FALSE.equals(makeFilePath.exists())){
            makeFilePath.mkdirs();
        }

        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(makeFilePath);
            sb2.append(File.separator);
            sb2.append(fileName);

            Thumbnails.of(file)
                    .size(THUMBNAIL_WIDTH,THUMBNAIL_HEIGHT)
                    .toFile(sb2.toString());

        }catch (IOException ioe){
            log.error("createThumbnail error: {}", ioe.getMessage());
        }

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileInfo {

        private String orgFileName;
        private String savedFileName;
        private long fileSize;
        private String fileExt;
    }

}
