package com.green.webadmin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.green.webmodels.enumerate.PhotoPath;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory(PhotoPath.PROFILE_PHOTO_DIR, registry);
	}
	
	private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get(dirName);
		String uploadPath = uploadDir.toFile().getAbsolutePath();
		
		System.out.println("exposeDirectory: " + uploadPath);
		
		if(dirName.startsWith("../")) {
			dirName.replace("../", "");
		}
		
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:" + uploadPath + "/");
	}
}
