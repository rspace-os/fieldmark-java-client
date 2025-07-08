package com.researchspace.fieldmark.model.utils;

import com.researchspace.fieldmark.model.exception.FieldmarkExtractorParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class FieldmarkDatetimeExtractor extends FieldmarkTypeExtractor<String> {

  protected static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
  private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN,
      Locale.ENGLISH);

  public FieldmarkDatetimeExtractor(Object fieldValue) {
    super(fieldValue, String.class);
    if (fieldValue != null) {
      try {
        LocalDateTime.parse(this.fieldValue, formatter);
      } catch (DateTimeParseException ex) {
        throw new FieldmarkExtractorParseException(
            "DateTime \"" + this.fieldValue + "\" cannot be ingested since it is not in the format "
                + DATE_TIME_PATTERN);
      }
    }
  }

  public String extractDateStringValue() {
    if (this.fieldValue != null) {
      return this.fieldValue.substring(0, 10);
    }
    return "";
  }

  public String extractTimeStringValue() {
    if (this.fieldValue != null) {
      return this.fieldValue.substring(11);
    }
    return "";
  }

}
