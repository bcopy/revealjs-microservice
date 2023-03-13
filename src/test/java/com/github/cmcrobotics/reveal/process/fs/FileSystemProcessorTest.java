package com.github.cmcrobotics.reveal.process.fs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.github.cmcrobotics.reveal.model.Cursor;
import com.github.cmcrobotics.reveal.model.Item;
import com.github.cmcrobotics.reveal.model.Presentation;
import com.github.cmcrobotics.reveal.process.fs.exif.ExifMetadataVisitorFactory;
import com.github.cmcrobotics.reveal.process.fs.simple.SimpleFileSystemVisitorFactory;
import com.github.cmcrobotics.reveal.process.fs.url.URLMetadataVisitorFactory;
import com.google.common.jimfs.Jimfs;

class FileSystemProcessorTest {

	@Test
	void testSimpleFilesystemProcessor() throws IOException {
		Path rootPath = initializeTestFileSystem();
		
	    FileSystemProcessor fsp = new FileSystemProcessor(Arrays.asList(new SimpleFileSystemVisitorFactory()));
		Cursor c = new Cursor();
		Presentation presentation = fsp.process(c, rootPath).getSlideshows().get(0);
		
		assertEquals(5, presentation.getCategories().size());
		Item redItem = presentation.getCategoriesMap().get("presentation1").getItemsMap().get("Red1");
		
		assertTrue(redItem != null);
		assertNull(redItem.getCaption());
	}

	@Test
	void testExifFilesystemProcessor() throws IOException {
		Path rootPath = initializeTestFileSystem();
		
	    FileSystemProcessor fsp = new FileSystemProcessor(Arrays.asList(new ExifMetadataVisitorFactory()));
		Cursor c = new Cursor();
		Presentation presentation = fsp.process(c, rootPath).getSlideshows().get(0);
		
		assertEquals(5, presentation.getCategories().size());
		Item redItem = presentation.getCategoriesMap().get("presentation1").getItemsMap().get("Red1");
		
		assertTrue(redItem != null);
		assertEquals("Red", redItem.getCaption());
		assertNull(redItem.getModified());
		
	}
	
	@Test
	void testUrlFilesystemProcessor() throws IOException {
		Path rootPath = initializeTestFileSystem();

		FileSystemProcessor fsp = new FileSystemProcessor(Arrays.asList(new URLMetadataVisitorFactory()));
		Cursor c = new Cursor();
		Presentation presentation = fsp.process(c, rootPath).getSlideshows().get(0);
		Item item = presentation.getCategoriesMap().get("presentation1").getItemsMap().get("Gallery");
		assertEquals("http://galle.ry/gallery", item.getMetadata().get("url"));
	}

	@Test
	void testMultipassProcessing() throws IOException {
		Path rootPath = initializeTestFileSystem();
		
	    FileSystemProcessor fsp = new FileSystemProcessor(Arrays.asList(new SimpleFileSystemVisitorFactory(), new ExifMetadataVisitorFactory()));
		Cursor c = new Cursor();
		Presentation presentation = fsp.process(c, rootPath).getSlideshows().get(0);
		
		assertEquals(5, presentation.getCategories().size());
		Item redItem = presentation.getCategoriesMap().get("presentation1").getItemsMap().get("Red1");
		
		assertTrue(redItem != null);
		assertEquals("Red", redItem.getCaption());
		// Thanks to the simple file system visitor, the modification date should be available
		assertNotNull(redItem.getModified());
		
	}

	
	private static final Path initializeTestFileSystem() throws IOException {
		FileSystem fs = Jimfs.newFileSystem();
		
	    Path rootPath = fs.getPath("/home", "user1", "presentations");
	    Files.createDirectories(rootPath);
	    for (int i = 1; i <= 5; i++) {
	    	Path path = rootPath.resolve("presentation"+i);
			Files.createDirectory(path);
			
			final int index = i;
			Arrays.asList("blue","red","white","black").stream().forEach(color ->{
				try {
					Files.copy(FileSystemProcessorTest.class.getResourceAsStream("/test-images/"+color+".jpg"), path.resolve(color+index+".jpg"), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					fail("Could not copy test image "+color);
				}
			});
			Files.copy(FileSystemProcessorTest.class.getResourceAsStream("/test-images/gallery.url"), path.resolve("gallery.url"), StandardCopyOption.REPLACE_EXISTING);
		}
		return rootPath;
	}
	

}
