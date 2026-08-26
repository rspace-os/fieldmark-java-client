package com.researchspace.fieldmark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
    assertEquals("2.0", metadataUnderTest.getSchemaVersion());
    assertEquals("true", metadataUnderTest.getShowQRCodeButton());
    assertEquals("1726126204618-rspace-igsn-demo", metadataUnderTest.getProjectId());

    assertNotNull(notebookUnderTest.getUiSpecification());
  }

  @Test
  void testWrappingV162JsonNotebook() throws IOException {
    String json = IOUtils.resourceToString("/json/notebookID_v162.json",
        Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    FieldmarkNotebook notebookUnderTest = mapper.readValue(json, FieldmarkNotebook.class);
    assertEquals("RSpace IGSN Demo", notebookUnderTest.getName());
    assertEquals("OPEN", notebookUnderTest.getStatus());
    assertEquals("1726126204618-rspace-igsn-demo", notebookUnderTest.getId());
    assertEquals("1726126204618-rspace-igsn-demo", notebookUnderTest.getProjectId());
    assertNull(notebookUnderTest.getListingId());

    FieldmarkNotebookMetadata metadataUnderTest = notebookUnderTest.getMetadata();
    assertNotNull(metadataUnderTest);
    assertEquals("1726126204618-rspace-igsn-demo", metadataUnderTest.getProjectId());
    assertEquals("RSpace IGSN Demo", metadataUnderTest.getName());
    // purposeMarkdown is the renamed pre_description, so it wins over the new description field
    assertEquals(
        "Demonstration notebook to help develop an export pipeline from Fieldmark to RSpace.",
        metadataUnderTest.getPreDescription());
    assertEquals("Steve Cassidy", metadataUnderTest.getProjectLead());
    assertEquals("Fieldmark", metadataUnderTest.getLeadInstitution());
    assertEquals("1.1", metadataUnderTest.getNotebookVersion());
    assertEquals("2.0", metadataUnderTest.getSchemaVersion());
    assertEquals("true", metadataUnderTest.getShowQRCodeButton());
    // relocated by the v1.6.2 API into uiSpecification.metadata.custom
    assertEquals("123", metadataUnderTest.getAge());
    assertEquals("Large", metadataUnderTest.getSize());
    assertEquals("New", metadataUnderTest.getProjectStatus());
    assertEquals(Boolean.TRUE, metadataUnderTest.getIsPublic());
    assertEquals(Boolean.FALSE, metadataUnderTest.getIsRequest());

    FieldmarkUiSpecification uiSpec = notebookUnderTest.getUiSpecification();
    assertNotNull(uiSpec);
    assertEquals(11, uiSpec.getFields().size());
    assertEquals("faims-core::Number", uiSpec.getFields().get("Length-mm").getFieldType());
    assertEquals("faims-attachment::Files",
        uiSpec.getFields().get("Sample-Photograph").getFieldType());
  }

  @Test
  void testWrappingJsonNotebookList() throws IOException {
    String jsonRecords = IOUtils.resourceToString("/json/notebooks.json", Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    List<FieldmarkNotebook> underTest = Arrays.asList(
        mapper.readValue(jsonRecords, FieldmarkNotebook[].class));

    assertEquals(3, underTest.size());

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
    assertEquals("2.0", metadata.getSchemaVersion());
    assertEquals("true", metadata.getShowQRCodeButton());
    assertEquals("1726126204618-rspace-igsn-demo", metadata.getProjectId());

    assertNull(underTest.get(0).getUiSpecification());
  }

  @Test
  void testWrappingV162JsonNotebookList() throws IOException {
    String json = IOUtils.resourceToString("/json/notebooks_v162.json", Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    List<FieldmarkNotebook> underTest = Arrays.asList(
        mapper.readValue(json, FieldmarkNotebook[].class));

    assertEquals(3, underTest.size());

    FieldmarkNotebook first = underTest.get(0);
    assertEquals("1726126204618-rspace-igsn-demo", first.getProjectId());
    assertEquals("RSpace IGSN Demo", first.getName());
    assertNull(first.getUiSpecification());

    FieldmarkNotebookMetadata metadata = first.getMetadata();
    assertNotNull(metadata);
    assertEquals("1726126204618-rspace-igsn-demo", metadata.getProjectId());
    assertEquals("RSpace IGSN Demo", metadata.getName());
    assertEquals(
        "Demonstration notebook to help develop an export pipeline from Fieldmark to RSpace.",
        metadata.getPreDescription());
    // the v1.6.2 list response carries no design metadata
    assertNull(metadata.getProjectStatus());
    assertNull(metadata.getProjectLead());
    assertNull(metadata.getLeadInstitution());
    // never null: the rspace-web import calls toString() on these unconditionally
    assertEquals(Boolean.FALSE, metadata.getIsPublic());
    assertEquals(Boolean.FALSE, metadata.getIsRequest());

    // a notebook without a description keeps a synthesized metadata object
    FieldmarkNotebookMetadata second = underTest.get(1).getMetadata();
    assertNotNull(second);
    assertEquals("Campus Survey", second.getName());
    assertNull(second.getPreDescription());
  }

  /**
   * rspace-web serves this model's JSON straight to its frontend, which reads name,
   * metadata.project_id, metadata.pre_description and metadata.project_lead. A notebook parsed
   * from the v1.6.2 API must serialize to the same names as one parsed from the old API, and the
   * v1.6.2-only input keys must not leak into the output.
   */
  @Test
  void testV162NotebookSerializesWithPre162Names() throws IOException {
    String json = IOUtils.resourceToString("/json/notebookID_v162.json",
        Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    FieldmarkNotebook notebook = mapper.readValue(json, FieldmarkNotebook.class);
    JsonNode serialized = mapper.readTree(mapper.writeValueAsString(notebook));

    assertEquals("RSpace IGSN Demo", serialized.get("name").asText());
    assertEquals("1726126204618-rspace-igsn-demo", serialized.get("project_id").asText());

    JsonNode metadata = serialized.get("metadata");
    assertNotNull(metadata);
    assertEquals("1726126204618-rspace-igsn-demo", metadata.get("project_id").asText());
    assertEquals("RSpace IGSN Demo", metadata.get("name").asText());
    assertEquals(
        "Demonstration notebook to help develop an export pipeline from Fieldmark to RSpace.",
        metadata.get("pre_description").asText());
    assertEquals("Steve Cassidy", metadata.get("project_lead").asText());

    JsonNode uiSpec = serialized.get("ui-specification");
    assertNotNull(uiSpec);
    assertEquals("faims-core::Number",
        uiSpec.get("fields").get("Length-mm").get("type-returned").asText());

    // v1.6.2-only input keys are deserialization-only
    assertNull(serialized.get("uiSpecification"));
    assertNull(serialized.get("description"));
    assertNull(uiSpec.get("schemaVersion"));
    assertNull(uiSpec.get("settings"));
  }

  /**
   * The two API generations must serialize with the same key set, and the old shape must
   * round-trip unchanged, so rspace-web serves identical JSON structure whichever Fieldmark
   * version it talked to.
   */
  @Test
  void testOldAndV162NotebooksSerializeWithSameKeys() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode oldSerialized = mapper.readTree(mapper.writeValueAsString(mapper.readValue(
        IOUtils.resourceToString("/json/notebookID.json", Charset.defaultCharset()),
        FieldmarkNotebook.class)));
    JsonNode newSerialized = mapper.readTree(mapper.writeValueAsString(mapper.readValue(
        IOUtils.resourceToString("/json/notebookID_v162.json", Charset.defaultCharset()),
        FieldmarkNotebook.class)));

    List<String> oldKeys = new ArrayList<>();
    oldSerialized.fieldNames().forEachRemaining(oldKeys::add);
    List<String> newKeys = new ArrayList<>();
    newSerialized.fieldNames().forEachRemaining(newKeys::add);
    assertEquals(oldKeys, newKeys);

    // the old shape round-trips its metadata untouched
    assertEquals("New", oldSerialized.get("metadata").get("project_status").asText());
    assertEquals(
        "Demonstration notebook to help develop an export pipeline from Fieldmark to RSpace.",
        oldSerialized.get("metadata").get("pre_description").asText());
  }

  @Test
  void testV162PreDescriptionFallsBackToDescription() throws IOException {
    String json = IOUtils.resourceToString("/json/notebookID_v162.json",
        Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode withoutDesignMetadata = (ObjectNode) mapper.readTree(json);
    ((ObjectNode) withoutDesignMetadata.get("uiSpecification")).remove("metadata");

    FieldmarkNotebook notebook =
        mapper.treeToValue(withoutDesignMetadata, FieldmarkNotebook.class);
    assertEquals("Short summary of the RSpace IGSN demo notebook.",
        notebook.getMetadata().getPreDescription());
  }

  /**
   * A partially populated v1.6.2 document (template-derived or draft notebooks can lack the
   * design metadata node, and settings are optional) must still synthesize the basics without
   * failing, and absent booleans must stay null rather than become the string "null".
   */
  @Test
  void testV162PartialDocumentSynthesizesBasics() throws IOException {
    String json = IOUtils.resourceToString("/json/notebookID_v162.json",
        Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode partial = (ObjectNode) mapper.readTree(json);
    ((ObjectNode) partial.get("uiSpecification")).remove("metadata");
    ((ObjectNode) partial.get("uiSpecification").get("uiSpec")).remove("settings");

    FieldmarkNotebook notebook = mapper.treeToValue(partial, FieldmarkNotebook.class);

    FieldmarkNotebookMetadata metadata = notebook.getMetadata();
    assertNotNull(metadata);
    assertEquals("1726126204618-rspace-igsn-demo", metadata.getProjectId());
    assertEquals("RSpace IGSN Demo", metadata.getName());
    assertNull(metadata.getProjectStatus());
    assertNull(metadata.getProjectLead());
    assertNull(metadata.getLeadInstitution());
    assertNull(metadata.getShowQRCodeButton());
    assertNull(metadata.getAge());
    assertEquals(Boolean.FALSE, metadata.getIsPublic());
    assertEquals(Boolean.FALSE, metadata.getIsRequest());
    assertEquals("2.0", metadata.getSchemaVersion());

    assertNotNull(notebook.getUiSpecification());
    assertEquals(11, notebook.getUiSpecification().getFields().size());
  }

  @Test
  void testExplicitlySetMetadataWinsOverSynthesis() throws IOException {
    String json = IOUtils.resourceToString("/json/notebookID_v162.json",
        Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    FieldmarkNotebook notebook = mapper.readValue(json, FieldmarkNotebook.class);

    FieldmarkNotebookMetadata custom = new FieldmarkNotebookMetadata();
    custom.setName("caller-provided");
    notebook.setMetadata(custom);

    assertEquals(custom, notebook.getMetadata());
    assertEquals("caller-provided", notebook.getMetadata().getName());
  }

  /**
   * Reading the synthesized values (which Jackson serialization also does, via the getters) must
   * not mutate the notebook: two notebooks parsed from the same JSON stay equal after one of them
   * has been read, so notebooks remain safe as map keys and in sets.
   */
  @Test
  void testGettersDoNotMutateEqualsAndHashCode() throws IOException {
    String json = IOUtils.resourceToString("/json/notebookID_v162.json",
        Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    FieldmarkNotebook read = mapper.readValue(json, FieldmarkNotebook.class);
    FieldmarkNotebook untouched = mapper.readValue(json, FieldmarkNotebook.class);

    int hashBefore = read.hashCode();
    assertNotNull(read.getMetadata());
    assertNotNull(read.getUiSpecification());

    assertEquals(hashBefore, read.hashCode());
    assertEquals(untouched, read);
    // and the synthesized values are stable across calls
    assertEquals(read.getMetadata(), read.getMetadata());
  }

}