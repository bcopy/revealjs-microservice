package com.github.cmcrobotics.reveal.process.fs;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import com.github.cmcrobotics.reveal.model.Category;
import com.github.cmcrobotics.reveal.model.Item;
import com.github.cmcrobotics.reveal.model.Presentation;

public interface CursorEventListener {

	FileVisitResult onNewItem(Item item, Path path, BasicFileAttributes fileAttr);

	FileVisitResult onNewCategory(Category category, Path path, BasicFileAttributes fileAttr);

	FileVisitResult onNewSlideshow(Presentation slideshow, Path path, BasicFileAttributes fileAttr);

}