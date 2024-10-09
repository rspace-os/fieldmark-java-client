package com.researchspace.fieldmark.client;

import static com.researchspace.fieldmark.model.utils.FieldmarkUtils.createFilesMap;

import com.researchspace.fieldmark.model.FieldmarkNotebook;
import com.researchspace.fieldmark.model.FieldmarkRecordsCsvExport;
import com.researchspace.fieldmark.model.FieldmarkRecordsJsonExport;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
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
  public List<FieldmarkNotebook> getNotebooks(String accessToken) throws HttpServerErrorException {
    return Arrays.asList(Objects.requireNonNull(restTemplate
        .exchange(
            fieldmarkBaseUrl + "/notebooks/",
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            FieldmarkNotebook[].class)
        .getBody()));
  }

  @Override
  public FieldmarkNotebook getNotebook(String accessToken, String notebookId)
      throws HttpServerErrorException {
    return restTemplate
        .exchange(
            fieldmarkBaseUrl + "/notebooks/" + notebookId,
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            FieldmarkNotebook.class)
        .getBody();
  }

  @Override
  public FieldmarkRecordsJsonExport getNotebookRecords(String accessToken, String notebookId) throws HttpServerErrorException {
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
      String formId) throws HttpServerErrorException, IOException {
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
  public Map<String, byte[]> getNotebookFiles(String accessToken, String notebookId, String formId)
      throws HttpServerErrorException, IOException {
    Map<String, byte[]> result = new LinkedHashMap<>();
    byte[] zipFileBytes =
        restTemplate
            .exchange(
                fieldmarkBaseUrl + "/notebooks/" + notebookId + "/" + formId + ".zip",
                HttpMethod.GET,
                new HttpEntity<>(createHttpHeaders(accessToken)),
                byte[].class)
            .getBody();

    return createFilesMap(notebookId, zipFileBytes);
  }

  private static HttpHeaders createHttpHeaders(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", String.format("Bearer %s", accessToken));
    return headers;
  }
}
