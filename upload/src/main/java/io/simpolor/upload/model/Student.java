package io.simpolor.upload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;

    private Integer grade;

    private Integer age;

    private List<String> hobby;

    private MultipartFile profile;

    private MultipartFile[] profiles;

}
