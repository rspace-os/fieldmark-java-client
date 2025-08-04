package com.researchspace.fieldmark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class FieldmarkNotebookTest {

  @Test
  void testWrappingJsonNotebook() throws IOException {
    String jsonRecords = IOUtils.resourceToString("/json/notebookID.json",
        Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    FieldmarkNotebook notebookUnderTest = mapper.readValue(jsonRecords, FieldmarkNotebook.class);
    assertEquals("RSpace IGSN Demo", notebookUnderTest.getName());
    assertEquals("OPEN", notebookUnderTest.getStatus());
    assertNull(notebookUnderTest.getId());
    assertNull(notebookUnderTest.getListingId());

    FieldmarkNotebookMetadata metadataUnderTest = notebookUnderTest.getMetadata();
    assertNotNull(metadataUnderTest);
    assertEquals("123", metadataUnderTest.getAge());
    assertEquals("Large", metadataUnderTest.getSize());
    assertFalse(metadataUnderTest.getIsPublic());
    assertFalse(metadataUnderTest.getIsRequest());
    assertEquals("Fieldmark", metadataUnderTest.getLeadInstitution());
    assertEquals("RSpace IGSN Demo", metadataUnderTest.getName());
    assertEquals("1.1", metadataUnderTest.getNotebookVersion());
    assertEquals(
        "Demonstration notebook to help develop an export pipeline from Fieldmark to RSpace.",
        metadataUnderTest.getPreDescription());
    assertEquals("Steve Cassidy", metadataUnderTest.getProjectLead());
    assertEquals("New", metadataUnderTest.getProjectStatus());
    assertEquals("1.0", metadataUnderTest.getSchemaVersion());
    assertEquals("true", metadataUnderTest.getShowQRCodeButton());
    assertEquals("1726126204618-rspace-igsn-demo", metadataUnderTest.getProjectId());

    assertNotNull(notebookUnderTest.getUiSpecification());
  }

  @Test
  void testWrappingJsonNotebookList() throws IOException {
    String jsonRecords = IOUtils.resourceToString("/json/notebooks.json", Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    List<FieldmarkNotebook> underTest = Arrays.asList(
        mapper.readValue(jsonRecords, FieldmarkNotebook[].class));

    assertEquals(1, underTest.size());

    FieldmarkNotebookMetadata metadata = underTest.get(0).getMetadata();
    assertNotNull(metadata);
    assertEquals("123", metadata.getAge());
    assertEquals("Large", metadata.getSize());
    assertFalse(metadata.getIsPublic());
    assertFalse(metadata.getIsRequest());
    assertEquals("Fieldmark", metadata.getLeadInstitution());
    assertEquals("RSpace IGSN Demo", metadata.getName());
    assertEquals("1.1", metadata.getNotebookVersion());
    assertEquals(
        "Demonstration notebook to help develop an export pipeline from Fieldmark to RSpace.",
        metadata.getPreDescription());
    assertEquals("Steve Cassidy", metadata.getProjectLead());
    assertEquals("New", metadata.getProjectStatus());
    assertEquals("1.0", metadata.getSchemaVersion());
    assertEquals("true", metadata.getShowQRCodeButton());
    assertEquals("1726126204618-rspace-igsn-demo", metadata.getProjectId());

    assertNull(underTest.get(0).getUiSpecification());
  }

}