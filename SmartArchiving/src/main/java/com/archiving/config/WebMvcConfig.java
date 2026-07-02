package com.archiving.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 설정.
 * 화면 URL은 모듈별 Page Controller에서 명시하고, 정적 리소스만 여기서 처리한다.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final Environment env;

    public WebMvcConfig(Environment env) {
        this.env = env;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/font/**").addResourceLocations("/font/");

        String noticeDir = env.getProperty("app.upload.dir-notice");
        if (noticeDir != null && !noticeDir.isBlank()) {
            Path p = Paths.get(noticeDir);
            String location = p.toUri().toString();
            if (!location.endsWith("/")) {
                location = location + "/";
            }
            registry.addResourceHandler("/notice/**").addResourceLocations(location);
        }
    }
}
