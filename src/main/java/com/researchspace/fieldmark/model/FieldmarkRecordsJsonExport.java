package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Arrays;
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


  public List<FieldmarkRecord> getRecords(){
    return Collections.unmodifiableList(records);
  }

  public FieldmarkRecord getRecord(String recordId){
    performRecordIndexing();
    return recordsById.get(recordId);
  }

  public Object getFieldValue(String recordId, String fieldName) {
    return getRecord(recordId).getData().get(fieldName);
  }

  public String getFieldType(String recordId, String fieldName) {
    return getRecord(recordId).getFieldType(fieldName);
  }

  private void performRecordIndexing(){
    if(recordsById == null){
      recordsById = new LinkedHashMap<>();
      Iterator<FieldmarkRecord> recordIterator = records.iterator();
      FieldmarkRecord currentRecord;
      while(recordIterator.hasNext()){
        currentRecord = recordIterator.next();
        recordsById.put(currentRecord.getRecordId(), currentRecord);
      }
    }
  }

}
