package com.researchspace.fieldmark.model.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.researchspace.fieldmark.model.FieldmarkNotebook;
import com.researchspace.fieldmark.model.FieldmarkRecordsJsonExport;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class FieldmarkUtilsTest {

  @Test
  void testBuildFieldTypeMapFromV162Notebook() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    FieldmarkNotebook notebook = mapper.readValue(
        IOUtils.resourceToString("/json/notebookID_v162.json", StandardCharsets.UTF_8),
        FieldmarkNotebook.class);

    Map<String, String> fieldTypes = FieldmarkUtils.buildFieldTypeMap(notebook);
    assertEquals(11, fieldTypes.size());
    assertEquals("faims-core::Number", fieldTypes.get("Length-mm"));
    assertEquals("faims-attachment::Files", fieldTypes.get("Sample-Photograph"));

    // the field-type map drives the records import: form id, file detection, type extraction
    FieldmarkRecordsJsonExport records = mapper.readValue(
        IOUtils.resourceToString("/json/records.json", StandardCharsets.UTF_8),
        FieldmarkRecordsJsonExport.class);
    records.setFieldTypes(fieldTypes);
    assertEquals("Primary", records.getFormId());
    assertTrue(records.hasFiles());
    assertEquals("Number", records.getFieldType("Length-mm").orElseThrow());
  }

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
