package com.researchspace.fieldmark.model.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.researchspace.fieldmark.model.exception.FieldmarkExtractorParseException;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
/***
 * This utility class wrap the value and the type of a specific field (in a Fieldmark Record)
 * It finally has utility methods in order to return the value in with the right java type
 *
 * @param <T>
 */
public class FieldmarkTypeExtractor<T> {

  protected T fieldValue;

  protected Class<T> fieldType;


  public FieldmarkTypeExtractor(T fieldValue, Class<T> fieldType) {
    if (fieldType == null) {
      throw new FieldmarkExtractorParseException("FieldType cannot be null");
    }
    this.fieldValue = fieldValue;
    this.fieldType = fieldType;
  }

  /***
   * Extract the fieldValue with his own java type
   *
   * @return the fieldValue or null
   */
  public T getFieldValue() {
    return this.fieldValue;
  }

  /***
   * Extract the fieldValue in the String format
   *
   * @return the fieldValue in String format or ""
   */
  public String extractStringFieldValue() {
    T value = this.getFieldValue();
    return value != null ? value.toString() : "";
  }

  /***
   * Extract the simple name of the fieldType class
   *
   * @return the simple name of the fieldType in a String format
   */
  public String getSimpleTypeName() {
    return this.fieldType.getSimpleName();
  }

}
