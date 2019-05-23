package me.junseok.demobootweb;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new GreetingInterceptor()).order(0);
		registry.addInterceptor(new AnotherInterceptor())
				.addPathPatterns("/hi/").order(-1);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/mobile/**")
				.addResourceLocations("classpath:/mobile/")
				.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
				
	}
}
