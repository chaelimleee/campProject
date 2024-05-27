package com.javateam.campProject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
    
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    	registry.addResourceHandler("/thumb_sub4_ext/**")
    			.addResourceLocations("file:///E:/lcr/work/thumb_sub4/");
    	
    	registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	
		registry.addResourceHandler("/jquery/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/jquery/");
				
		registry.addResourceHandler("/bootstrap/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/");
		
		// axios
		registry.addResourceHandler("/axios/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/axios/");
		
		// swagger
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");		
		
		// bootstrap icons
		registry.addResourceHandler("/bootstrap-icons/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap-icons/");
		
		// 게시판 관련 : summernote 자원 경로 추가
		registry.addResourceHandler("/summernote/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/summernote/");
	
    }
    
}