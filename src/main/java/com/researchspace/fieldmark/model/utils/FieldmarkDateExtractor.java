package com.researchspace.fieldmark.model.utils;

import com.researchspace.fieldmark.model.exception.FieldmarkExtractorParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class FieldmarkDateExtractor extends FieldmarkTypeExtractor<String> {


  protected static final String DATE_PATTERN = "yyyy-MM-dd";
  private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN,
      Locale.ENGLISH);

  public FieldmarkDateExtractor(Object fieldValue) {
    super(fieldValue, String.class);
    if (fieldValue != null) {
      try {
        LocalDate.parse(this.fieldValue, formatter);
      } catch (DateTimeParseException ex) {
        throw new FieldmarkExtractorParseException("Date \"" + this.fieldValue
            + "\" cannot be ingested since it is not in the format " + DATE_PATTERN);
      }
    }
  }


}
