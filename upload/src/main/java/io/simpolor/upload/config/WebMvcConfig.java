package io.simpolor.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages= {"io.simpolor.upload"})
public class WebMvcConfig implements WebMvcConfigurer {

    private final int MAX_SIZE = 100 * 1024 * 1024;

    /*@Bean
    public MultipartResolver resolver() {

        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();

        return resolver;
    }*/

    @Bean
    public MultipartResolver resolver() {

        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(MAX_SIZE); // 100MB
        resolver.setMaxUploadSizePerFile(MAX_SIZE); // 100MB
        resolver.setMaxInMemorySize(0);
        return resolver;
    }

    /***
     * 애노테이션으로 제한도 가능
     *
     * @MultipartConfig (
     *   location=/tmp,
     *   fileSizeThreshold=0,
     *   maxFileSize=5242880,       // 5 MB
     *   maxRequestSize=20971520
     * )
     */
}
