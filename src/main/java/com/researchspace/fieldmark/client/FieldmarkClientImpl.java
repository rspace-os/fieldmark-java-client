package com.researchspace.fieldmark.client;

import static com.researchspace.fieldmark.model.utils.FieldmarkUtils.createFilesMap;

import com.researchspace.fieldmark.model.FieldmarkNotebook;
import com.researchspace.fieldmark.model.FieldmarkRecordsCsvExport;
import com.researchspace.fieldmark.model.FieldmarkRecordsJsonExport;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  private static class FieldmarkToken {

    private String token;
  }

  private String getShortLivedAccessToken(String longLivedToken) {
    FieldmarkToken accessToken =
        restTemplate
            .exchange(
                fieldmarkBaseUrl + "/auth/exchange-long-lived-token",
                HttpMethod.POST,
                new HttpEntity<>(new FieldmarkToken(longLivedToken), null),
                FieldmarkToken.class)
            .getBody();
    return accessToken.getToken();
  }

  @Override
  public List<FieldmarkNotebook> getNotebooks(String longLivedToken)
      throws HttpServerErrorException {
    String shortLivedToken = getShortLivedAccessToken(longLivedToken);
    return Arrays.asList(Objects.requireNonNull(restTemplate
        .exchange(
            fieldmarkBaseUrl + "/notebooks/",
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(shortLivedToken)),
            FieldmarkNotebook[].class)
        .getBody()));
  }

  @Override
  public FieldmarkNotebook getNotebook(String longLivedToken, String notebookId)
      throws HttpServerErrorException {
    String shortLivedToken = getShortLivedAccessToken(longLivedToken);
    return restTemplate
        .exchange(
            fieldmarkBaseUrl + "/notebooks/" + notebookId,
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(shortLivedToken)),
            FieldmarkNotebook.class)
        .getBody();
  }

  @Override
  public FieldmarkRecordsJsonExport getNotebookRecords(String longLivedToken, String notebookId)
      throws HttpServerErrorException {
    String shortLivedToken = getShortLivedAccessToken(longLivedToken);
    return restTemplate
        .exchange(
            fieldmarkBaseUrl + "/notebooks/" + notebookId + "/records",
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(shortLivedToken)),
            FieldmarkRecordsJsonExport.class)
        .getBody();
  }

  @Override
  public FieldmarkRecordsCsvExport getNotebookCsv(String longLivedToken, String notebookId,
      String formId) throws HttpServerErrorException {
    String shortLivedToken = getShortLivedAccessToken(longLivedToken);
    byte[] csvFileBytes =
        restTemplate
            .exchange(
                fieldmarkBaseUrl + "/notebooks/" + notebookId + "/records/" + formId + ".csv",
                HttpMethod.GET,
                new HttpEntity<>(createHttpHeaders(shortLivedToken)),
                byte[].class)
            .getBody();
    return new FieldmarkRecordsCsvExport(csvFileBytes);
  }

  @Override
  public Map<String, byte[]> getNotebookFiles(String longLivedToken, String notebookId,
      String formId)
      throws HttpServerErrorException, IOException {
    String shortLivedToken = getShortLivedAccessToken(longLivedToken);
    byte[] zipFileBytes =
        restTemplate
            .exchange(
                fieldmarkBaseUrl + "/notebooks/" + notebookId + "/records/" + formId + ".zip",
                HttpMethod.GET,
                new HttpEntity<>(createHttpHeaders(shortLivedToken)),
                byte[].class)
            .getBody();

    return createFilesMap(notebookId, zipFileBytes);
  }

  private static HttpHeaders createHttpHeaders(String shortLivedToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", String.format("Bearer %s", shortLivedToken));
    return headers;
  }
}
