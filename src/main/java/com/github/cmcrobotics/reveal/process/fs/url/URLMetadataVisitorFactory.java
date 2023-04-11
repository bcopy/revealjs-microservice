package com.github.cmcrobotics.reveal.process.fs.url;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.github.cmcrobotics.reveal.model.Cursor;
import com.github.cmcrobotics.reveal.process.fs.AbstractFileVisitor;
import com.github.cmcrobotics.reveal.process.fs.FileVisitorFactory;


@ConditionalOnProperty(
		  prefix="reveal.microservice.process",
		  name = "visitors",
		  matchIfMissing = true,
		  havingValue = "url")
@Component
public class URLMetadataVisitorFactory implements FileVisitorFactory<AbstractFileVisitor> {
	
  public URLMetadataVisitor getInstance(Cursor cursor){
	  return new URLMetadataVisitor(cursor);
  }

}
