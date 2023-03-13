package com.github.cmcrobotics.reveal.process.fs;

import com.github.cmcrobotics.reveal.model.Cursor;

public interface FileVisitorFactory<T extends AbstractFileVisitor> {
	public T getInstance(Cursor cursor);
}
