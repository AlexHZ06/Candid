package com.nea.candid;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/storage/public/**")
                .addResourceLocations("file:///C:/Users/Alexander Hernandez/Desktop/Programing/Projects/Candid/Storage");

    }

}
