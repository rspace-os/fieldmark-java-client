package com.researchspace.fieldmark.model.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.researchspace.fieldmark.model.exception.FieldmarkExtractorParseException;
import org.junit.jupiter.api.Test;

class FieldmarkTypeExtractorTest {

  @Test
  void testExtractFieldValueVanilla() {
    FieldmarkTypeExtractor<Integer> underTest = new FieldmarkTypeExtractor<>(Integer.MAX_VALUE,
        Integer.class);
    assertEquals(Integer.class, underTest.getFieldType());
    assertEquals(Integer.MAX_VALUE, underTest.getFieldValue());
    assertEquals(String.valueOf(Integer.MAX_VALUE), underTest.extractStringFieldValue());
    assertEquals("Integer", underTest.getSimpleTypeName());
    assertFalse(underTest.isDoiIdentifier());
  }

  @Test
  void testExtractFieldValueThrowException() {
    FieldmarkExtractorParseException thrown = assertThrows(
        FieldmarkExtractorParseException.class,
        () -> new FieldmarkTypeExtractor<>(Integer.MAX_VALUE, null),
        "FieldmarkTypeExtractor did not throw the exception, but it was needed"
    );
    assertTrue(thrown.getMessage().contains("FieldType cannot be null"));
  }

  @Test
  void testExtractFieldValueWhenNull() {
    FieldmarkTypeExtractor<Integer> underTest = new FieldmarkTypeExtractor<>(null, Integer.class);
    assertEquals(Integer.class, underTest.getFieldType());
    assertNull(underTest.getFieldValue());
    assertEquals("", underTest.extractStringFieldValue());
  }

}