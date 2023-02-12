package com.github.cmcrobotics.reveal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@SpringBootApplication
public class RevealJsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevealJsMicroserviceApplication.class, args);
	}

	@Configuration
	@EnableWebMvc
	public class MvcConfig implements WebMvcConfigurer {
		@Value("${static.path:./www/}")
		private String staticPath;

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry
					.addResourceHandler("revealjs/**")
					.addResourceLocations("classpath:/revealjs/");
			System.out.println(" ********************\n************ static path "+staticPath);
			registry
					.addResourceHandler("/**")
					.addResourceLocations("file:" + staticPath, "classpath:/static/");
		}

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/").setViewName("/index.html");
		}
	}

}
