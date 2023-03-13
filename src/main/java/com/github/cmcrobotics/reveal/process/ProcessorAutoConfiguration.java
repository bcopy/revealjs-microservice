package com.github.cmcrobotics.reveal.process;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ProcessorAutoConfiguration {
   
   @Bean
   @ConfigurationProperties("reveal.microservice.process")
   ProcessorConfigurationProperties processorConfiguration() {
	   return new ProcessorConfigurationProperties();
   }
   
}
