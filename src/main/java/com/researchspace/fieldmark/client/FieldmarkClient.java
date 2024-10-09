package com.researchspace.fieldmark.client;

import com.researchspace.fieldmark.model.FieldmarkNotebook;
import com.researchspace.fieldmark.model.FieldmarkRecordsCsvExport;
import com.researchspace.fieldmark.model.FieldmarkRecordsJsonExport;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

/*
 * This interface declares the operations that this library supports with
 * regard to the Fieldmark API.
 */
public interface FieldmarkClient {

  List<FieldmarkNotebook> getNotebooks(String accessToken)
      throws MalformedURLException, URISyntaxException;

  FieldmarkNotebook getNotebook(String accessToken, String notebookId);

  FieldmarkRecordsJsonExport getNotebookRecords(String accessToken, String notebookId);

  FieldmarkRecordsCsvExport getNotebookCsv(String accessToken, String notebookId, String formId)
      throws IOException;

  List<File> getNotebookFiles(String accessToken, String notebookId, String formId)
      throws IOException;

}
