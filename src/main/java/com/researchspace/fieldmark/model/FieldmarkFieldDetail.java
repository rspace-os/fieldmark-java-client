package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldmarkFieldDetail {

  private static final String SEPARATOR = "::";

  @JsonProperty("type-returned")
  private String fieldType;

  /***
   * The type-returned by Fieldmark could be the following:
   *     'type-returned': 'faims-attachment::Files'
   *     'type-returned': 'faims-core::Array'
   *     'type-returned': 'faims-core::Bool'
   *     'type-returned': 'faims-core::Date'
   *     'type-returned': 'faims-core::Datetime'
   *     'type-returned': 'faims-core::Email'
   *     'type-returned': 'faims-core::Integer'
   *     'type-returned': 'faims-core::JSON'
   *     'type-returned': 'faims-core::Relationship'
   *     'type-returned': 'faims-core::String'
   *     'type-returned': 'faims-pos::Location'
   * so this utility method strips the firs part and just return the second part
   * after the "::" token (i.e. for "faims-core::String" it would return just "String")
   *
   * @return the type of the Fieldmark data in a String format
   */
  public String getFieldType() {
    try {
      return StringUtils.isNotBlank(fieldType) ? fieldType.split(SEPARATOR)[1] : "";
    } catch (IndexOutOfBoundsException ex){
      return "";
    }
  }

}
