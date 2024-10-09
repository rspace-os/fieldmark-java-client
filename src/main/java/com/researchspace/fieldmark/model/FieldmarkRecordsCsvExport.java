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

public class FieldmarkRecordsCsvExport {

  private static final String IDENTIFIER = "record_id";

  // records are kept as  Map<record_id, Map<fieldName, fieldValue>>
  private final Map<String, Map<String, String>> records = new LinkedHashMap<>();

  public FieldmarkRecordsCsvExport(byte[] csvFileBytes) throws IOException {
    InputStream csvStream = new ByteArrayInputStream(csvFileBytes);

    CsvMapper mapper = new CsvMapper();
    mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
    mapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
    mapper.enable(CsvParser.Feature.TRIM_SPACES);
    mapper.enable(Feature.FAIL_ON_MISSING_COLUMNS);
    MappingIterator<String[]> linesIterator =
        mapper.readerFor(String[].class).readValues(csvStream);
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
  }

  public Map<String, Map<String, String>> getRecords() {
    return Collections.unmodifiableMap(records);
  }

  public Map<String, String> getRecord(String recordId) {
    return records.get(recordId);
  }

  public String getStringFieldValue(String recordId, String fieldName) {
    return getRecord(recordId).get(fieldName);
  }

}
