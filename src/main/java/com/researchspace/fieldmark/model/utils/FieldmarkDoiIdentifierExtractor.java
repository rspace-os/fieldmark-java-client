package com.researchspace.fieldmark.model.utils;

import com.researchspace.fieldmark.model.FieldmarkDoiIdentifier;

public class FieldmarkDoiIdentifierExtractor extends FieldmarkTypeExtractor<FieldmarkDoiIdentifier> {

  public FieldmarkDoiIdentifierExtractor(Object fieldValue) {
    super(FieldmarkDoiIdentifier.class);
    if(fieldValue != null){
      this.fieldValue = new FieldmarkDoiIdentifier((String) fieldValue);
    }
  }

}
