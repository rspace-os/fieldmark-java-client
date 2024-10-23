package com.researchspace.fieldmark.model.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FieldmarkDatetimeExtractor extends FieldmarkTypeExtractor<LocalDateTime> {

  public FieldmarkDatetimeExtractor(Object fieldValue) {
    super(fieldValue);
  }

  public LocalDateTime extractFieldValue(){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    return LocalDateTime.parse((String) this.fieldValue, formatter);
  }

}
