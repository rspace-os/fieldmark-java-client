package com.researchspace.fieldmark.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.researchspace.fieldmark.model.FieldmarkNotebook;
import com.researchspace.fieldmark.model.FieldmarkRecord;
import com.researchspace.fieldmark.model.FieldmarkRecordsCsvExport;
import com.researchspace.fieldmark.model.FieldmarkRecordsJsonExport;
import com.researchspace.fieldmark.model.utils.FieldmarkUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Manual contract test against the live Fieldmark API. Export a long-lived token as the
 * FIELDMARK_TOKEN environment variable (never paste it into this file) 
 * `FIELDMARK_TOKEN='your-long-lived-token-here' mvn test -Dtest=FieldmarkClientRealConnectionTest`
 * and remove @Disabled
 * locally to run. Run it after any Fieldmark/FAIMS3 release: it asserts the response fields this
 * client and the rspace-web import depend on (ids, names, design metadata, field types, CSV
 * columns, ZIP filenames), so a breaking API change fails here instead of in production. Written
 * against the FAIMS3 v1.6.2 API.
 */
@Disabled("Manual test: needs a live Fieldmark account token in the FIELDMARK_TOKEN env variable")
class FieldmarkClientRealConnectionTest {

  private final FieldmarkClientImpl fieldmarkClientImpl = new FieldmarkClientImpl();
  private final String LONG_LIVED_TOKEN = System.getenv("FIELDMARK_TOKEN");
  private final String NOTEBOOK_ID = "1726126204618-rspace-igsn-demo";
  private final String FORM_ID = "Primary";

  @BeforeEach
  public void setUp() {
    assumeTrue(StringUtils.isNotBlank(LONG_LIVED_TOKEN),
        "Set the FIELDMARK_TOKEN environment variable to a Fieldmark long-lived token "
            + "before running this manual test");
    fieldmarkClientImpl.setFieldmarkBaseUrl("https://api.fieldmark.app/api");
  }

  @Test
  public void testGetNotebooks() {
    List<FieldmarkNotebook> result = fieldmarkClientImpl.getNotebooks(LONG_LIVED_TOKEN);
    assertFalse(result.isEmpty(), "the account should own at least one notebook");
    for (FieldmarkNotebook notebook : result) {
      assertTrue(StringUtils.isNotBlank(notebook.getProjectId()),
          "every notebook needs an id: rspace-web keys the import dialog on it");
      assertTrue(StringUtils.isNotBlank(notebook.getName()));
      assertNotNull(notebook.getMetadata(),
          "metadata must be synthesizable from the list response");
      assertEquals(notebook.getProjectId(), notebook.getMetadata().getProjectId());
      assertEquals(notebook.getName(), notebook.getMetadata().getName());
    }
  }

  @Test
  public void testGetNotebook() {
    FieldmarkNotebook result = fieldmarkClientImpl.getNotebook(LONG_LIVED_TOKEN, NOTEBOOK_ID);
    assertEquals(NOTEBOOK_ID, result.getProjectId());
    assertEquals(NOTEBOOK_ID, result.getMetadata().getProjectId());
    assertTrue(StringUtils.isNotBlank(result.getMetadata().getName()));
    // the design metadata the rspace-web import surfaces; the demo notebook populates all of it
    assertTrue(StringUtils.isNotBlank(result.getMetadata().getPreDescription()));
    assertTrue(StringUtils.isNotBlank(result.getMetadata().getProjectLead()));
    assertTrue(StringUtils.isNotBlank(result.getMetadata().getLeadInstitution()));
    assertTrue(StringUtils.isNotBlank(result.getMetadata().getNotebookVersion()));
    assertTrue(StringUtils.isNotBlank(result.getMetadata().getSchemaVersion()));
    // the rspace-web import calls toString() on these two, so null here breaks every import
    assertNotNull(result.getMetadata().getIsPublic());
    assertNotNull(result.getMetadata().getIsRequest());

    assertNotNull(result.getUiSpecification(),
        "the UI specification drives the whole import: it must be present on a single-notebook GET");
    Map<String, String> fieldTypes = FieldmarkUtils.buildFieldTypeMap(result);
    assertFalse(fieldTypes.isEmpty());
    for (Map.Entry<String, String> fieldType : fieldTypes.entrySet()) {
      assertTrue(fieldType.getValue() != null && fieldType.getValue().contains("::"),
          "field \"" + fieldType.getKey() + "\" must have a namespaced type-returned, but was: "
              + fieldType.getValue());
    }
  }

  @Test
  public void testGetNotebookRecords() {
    FieldmarkRecordsJsonExport result =
        fieldmarkClientImpl.getNotebookRecords(LONG_LIVED_TOKEN, NOTEBOOK_ID);
    assertFalse(result.getRecords().isEmpty(), "the demo notebook should have records");
    assertEquals(FORM_ID, result.getFormId());
    for (FieldmarkRecord record : result.getRecords()) {
      assertTrue(StringUtils.isNotBlank(record.getRecordId()));
      assertNotNull(record.getFieldList(), "every record must carry its data map");
      assertFalse(record.getFieldList().isEmpty());
    }
  }

  @Test
  public void testGetNotebookCsv() {
    FieldmarkRecordsCsvExport result =
        fieldmarkClientImpl.getNotebookCsv(LONG_LIVED_TOKEN, NOTEBOOK_ID, FORM_ID);
    assertFalse(result.getRecords().isEmpty(), "the CSV export must contain the demo records");
  }

  @Test
  public void testGetNotebookFiles() throws IOException {
    Map<String, byte[]> result =
        fieldmarkClientImpl.getNotebookFiles(LONG_LIVED_TOKEN, NOTEBOOK_ID, FORM_ID);
    assertFalse(result.isEmpty(), "the demo notebook has photographs, so the ZIP must not be empty");
  }

  /**
   * Cross-checks the contracts between the notebook, records, CSV and ZIP endpoints that the
   * rspace-web import relies on: the record ids in the CSV, the "identifier" column, the field
   * types resolving for every record field, and the ZIP entry names matching the file names
   * written into the records JSON.
   */
  @Test
  public void testImportContractAcrossEndpoints() throws IOException {
    FieldmarkNotebook notebook = fieldmarkClientImpl.getNotebook(LONG_LIVED_TOKEN, NOTEBOOK_ID);
    FieldmarkRecordsJsonExport records =
        fieldmarkClientImpl.getNotebookRecords(LONG_LIVED_TOKEN, NOTEBOOK_ID);
    records.setFieldTypes(FieldmarkUtils.buildFieldTypeMap(notebook));

    FieldmarkRecordsCsvExport csv =
        fieldmarkClientImpl.getNotebookCsv(LONG_LIVED_TOKEN, NOTEBOOK_ID, records.getFormId());
    assertFalse(csv.getRecords().isEmpty());

    for (FieldmarkRecord record : records.getRecords()) {
      Map<String, String> csvRecord = csv.getRecord(record.getRecordId());
      assertNotNull(csvRecord,
          "record " + record.getRecordId() + " from the JSON export must appear in the CSV,"
              + " keyed by the record_id column");
      assertTrue(csvRecord.containsKey("identifier"),
          "the CSV must keep the identifier column: the IGSN import reads it");
      for (String fieldName : record.getFieldList().keySet()) {
        assertTrue(record.getFieldType(fieldName).isPresent(),
            "record field \"" + fieldName + "\" has no type in the notebook UI specification");
      }
    }

    if (records.hasFiles()) {
      Map<String, byte[]> files =
          fieldmarkClientImpl.getNotebookFiles(LONG_LIVED_TOKEN, NOTEBOOK_ID, records.getFormId());
      assertFalse(files.isEmpty(), "the notebook declares FILES fields but the ZIP is empty");
      for (FieldmarkRecord record : records.getRecords()) {
        for (String fieldName : record.getFieldList().keySet()) {
          if ("Files".equalsIgnoreCase(record.getFieldType(fieldName).orElse(""))) {
            Object fileNames = record.getFieldList().get(fieldName);
            if (fileNames instanceof List) {
              for (Object fileName : (List<?>) fileNames) {
                assertTrue(files.containsKey(String.valueOf(fileName)),
                    "file \"" + fileName + "\" referenced by record " + record.getRecordId()
                        + " is missing from the ZIP export: the JSON/ZIP filename contract broke");
              }
            }
          }
        }
      }
    }
  }
}
