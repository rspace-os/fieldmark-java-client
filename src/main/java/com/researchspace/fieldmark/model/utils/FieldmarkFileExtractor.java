package com.researchspace.fieldmark.model.utils;

import lombok.Getter;
import lombok.Setter;

public class FieldmarkFileExtractor extends FieldmarkTypeExtractor<byte[]> {

  @Setter @Getter
  private String fileName;

  public FieldmarkFileExtractor() {
    super(null, byte[].class);
  }

  @Override
  public String extractStringFieldValue() {
    throw new UnsupportedOperationException(
        "FieldmarkFileExtractor does not implements this method");
  }


}
