package com.github.cmcrobotics.reveal;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PlantUmlController {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PlantUmlController.class);

  @ResponseBody
  @RequestMapping(value = "/uml", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] generateImageFromUmlScript(@RequestParam(value = "script", defaultValue = "@startuml;title Welcome to PlantUML;@enduml") String script) throws IOException {
    log.debug("Generating Plantuml diagram for: {}",script);
    SourceStringReader reader = new SourceStringReader(script.replace(';','\n'));
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    reader.generateImage(bos, new FileFormatOption(FileFormat.PNG, false));
    return bos.toByteArray();
  }
}
