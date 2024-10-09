package com.researchspace.fieldmark.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.researchspace.fieldmark.model.FieldmarkNotebook;
import com.researchspace.fieldmark.model.FieldmarkRecordsCsvExport;
import com.researchspace.fieldmark.model.FieldmarkRecordsJsonExport;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class FieldmarkClientTest {

  private final FieldmarkClientImpl fieldmarkClientImpl = new FieldmarkClientImpl();
  private final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImZpZWxkbWFyayJ9.eyJfY291Y2hkYi5yb2xlcyI6WyIxNzI2MTI2MjA0NjE4LXJzcGFjZS1pZ3NuLWRlbW98fGFkbWluIl0sIm5hbWUiOiJSZXNlYXJjU3BhY2UgZGV2ZWxvcGVyIiwic3ViIjoiZGV2QHJlc2VhcmNoc3BhY2UuY29tIiwiaWF0IjoxNzI4MzkwODg1LCJpc3MiOiJEZW1vIEZpZWxkbWFyayBTZXJ2ZXIifQ.GxZfYvDYJiTTwr-rcVCjfarSOr21ezISK2YEptwbH0gZPVMC50Jf2PHHkMT6EQaILQCKHQIChHEjcsfhDoP_7yMZnVb6bwaUE2jSvDlRngKtWcym7dgcL7l5ZvXvMyTEGDw-ILLJMhqodViYbmJiipVc6JiS603pA2nynXF8FBE9m2P1Ml0ZR-ifuNRqfccY596EqKYz3qWXedPhmtDLeqlm4MEO5TZU-vlB3PsOqlw7ROx4mzDwRyEPZIUenyubf2wg5IGS9aN1W1N5YWWvrzAOQrcQvPHsjrO1SaG031MNK8XQrdq3HDykN84z4uYOPHWY-2_gV_UKCxDsRrlSOA";
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
    List<File> result =  fieldmarkClientImpl.getNotebookFiles(ACCESS_TOKEN, NOTEBOOK_ID, FORM_ID);
    assertNotNull(result);
  }

}