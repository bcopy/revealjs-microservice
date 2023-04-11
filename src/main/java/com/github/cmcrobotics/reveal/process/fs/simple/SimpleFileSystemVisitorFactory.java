package com.github.cmcrobotics.reveal.process.fs.simple;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.cmcrobotics.reveal.model.Cursor;
import com.github.cmcrobotics.reveal.process.fs.AbstractFileVisitor;
import com.github.cmcrobotics.reveal.process.fs.FileVisitorFactory;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleFileSystemVisitorFactory implements FileVisitorFactory<AbstractFileVisitor> {
	
  public AbstractFileVisitor getInstance(Cursor cursor){
	  return new SimpleFileSystemVisitor(cursor);
  }

}
