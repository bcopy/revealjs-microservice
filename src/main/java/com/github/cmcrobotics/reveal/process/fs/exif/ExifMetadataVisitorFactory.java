package com.github.cmcrobotics.reveal.process.fs.exif;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.github.cmcrobotics.reveal.model.Cursor;
import com.github.cmcrobotics.reveal.process.fs.AbstractFileVisitor;
import com.github.cmcrobotics.reveal.process.fs.FileVisitorFactory;


@ConditionalOnProperty(
		  prefix="reveal.microservice.process",
		  name = "visitors",
		  matchIfMissing = true,
		  havingValue = "exif")
@Component
public class ExifMetadataVisitorFactory implements FileVisitorFactory<AbstractFileVisitor> {
	
  public ExifMetadataVisitor getInstance(Cursor cursor){
	  return new ExifMetadataVisitor(cursor);
  }

}
