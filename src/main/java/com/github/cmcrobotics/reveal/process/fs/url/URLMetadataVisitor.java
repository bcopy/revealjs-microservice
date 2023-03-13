package com.github.cmcrobotics.reveal.process.fs.url;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.ini4j.Ini;

import com.github.cmcrobotics.reveal.model.Cursor;
import com.github.cmcrobotics.reveal.model.Item;
import com.github.cmcrobotics.reveal.process.fs.AbstractFileVisitor;

public class URLMetadataVisitor extends AbstractFileVisitor {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(URLMetadataVisitor.class);
	
	public URLMetadataVisitor(Cursor cursor) {
		super(cursor);
	}

	@Override
	public FileVisitResult onNewItem(Item item, Path path, BasicFileAttributes fileAttr) {
		item.setCaption(item.getTitle());
		Map<String, String> metadata = new HashMap<>();
		
		try {
			Ini.Section section = new Ini(Files.newInputStream(path)).get("InternetShortcut");
			if(section != null) {
				String url = section.get("URL");
				if(url != null) {
				  metadata.put("url", url);
				}
			}
			if(! metadata.containsKey("url") ) {
				log.error("Could not extract URL value from file {}", path);
			}
			
			item.setMetadata(metadata);
		} catch (IOException e) {
			log.error("Could not parse URL file {} : {}", path, e);
		}
		return null;
	}

	@Override
	public FileVisitResult acceptNewItem(Path path, BasicFileAttributes fileAttr, String title, String mimeType) {
		Optional<String> ext = getExtensionByStringHandling(path.getFileName().toString());
		if (ext.isPresent() && "url".equalsIgnoreCase(ext.get())) {
			return FileVisitResult.CONTINUE;
		}
		return FileVisitResult.TERMINATE;
	}
	
}