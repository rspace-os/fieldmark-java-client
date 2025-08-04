package com.researchspace.fieldmark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FieldmarkRecordsCsvExportTest {

  FieldmarkRecordsCsvExport underTest;

  @BeforeEach
  void init() throws IOException {
    byte[] csvFileInByte = IOUtils.resourceToByteArray("/files/notebook.csv");
    underTest = new FieldmarkRecordsCsvExport(csvFileInByte);
  }

  @ParameterizedTest
  @ValueSource(strings = {"identifier","record_id","revision_id","type","updated_by","updated","Field-ID","hridPrimary-Next-Section","Survey-Number","IGSN-QR-Code","Sample-Location","Sample-Location_latitude","Sample-Location_longitude","New-Text-Field","Description","Sample-Photograph","Length-mm","Width-mm","Thickness-mm"})
  void testAllColumnsAreIngested(String columnName) {
    Map<String, String> firstRecord = underTest.getRecords().values().stream().findFirst().orElseThrow();
    assertTrue(firstRecord.containsKey(columnName));
  }

  @ParameterizedTest
  @ValueSource(strings = {"rec-5eb53c21-d7f8-41a7-a8b5-4900e46cf8e0","rec-b189e6ec-b760-4b44-8789-0ddc35d7cde2","rec-e881323c-d3cb-4393-9784-07b86585675b"})
  void testAllRecordsAreIngested(String recordId) {
    assertEquals(3, underTest.getRecords().size());
    assertNotNull(underTest.getRecord(recordId));
    assertEquals(40, underTest.getRecord(recordId).size());
  }

}