package com.researchspace.fieldmark.model.exception;

public class FieldmarkImportException extends RuntimeException {

  public FieldmarkImportException(String message) {
    super(message);
  }

  public FieldmarkImportException(Throwable cause){
    super(cause);
  }

}
