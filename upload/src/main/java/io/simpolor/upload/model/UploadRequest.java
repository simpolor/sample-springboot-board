package io.simpolor.upload.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequest {

    private MultipartFile uploadFile;

    private MultipartFile[] uploadFiles;

}
