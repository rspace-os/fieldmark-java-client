package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.researchspace.fieldmark.model.utils.FieldmarkTypeExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkTypeExtractorFactory;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldmarkRecordsJsonExport {

  private List<FieldmarkRecord> records;

  // records are kept as Map<record_id, FieldmarkRecord>
  private Map<String, FieldmarkRecord> recordsById;
  private Boolean hasFiles;


  private List<FieldmarkRecord> getRecords() {
    performRecordIndexing();
    return Collections.unmodifiableList(records);
  }

  private Map<String, FieldmarkRecord> getRecordsDyId() {
    performRecordIndexing();
    return Collections.unmodifiableMap(recordsById);
  }

  private FieldmarkRecord getRecord(String recordId) {
    performRecordIndexing();
    return recordsById.get(recordId);
  }

  public FieldmarkRecord getFirstRecord() {
    performRecordIndexing();
    return recordsById.values().stream().findFirst().orElse(null);
  }

  /***
   * MVP: At the moment we support only 1 FORM_ID per Notebook
   *
   * @return
   */
  public String getFormId() {
    return getFirstRecord().getType();
  }

  public FieldmarkTypeExtractor getFieldTypeExtractor(String recordId, String fieldName) {
    return FieldmarkTypeExtractorFactory.getTypeExtractor(
        getObjectFieldValue(recordId, fieldName),
        getFieldType(fieldName));
  }

  private void performRecordIndexing() {
    if (recordsById == null) {
      recordsById = new LinkedHashMap<>();
      Iterator<FieldmarkRecord> recordIterator = records.iterator();
      FieldmarkRecord currentRecord;
      while (recordIterator.hasNext()) {
        currentRecord = recordIterator.next();
        recordsById.put(currentRecord.getRecordId(), currentRecord);
      }
      if (this.hasFiles == null) {
        this.hasFiles = records.get(0).getFieldTypes().keySet().stream()
            .anyMatch(fieldName -> "FILES".equalsIgnoreCase(getFieldType(fieldName)));
      }
    }
  }


  private Object getObjectFieldValue(String recordId, String fieldName) {
    return getRecord(recordId).getData().get(fieldName);
  }

  public String getFieldType(String fieldName) {
    return getFirstRecord().getFieldType(fieldName);
  }

  public Boolean hasFiles() {
    performRecordIndexing();
    return this.hasFiles;
  }

  private Boolean getHasFiles() {
    return this.hasFiles;
  }

}
