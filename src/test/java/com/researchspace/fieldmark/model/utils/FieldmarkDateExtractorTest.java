package com.researchspace.fieldmark.model.utils;

import static com.researchspace.fieldmark.model.utils.FieldmarkDateExtractor.DATE_PATTERN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.researchspace.fieldmark.model.exception.FieldmarkExtractorParseException;
import org.junit.jupiter.api.Test;

class FieldmarkDateExtractorTest {

  private static final String DATE = "2024-12-14";

  @Test
  void testExtractFieldValueVanilla() {
    FieldmarkDateExtractor underTest = new FieldmarkDateExtractor(DATE);

    assertEquals(DATE, underTest.getFieldValue());
    assertEquals(DATE, underTest.extractStringFieldValue());
    assertEquals(String.class, underTest.getFieldType());
    assertEquals("String", underTest.getSimpleTypeName());

    assertEquals(DATE, underTest.extractStringFieldValue());
    assertFalse(underTest.isDoiIdentifier());
  }

  @Test
  void testExtractFieldThrowException() {
    FieldmarkExtractorParseException thrown = assertThrows(
        FieldmarkExtractorParseException.class,
        () -> new FieldmarkDateExtractor(DATE + " 12:04:33"),
        "FieldmarkDateExtractor did not throw the exception, but it was needed"
    );
    assertTrue(
        thrown.getMessage().contains("Date \"" + DATE + " 12:04:33\" cannot be ingested "
            + "since it is not in the format " + DATE_PATTERN));

  }

  @Test
  void testExtractFieldValueWhenNull() {
    FieldmarkDateExtractor underTest = new FieldmarkDateExtractor(null);

    assertNull(underTest.getFieldValue());
    assertEquals("", underTest.extractStringFieldValue());
  }


}