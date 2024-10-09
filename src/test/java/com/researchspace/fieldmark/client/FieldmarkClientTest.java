package com.researchspace.fieldmark.client;


import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.researchspace.fieldmark.model.FieldmarkNotebook;
import com.researchspace.fieldmark.model.FieldmarkRecordsCsvExport;
import com.researchspace.fieldmark.model.FieldmarkRecordsJsonExport;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

class FieldmarkClientTest {

  private FieldmarkClientImpl fieldmarkClient;
  private MockRestServiceServer mockServer;

  @BeforeEach
  public void init() {
    fieldmarkClient = new FieldmarkClientImpl();
    mockServer = MockRestServiceServer.createServer(fieldmarkClient.getRestTemplate());
  }

  @Test
  public void testGetNotebooks() throws IOException {
    String notebooksJson = IOUtils.resourceToString("/json/notebooks.json",
        Charset.defaultCharset());
    mockServer.expect(requestTo(containsString("/notebooks/")))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(notebooksJson, MediaType.APPLICATION_JSON));

    List<FieldmarkNotebook> notebooks = fieldmarkClient.getNotebooks("");
    assertNotNull(notebooks);
    assertEquals(1, notebooks.size());
    assertEquals("1726126204618-rspace-igsn-demo", notebooks.get(0).getNonUniqueProjectId());
    assertEquals("RSpace IGSN Demo", notebooks.get(0).getName());
  }

  @Test
  public void testGetNotebookId() throws IOException {
    String notebookIdJson = IOUtils.resourceToString("/json/notebookID.json",
        Charset.defaultCharset());
    mockServer.expect(requestTo(containsString("/notebooks/1726126204618-rspace-igsn-demo")))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(notebookIdJson, MediaType.APPLICATION_JSON));

    FieldmarkNotebook notebook = fieldmarkClient.getNotebook("", "1726126204618-rspace-igsn-demo");
    assertNotNull(notebook);
    assertNotNull(notebook.getMetadata());
    assertEquals("RSpace IGSN Demo", notebook.getMetadata().getName());
    assertEquals("1726126204618-rspace-igsn-demo", notebook.getMetadata().getProjectId());
  }

  @Test
  public void testGetNotebookRecords() throws IOException {
    String notebookRecordsJson = IOUtils.resourceToString("/json/records.json",
        Charset.defaultCharset());
    mockServer.expect(requestTo(containsString("/notebooks/1726126204618-rspace-igsn-demo/records")))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(notebookRecordsJson, MediaType.APPLICATION_JSON));

    FieldmarkRecordsJsonExport notebook = fieldmarkClient.getNotebookRecords("", "1726126204618-rspace-igsn-demo");
    assertNotNull(notebook);
    assertNotNull(notebook.getRecords());
    assertEquals(3, notebook.getRecords().size());
  }

  @Test
  public void testGetCsvRecords() throws IOException {
    String notebookRecordsCsv = IOUtils.resourceToString("/files/notebook.csv",
        Charset.defaultCharset());
    mockServer.expect(requestTo(containsString("/notebooks/1726126204618-rspace-igsn-demo/Primary.csv")))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(notebookRecordsCsv, MediaType.APPLICATION_JSON));

    FieldmarkRecordsCsvExport csvRecords = fieldmarkClient.getNotebookCsv("", "1726126204618-rspace-igsn-demo", "Primary");

    assertNotNull(csvRecords);
    assertNotNull(csvRecords.getRecords());
    assertEquals(3, csvRecords.getRecords().size());
  }

  @Test
  public void testGetFieldmarkZipFile() throws IOException {
    byte[] zipFile = IOUtils.resourceToByteArray("/files/FieldmarkFile.zip");
    mockServer.expect(requestTo(containsString("/notebooks/1726126204618-rspace-igsn-demo/Primary.zip")))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(zipFile, MediaType.APPLICATION_OCTET_STREAM));

    Map<String, byte[]> files = fieldmarkClient.getNotebookFiles("", "1726126204618-rspace-igsn-demo", "Primary");

    assertNotNull(files);
    assertEquals(3, files.size());
  }

}