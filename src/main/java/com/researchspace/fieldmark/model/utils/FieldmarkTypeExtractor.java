package com.researchspace.fieldmark.model.utils;

public class FieldmarkTypeExtractor<T> {

  protected Object fieldValue;

  public FieldmarkTypeExtractor(Object fieldValue){
    this.fieldValue = fieldValue;
  }

  public T extractFieldValue(Object fieldValue){
    return (T) fieldValue;
  }



}
