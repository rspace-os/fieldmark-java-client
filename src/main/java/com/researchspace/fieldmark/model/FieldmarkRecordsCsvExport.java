package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvParser.Feature;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public class FieldmarkRecordsCsvExport {

  private static final String IDENTIFIER = "record_id";

  // records are kept as  Map<record_id, Map<fieldName, fieldValue>>
  private Map<String, Map<String, String>> records;

  @Getter
  private final InputStream csvFile;

  public FieldmarkRecordsCsvExport(byte[] csvFileBytes)  {
    csvFile = new ByteArrayInputStream(csvFileBytes);
  }

  public Map<String, Map<String, String>> getRecords() {
    if (records == null) {
      records = new LinkedHashMap<>();
      CsvMapper mapper = new CsvMapper();
      mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
      mapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
      mapper.enable(CsvParser.Feature.TRIM_SPACES);
      mapper.enable(Feature.FAIL_ON_MISSING_COLUMNS);
      try {
        MappingIterator<String[]> linesIterator =
            mapper.readerFor(String[].class).readValues(csvFile);
        List<String[]> csvLines = linesIterator.readAll();

        Iterator<String[]> lineIterator = csvLines.iterator();
        String[] columnNames;
        String[] lineValues;
        if (lineIterator.hasNext()) { // read the 1st line: column names
          columnNames = lineIterator.next();

          while (lineIterator.hasNext()) { // from the 2nd line
            lineValues = lineIterator.next();
            Map<String, String> valueByName = new LinkedHashMap<>();
            for (int i = 0; i < lineValues.length; i++) {
              valueByName.put(columnNames[i], lineValues[i]);
            }
            records.put(valueByName.get(IDENTIFIER), Collections.unmodifiableMap(valueByName));
          }
        }
      } catch (Exception e){
        throw new IllegalArgumentException(
            "It was not possible to extract the lines from the CSV: ", e);
      }
    }
    return Collections.unmodifiableMap(records);
  }

  public Map<String, String> getRecord(String recordId) {
    return getRecords().get(recordId);
  }

  public String getStringFieldValue(String recordId, String fieldName) {
    return getRecord(recordId).get(fieldName);
  }

}
