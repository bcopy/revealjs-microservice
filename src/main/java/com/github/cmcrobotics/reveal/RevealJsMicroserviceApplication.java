package com.github.cmcrobotics.reveal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.github.cmcrobotics.reveal.model.Presentation;
import com.github.cmcrobotics.reveal.process.ProcessorService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(proxyBeanMethods = false)
@ComponentScan({"com.github.cmcrobotics.reveal.process"})
public class RevealJsMicroserviceApplication {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RevealJsMicroserviceApplication.class);
	
	@Autowired
    ProcessorService processorService;

	public static void main(String[] args) {
		SpringApplication.run(RevealJsMicroserviceApplication.class, args);
	}
	
	List<Presentation> presentations;
	
	public List<Presentation> getPresentations() {
		return presentations;
	}

	public boolean isProcessedContents() {
		return processedContents;
	}

	boolean processedContents = false;

	@PostConstruct
	void start() {
		log.info("Starting processing...");
		
		presentations = processorService.processConfiguredPaths();
		processedContents = true;
		
	}

}
