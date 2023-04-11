package com.github.cmcrobotics.reveal.process.fs;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.cmcrobotics.reveal.model.Cursor;
import com.github.cmcrobotics.reveal.process.Processor;


@Component
public class FileSystemProcessor implements Processor<Path> {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FileSystemProcessor.class);
	
	List<FileVisitorFactory<AbstractFileVisitor>> visitorFactories;
	
	public FileSystemProcessor(@Autowired List<FileVisitorFactory<AbstractFileVisitor>> visitorFactories) {
		this.visitorFactories = visitorFactories;
	}
	
	@Override
	public Cursor process(Cursor cursor, Path... paths) {
		
		for (Path path : paths) {
			String slideshowName = path.getFileName().toString();
			
			cursor.setOrCreateSlideshow(slideshowName);

			for (FileVisitorFactory<AbstractFileVisitor> visitorFactory : visitorFactories) {
				FileVisitor<Path> visitor = visitorFactory.getInstance(cursor);
				try {
					Files.walkFileTree(path, visitor);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		
		return cursor;
	}
}
