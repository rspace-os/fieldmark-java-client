package com.researchspace.fieldmark.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.researchspace.fieldmark.model.FieldmarkNotebook;
import com.researchspace.fieldmark.model.FieldmarkRecordsCsvExport;
import com.researchspace.fieldmark.model.FieldmarkRecordsJsonExport;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("We leave the test Disabled so we can potentially run it "
    + "manually by pasting the bearer token here below")
class FieldmarkClientRealConnectionTest {

  private final FieldmarkClientImpl fieldmarkClientImpl = new FieldmarkClientImpl();
  private final String ACCESS_TOKEN = "_______PASTE_TOKEN_HERE_________";
  private final String NOTEBOOK_ID = "1726126204618-rspace-igsn-demo";
  private final String FORM_ID = "Primary";

  @BeforeEach
  public void setUp(){
    fieldmarkClientImpl.setFieldmarkBaseUrl("https://conductor.fieldmark.app/api");
  }

  @Test
  public void testGetNotebooks() {
    List<FieldmarkNotebook> result =  fieldmarkClientImpl.getNotebooks(ACCESS_TOKEN);
    assertNotNull(result);
  }

 @Test
  public void testGetNotebook() {
    FieldmarkNotebook result =  fieldmarkClientImpl.getNotebook(ACCESS_TOKEN, NOTEBOOK_ID);
    assertNotNull(result);
  }

  @Test
  public void testGetNotebookRecords() {
    FieldmarkRecordsJsonExport result =  fieldmarkClientImpl.getNotebookRecords(ACCESS_TOKEN, NOTEBOOK_ID);
    assertNotNull(result);
  }

  @Test
  public void testGetNotebookCsv() throws IOException {
    FieldmarkRecordsCsvExport result =  fieldmarkClientImpl.getNotebookCsv(ACCESS_TOKEN, NOTEBOOK_ID, FORM_ID);
    assertNotNull(result.getRecords());
  }

  @Test
  public void testGetNotebookFiles() throws IOException {
    Map<String, byte[]> result =  fieldmarkClientImpl.getNotebookFiles(ACCESS_TOKEN, NOTEBOOK_ID, FORM_ID);
    assertNotNull(result);
  }

}