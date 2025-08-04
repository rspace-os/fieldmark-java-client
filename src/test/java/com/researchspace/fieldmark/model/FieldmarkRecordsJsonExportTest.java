package com.researchspace.fieldmark.model;

import static com.researchspace.fieldmark.model.utils.FieldmarkUtils.buildFieldTypeMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.researchspace.fieldmark.model.exception.FieldmarkUnsupportedNotebookException;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FieldmarkRecordsJsonExportTest {

  private FieldmarkRecordsJsonExport underTest;
  private ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  void init() throws IOException {
    String jsonRecords = IOUtils.resourceToString("/json/records.json", Charset.defaultCharset());
    underTest = mapper.readValue(jsonRecords, FieldmarkRecordsJsonExport.class);

    String jsonNotebook = IOUtils.resourceToString("/json/notebookID.json",
        Charset.defaultCharset());
    FieldmarkNotebook fieldmarkNotebook = mapper.readValue(jsonNotebook, FieldmarkNotebook.class);

    underTest.setFieldTypes(buildFieldTypeMap(fieldmarkNotebook));
  }

  @Test
  void testGetFormIdCorrectly(){
    assertEquals("Primary", underTest.getFormId());
  }

  @ParameterizedTest
  @ValueSource(strings = {"rec-5eb53c21-d7f8-41a7-a8b5-4900e46cf8e0","rec-b189e6ec-b760-4b44-8789-0ddc35d7cde2","rec-e881323c-d3cb-4393-9784-07b86585675b"})
  void testAllRecordsAreIngested(String recordId) {
    assertEquals(3, underTest.getRecordsById().size());
    assertNotNull(underTest.getRecordsById().get(recordId));
    assertEquals(recordId, underTest.getRecordsById().get(recordId).getRecordId());
    assertEquals(11, underTest.getRecordsById().get(recordId).getFieldList().size());
  }

  @ParameterizedTest
  @ValueSource(strings = {"Field-ID","hridPrimary-Next-Section","Survey-Number","IGSN-QR-Code","Sample-Location","New-Text-Field","Description","Sample-Photograph","Length-mm","Width-mm","Thickness-mm"})
  void testAllColumnsAreIngestedWithType(String fieldName) {
    FieldmarkRecord firstRecord = underTest.getFirstRecord();
    assertNotNull(firstRecord.getFieldTypes().get(fieldName));
    assertTrue(underTest.getFieldType(fieldName).isPresent());
  }

  @Test
  void testThrowExceptionIfMultipleFormIDsAreFound() throws IOException {
    String jsonRecords = IOUtils.resourceToString("/json/records_unsupported.json", Charset.defaultCharset());
    FieldmarkRecordsJsonExport records = mapper.readValue(jsonRecords, FieldmarkRecordsJsonExport.class);

    String jsonNotebook = IOUtils.resourceToString("/json/notebookID.json",
        Charset.defaultCharset());
    FieldmarkNotebook fieldmarkNotebook = mapper.readValue(jsonNotebook, FieldmarkNotebook.class);

    FieldmarkUnsupportedNotebookException thrown = assertThrows(
        FieldmarkUnsupportedNotebookException.class,
        () -> records.setFieldTypes(buildFieldTypeMap(fieldmarkNotebook)),
        "FieldmarkRecordsJsonExport did not throw the exception, but it was needed"
    );
    assertTrue(
        thrown.getMessage().contains("The notebook has more than one FormID: [Primary, Secondary]\n"
            + "At the moment it is not possible to import Fieldmark notebooks with more than one FormID"));

  }


}