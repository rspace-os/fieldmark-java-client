package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.researchspace.fieldmark.model.utils.FieldmarkTypeExtractor;
import com.researchspace.fieldmark.model.utils.factory.FieldmarkTypeExtractorFactory;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldmarkRecord {

  @JsonProperty("project_id")
  private String projectId;

  @JsonProperty("record_id")
  private String recordId;

  @JsonProperty("revision_id")
  private String revisionId;

  private String type;

  private Map<String, Object> data;

  @JsonProperty("updated_by")
  private String updatedBy;

  private Date updated;
  private Date created;

  @JsonProperty("created_by")
  private String createdBy;

  @JsonProperty("field_types")
  private Map<String, String> fieldTypes;

  private boolean deleted;

  public Optional<String> getFieldType(String fieldName) {
    try {
      return Optional.of(getFieldTypes().get(fieldName).split("::")[1]);
    } catch (ArrayIndexOutOfBoundsException | NullPointerException ex){
      return Optional.empty();
    }
  }

  public FieldmarkTypeExtractor createFieldTypeExtractor(String fieldName) throws NoSuchElementException {
    return FieldmarkTypeExtractorFactory.getTypeExtractorInstance(
        getObjectFieldValue(fieldName),
        getFieldType(fieldName).orElseThrow());
  }

  public Map<String, Object> getFieldList(){
    return Collections.unmodifiableMap(this.data);
  }

  private Object getObjectFieldValue(String fieldName) {
    return this.data.get(fieldName);
  }

}
