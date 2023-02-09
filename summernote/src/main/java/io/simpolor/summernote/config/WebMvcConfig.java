package io.simpolor.summernote.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${application.upload.path}")
    private String uploadPath;

    /**
     * Appication의 경로가 아닌 외부 경로에 있는 리소스를 url로 불러올 수 있도록 설정
     * ( 현재 localhost:8080/upload/1234.jpg로 접속하면 C:/upload/1234.jpg 파일을 불러옴 )
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // filePath 마지막 "/"을 넣어야함, 주의필요
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///"+uploadPath+"/");
    }
}
