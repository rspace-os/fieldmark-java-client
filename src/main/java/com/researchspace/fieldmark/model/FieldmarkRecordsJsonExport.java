package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.researchspace.fieldmark.model.exception.FieldmarkUnsupportedNotebookException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldmarkRecordsJsonExport {

  private static final String FILE_TYPE = "FILES";

  private List<FieldmarkRecord> records;

  // records are kept as Map<record_id, FieldmarkRecord>
  private Map<String, FieldmarkRecord> recordsById;
  private Boolean hasFiles;

  public void setRecords(List<FieldmarkRecord> records) {
    this.records = records;
  }

  public void setFieldTypes(Map<String, String> fieldTypes){
    for (FieldmarkRecord currentRecord : this.records) {
      currentRecord.setFieldTypes(fieldTypes);
    }
    performRecordIndexing();
  }

  public List<FieldmarkRecord> getRecords() {
    return Collections.unmodifiableList(records);
  }

  public Map<String, FieldmarkRecord> getRecordsById() {
    return Collections.unmodifiableMap(recordsById);
  }


  public FieldmarkRecord getFirstRecord() {
    return records.get(0);
  }

  /***
   * This method returns the name of the formID given by the very first record of the notebook
   *
   * MVP: At the moment we support 1 FORM_ID per Notebook
   *
   * @return the name of the formID
   */
  public String getFormId() {
    return getFirstRecord().getType();
  }


  private void performRecordIndexing() {
    if (recordsById == null) {
      recordsById = new LinkedHashMap<>();
      Iterator<FieldmarkRecord> recordIterator = records.iterator();
      FieldmarkRecord currentRecord;
      String formId = null;
      while (recordIterator.hasNext()) {
        currentRecord = recordIterator.next();
        recordsById.put(currentRecord.getRecordId(), currentRecord);
        if (formId == null) {
          formId = currentRecord.getType(); // the formID
        } else {
          if (!formId.equals(currentRecord.getType())) {
            throw new FieldmarkUnsupportedNotebookException(
                "The notebook has more than one FormID: "
                    + "[" + formId + ", " + currentRecord.getType() + "]\n"
                    + "At the moment it is not possible to import Fieldmark notebooks with more "
                    + "than one FormID");
          }
        }
      }
      if (this.hasFiles == null) {
        this.hasFiles = records.get(0).getFieldTypes().keySet().stream()
            .anyMatch(
                fieldName -> FILE_TYPE.equalsIgnoreCase(getFieldType(fieldName).orElse(null))
            );
      }
    }
  }

  public Optional<String> getFieldType(String fieldName) {
    return getFirstRecord().getFieldType(fieldName);
  }

  public Boolean hasFiles() {
    return this.hasFiles;
  }

}
