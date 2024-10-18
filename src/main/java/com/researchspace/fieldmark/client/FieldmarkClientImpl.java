package com.researchspace.fieldmark.client;

import com.researchspace.fieldmark.model.FieldmarkNotebook;
import com.researchspace.fieldmark.model.FieldmarkRecordsCsvExport;
import com.researchspace.fieldmark.model.FieldmarkRecordsJsonExport;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@Slf4j
@Service
public class FieldmarkClientImpl implements FieldmarkClient {

  @Value("${fieldmark.api.url}")
  private String fieldmarkBaseUrl;

  private RestTemplate restTemplate;

  public FieldmarkClientImpl() {
    restTemplate = new RestTemplate();
  }

  @Override
  public List<FieldmarkNotebook> getNotebooks(String accessToken) {
    return restTemplate
        .exchange(
            fieldmarkBaseUrl + "/notebooks/",
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            List.class)
        .getBody();
  }

  @Override
  public FieldmarkNotebook getNotebook(String accessToken, String notebookId) {
    return restTemplate
        .exchange(
            fieldmarkBaseUrl + "/notebooks/" + notebookId,
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            FieldmarkNotebook.class)
        .getBody();
  }

  @Override
  public FieldmarkRecordsJsonExport getNotebookRecords(String accessToken, String notebookId) {
    return restTemplate
        .exchange(
            fieldmarkBaseUrl + "/notebooks/" + notebookId + "/records",
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            FieldmarkRecordsJsonExport.class)
        .getBody();
  }

  @Override
  public FieldmarkRecordsCsvExport getNotebookCsv(String accessToken, String notebookId,
      String formId) throws IOException {
    byte[] csvFileBytes =
        restTemplate
            .exchange(
                fieldmarkBaseUrl + "/notebooks/" + notebookId + "/" + formId + ".csv",
                HttpMethod.GET,
                new HttpEntity<>(createHttpHeaders(accessToken)),
                byte[].class)
            .getBody();
    return new FieldmarkRecordsCsvExport(csvFileBytes);
  }

  @Override
  public List<File> getNotebookFiles(String accessToken, String notebookId, String formId)
      throws IOException {
    List<File> result = new LinkedList<>();
    byte[] zipFileBytes =
        restTemplate
            .exchange(
                fieldmarkBaseUrl + "/notebooks/" + notebookId + "/" + formId + ".zip",
                HttpMethod.GET,
                new HttpEntity<>(createHttpHeaders(accessToken)),
                byte[].class)
            .getBody();

    File fileFromBytes = null;

    ///// save the photos to the filesystem
    try {
      fileFromBytes = new File("file.zip");
      FileUtils.writeByteArrayToFile(fileFromBytes, zipFileBytes);

      try (ZipFile zipFile = new ZipFile(fileFromBytes)) {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
          ZipEntry entry = entries.nextElement();
          // Check if entry is not a directory
          if (!entry.isDirectory()) {
            File newPicture = new File(entry.getName());
            FileUtils.copyInputStreamToFile(
                zipFile.getInputStream(entry), newPicture);
            result.add(newPicture);
          }
        }
      }
    } finally {
      if (fileFromBytes != null) {
        FileUtils.delete(fileFromBytes);
      }
    }
    return result;
  }

  private static HttpHeaders createHttpHeaders(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", String.format("Bearer %s", accessToken));
    return headers;
  }
}
