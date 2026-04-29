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

    assertTrue(result.containsKey("primary/sample-photograph/sample-12-00009.jpg"));
    File file = new File(FileUtils.getTempDirectoryPath() +
        "primary/sample-photograph/sample-12-00009.jpg");
    FileUtils.writeByteArrayToFile(file,
        result.get("primary/sample-photograph/sample-12-00009.jpg"));
    assertEquals("sample-12-00009.jpg", file.getName());
    assertTrue(file.canRead());

    assertTrue(result.containsKey("primary/sample-photograph/sample-63-00050.jpg"));
    file = new File(FileUtils.getTempDirectoryPath() +
        "primary/sample-photograph/sample-63-00050.jpg");
    FileUtils.writeByteArrayToFile(file,
        result.get("primary/sample-photograph/sample-63-00050.jpg"));
    assertEquals("sample-63-00050.jpg", file.getName());
    assertTrue(file.canRead());


    assertTrue(result.containsKey("primary/sample-photograph/sample-1-00008.jpg"));
    file = new File(FileUtils.getTempDirectoryPath() +
        "primary/sample-photograph/sample-1-00008.jpg");
    FileUtils.writeByteArrayToFile(file,
        result.get("primary/sample-photograph/sample-1-00008.jpg"));
    assertEquals("sample-1-00008.jpg", file.getName());
    assertTrue(file.canRead());

  }

}
