package com.researchspace.fieldmark.client;

import com.researchspace.fieldmark.model.FieldmarkNotebook;
import com.researchspace.fieldmark.model.FieldmarkRecordsCsvExport;
import com.researchspace.fieldmark.model.FieldmarkRecordsJsonExport;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.springframework.web.client.HttpServerErrorException;

/*
 * This interface declares the operations that this library supports with
 * regard to the Fieldmark API.
 */
public interface FieldmarkClient {

  /***
   *
   * This method is in charge to call the Fieldmark end point to receive the overall information
   * and metadata of a given {@param notebookId}.
   *
   * @param accessToken the bearer access token to call the Fieldmark APIs
   * @return a list of all the notebooks owned by the user
   * @throws MalformedURLException
   * @throws URISyntaxException
   * @throws HttpServerErrorException
   */
  List<FieldmarkNotebook> getNotebooks(String accessToken)
      throws MalformedURLException, URISyntaxException, HttpServerErrorException;


  /***
   * This method is in charge to call the Fieldmark end point to receive the overall information
   * and metadata of a given {@param notebookId}.
   *
   * @param accessToken the bearer access token to call the Fieldmark APIs
   * @param notebookId the name of the notebook we need to query
   * @return an object wrapping the overall notebook (without records) including metadata
   * @throws HttpServerErrorException
   */
  FieldmarkNotebook getNotebook(String accessToken, String notebookId)
      throws HttpServerErrorException;


  /***
   * This method is in charge to call the Fieldmark end point to receive all the records
   * (in a JSON format) for all the Forms existing in a given {@param notebookId}.
   *
   * At the moment we support ONLY notebooks with a singe formID
   * So, in case there are more than one formID, the return object could not be consistent
   *
   * @param accessToken the bearer access token to call the Fieldmark APIs
   * @param notebookId the name of the notebook we need to query
   * @return an object wrapping all the records for a given notebook
   * @throws HttpServerErrorException
   */
  FieldmarkRecordsJsonExport getNotebookRecords(String accessToken, String notebookId)
      throws HttpServerErrorException;

  /***
   * This method is in charge to call the Fieldmark end point to receive the CSV file having
   * all the records for a given {@param notebookId} and {@param formId}
   *
   * @param accessToken the bearer access token to call the Fieldmark APIs
   * @param notebookId the name of the notebook we need to query
   * @param formId the name of the form we need to query
   * @return an object wrapping the CSV file with all the records
   * @throws HttpServerErrorException
   * @throws IOException
   */
  FieldmarkRecordsCsvExport getNotebookCsv(String accessToken, String notebookId, String formId)
      throws HttpServerErrorException, IOException;

  /***
   * This method is in charge to call the Fieldmark end point to receive the ZIP file with all the
   * attached file on the notebook.
   *
   * It returns the map of the files that are attached on a given {@param notebookId}
   * and {@param formId}
   *
   * @param accessToken the bearer access token to call the Fieldmark APIs
   * @param notebookId the name of the notebook we need to query
   * @param formId the name of the form we need to query
   * @return a Map<unique path of the File, File in byte[]>
   * @throws IOException
   */
  Map<String, byte[]> getNotebookFiles(String accessToken, String notebookId, String formId)
      throws HttpServerErrorException, IOException;

}
