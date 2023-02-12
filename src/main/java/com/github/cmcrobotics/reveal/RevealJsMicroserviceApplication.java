package com.github.cmcrobotics.reveal;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportRuntimeHints(ResourceRuntimeHints.class)
public class RevealJsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevealJsMicroserviceApplication.class, args);
	}

	

}
