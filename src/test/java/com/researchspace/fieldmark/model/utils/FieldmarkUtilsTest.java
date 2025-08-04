package com.researchspace.fieldmark.model.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class FieldmarkUtilsTest {

  @Test
  void testCreateFilesMap() throws IOException {
    byte[] zipFile = IOUtils.resourceToByteArray("/files/FieldmarkFile.zip");
    Map<String, byte[]> result = FieldmarkUtils.createFilesMap("notebookId", zipFile);

    assertNotNull(result);
    assertEquals(3, result.size());

    assertTrue(result.containsKey("Sample-Photograph/Sample-12-00009-Sample-Photograph.jpg"));
    File file = new File(FileUtils.getTempDirectoryPath() +
        "/Sample-Photograph/Sample-12-00009-Sample-Photograph.jpg");
    FileUtils.writeByteArrayToFile(file,
        result.get("Sample-Photograph/Sample-12-00009-Sample-Photograph.jpg"));
    assertEquals("Sample-12-00009-Sample-Photograph.jpg", file.getName());
    assertTrue(file.canRead());

    assertTrue(result.containsKey("Sample-Photograph/Sample-63-00050-Sample-Photograph.jpg"));
    file = new File(FileUtils.getTempDirectoryPath() +
        "Sample-Photograph/Sample-63-00050-Sample-Photograph.jpg");
    FileUtils.writeByteArrayToFile(file,
        result.get("Sample-Photograph/Sample-63-00050-Sample-Photograph.jpg"));
    assertEquals("Sample-63-00050-Sample-Photograph.jpg", file.getName());
    assertTrue(file.canRead());


    assertTrue(result.containsKey("Sample-Photograph/Sample-1-00008-Sample-Photograph.jpg"));
    file = new File(FileUtils.getTempDirectoryPath() +
        "Sample-Photograph/Sample-1-00008-Sample-Photograph.jpg");
    FileUtils.writeByteArrayToFile(file,
        result.get("Sample-Photograph/Sample-1-00008-Sample-Photograph.jpg"));
    assertEquals("Sample-1-00008-Sample-Photograph.jpg", file.getName());
    assertTrue(file.canRead());

  }

}
