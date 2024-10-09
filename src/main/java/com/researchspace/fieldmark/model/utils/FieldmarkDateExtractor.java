package com.researchspace.fieldmark.model.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FieldmarkDateExtractor extends FieldmarkTypeExtractor<LocalDate> {

  public FieldmarkDateExtractor(Object fieldValue) {
    super(fieldValue);
  }

  public LocalDate extractFieldValue(){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    return LocalDate.parse((String) this.fieldValue, formatter);
  }

}
