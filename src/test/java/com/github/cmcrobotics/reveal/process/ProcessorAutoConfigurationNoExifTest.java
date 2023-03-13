package com.github.cmcrobotics.reveal.process;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.cmcrobotics.reveal.RevealJsMicroserviceApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RevealJsMicroserviceApplication.class)
@TestPropertySource(properties = "reveal.microservice.process.visitors=nonExistingVisitor")
class ProcessorAutoConfigurationNoExifTest {
	
	@Autowired
	ApplicationContext applicationContext;

	@Test
	@DirtiesContext
	void testExifDisabled() {
		assertTrue(applicationContext.containsBean("simpleFileSystemVisitorFactory"));
		assertFalse(applicationContext.containsBean("exifMetadataVisitorFactory"));
	}

}
