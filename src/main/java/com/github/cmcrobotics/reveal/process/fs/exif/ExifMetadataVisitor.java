package com.github.cmcrobotics.reveal.process.fs.exif;

import static com.drew.metadata.exif.ExifDirectoryBase.TAG_DATETIME_ORIGINAL;
import static com.drew.metadata.exif.ExifDirectoryBase.TAG_IMAGE_DESCRIPTION;
import static com.drew.metadata.exif.ExifDirectoryBase.TAG_USER_COMMENT;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifReader;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.github.cmcrobotics.reveal.model.Cursor;
import com.github.cmcrobotics.reveal.model.Item;
import com.github.cmcrobotics.reveal.process.fs.AbstractFileVisitor;

public class ExifMetadataVisitor extends AbstractFileVisitor {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExifMetadataVisitor.class);
	
	public ExifMetadataVisitor(Cursor cursor) {
		super(cursor);
	}
	
	

	Iterable<JpegSegmentMetadataReader> metadataReaders = Arrays.asList(new ExifReader());

	@Override
	public FileVisitResult onNewItem(Item i, Path path, BasicFileAttributes fileAttr) {
	    extractExifMetadata(path, i);
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult acceptNewItem(Path path, BasicFileAttributes fileAttr, String title, String mimeType) {
		if(Arrays.asList("image/svg+xml", "image/gif", "image/png","image/jpeg").contains(mimeType)){
			return FileVisitResult.CONTINUE;
		}
		return FileVisitResult.TERMINATE;
	}

	protected void extractExifMetadata(Path path, Item i) {
		try {
			Metadata metadata = JpegMetadataReader.readMetadata(Files.newInputStream(path), metadataReaders);
			ExifIFD0Directory exifId0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			ExifSubIFDDirectory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
			
			// Fallback on the item title, if any is set
			i.setCaption(i.getTitle());
			
			if(exifSubIFDDirectory != null) {
				if(exifSubIFDDirectory.containsTag(TAG_DATETIME_ORIGINAL)) {
				  i.setCreated(exifSubIFDDirectory.getDate(TAG_DATETIME_ORIGINAL).getTime());
				}
				if(exifSubIFDDirectory.containsTag(TAG_USER_COMMENT)) {
				  i.setCaption(exifSubIFDDirectory.getString(TAG_USER_COMMENT));
				}
			}
			if(exifId0Directory != null && exifId0Directory.containsTag(TAG_IMAGE_DESCRIPTION)) {
				i.setCaption(exifId0Directory.getString(TAG_IMAGE_DESCRIPTION));
			}
			
			if(exifSubIFDDirectory == null && exifId0Directory == null) {
				log.debug("No EXIF information : Could not extract metadata from '{}'", path.toString());
			}
		} catch (JpegProcessingException | IOException ex) {
			if (log.isWarnEnabled()) {
				log.warn("Could not extract metadata from '{}'", path.toString(), ex);
			}
		}
	}

  }