package com.researchspace.fieldmark.model.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class FieldmarkFileExtractorTest {

  @Test
  void testExtractFieldValueVanilla() throws IOException {
    FieldmarkFileExtractor underTest = new FieldmarkFileExtractor();
    File file = File.createTempFile("fieldmark_", "_temp");
    FileUtils.writeStringToFile(file, "Hello, World!", Charset.defaultCharset());
    byte[] fileInByte = FileUtils.readFileToByteArray(file);
    underTest.setFieldValue(fileInByte);
    underTest.setFileName("fileName");

    assertEquals(byte[].class, underTest.getFieldType());
    assertEquals(fileInByte, underTest.getFieldValue());
    assertEquals("byte[]", underTest.getSimpleTypeName());
    assertFalse(underTest.isDoiIdentifier());
  }

  @Test
  void testExtractFieldStringValueThrowException() {
    FieldmarkFileExtractor underTest = new FieldmarkFileExtractor();

    UnsupportedOperationException thrown = assertThrows(
        UnsupportedOperationException.class,
        () -> underTest.extractStringFieldValue(),
        "FieldmarkFileExtractor did not throw the exception, but it was needed"
    );
    assertTrue(
        thrown.getMessage().contains("FieldmarkFileExtractor does not implements this method"));

  }

  @Test
  void testExtractFieldValueWhenNull() {
    FieldmarkFileExtractor underTest = new FieldmarkFileExtractor();
    assertEquals(byte[].class, underTest.getFieldType());
    assertNull(underTest.getFieldValue());
  }

}