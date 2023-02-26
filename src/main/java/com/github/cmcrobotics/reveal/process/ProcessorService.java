package com.github.cmcrobotics.reveal.process;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.cmcrobotics.reveal.model.Cursor;
import com.github.cmcrobotics.reveal.model.Presentation;
import com.github.cmcrobotics.reveal.process.fs.FileSystemProcessor;

@Component
public class ProcessorService {
	
	FileSystem filesystem;

	@Autowired
	ProcessorConfigurationProperties configuration;
	
	@Autowired
	FileSystemProcessor fileSystemProcessor;
	
	
	public List<Presentation> processConfiguredPaths() {
		Cursor cursor = new Cursor();
		for(String pathString : configuration.getContentPaths()) {
			Path path;
			if(filesystem != null) {
				path = filesystem.getPath(pathString);
			}else {
				path = Paths.get(pathString);
			}
			fileSystemProcessor.process(cursor, path);
		}
		return cursor.getSlideshows();
	}
	
}
