package synergyhubback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //각자 폴더의 이름에 맞게 DIR 수정해주세요

        // 상품 이미지 경로 추가
        registry.addResourceHandler("/post/**")
                .addResourceLocations("file:///C:/Synergy/original/productUpload/");




    }


}
