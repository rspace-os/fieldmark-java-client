package com.researchspace.fieldmark.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldmarkUiSpecification {

  private Map<String, FieldmarkFieldDetail> fields;

}
