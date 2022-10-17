package com.shopme.admin;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "./ShopmeWebParent/ShopmeBackEnd/user-photos";
        Path userPhotosDir = Paths.get(dirName);
        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:/" + userPhotosPath + "/");
    }
}
