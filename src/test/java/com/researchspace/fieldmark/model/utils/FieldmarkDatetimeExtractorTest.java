package com.researchspace.fieldmark.model.utils;

import static com.researchspace.fieldmark.model.utils.FieldmarkDatetimeExtractor.DATE_TIME_PATTERN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.researchspace.fieldmark.model.exception.FieldmarkExtractorParseException;
import org.junit.jupiter.api.Test;

class FieldmarkDatetimeExtractorTest {

  private static final String DATE = "2024-12-14";
  private static final String TIME = "12:05:30";
  private static final String DATE_TIME = DATE + " " + TIME;

  @Test
  void testExtractFieldValueVanilla() {
    FieldmarkDatetimeExtractor underTest = new FieldmarkDatetimeExtractor(DATE_TIME);

    assertEquals(DATE_TIME, underTest.getFieldValue());
    assertEquals(DATE_TIME, underTest.extractStringFieldValue());
    assertEquals(String.class, underTest.getFieldType());
    assertEquals("String", underTest.getSimpleTypeName());

    assertEquals(DATE, underTest.extractDateStringValue());
    assertEquals(TIME, underTest.extractTimeStringValue());
  }

  @Test
  void testExtractFieldThrowException() {
    FieldmarkExtractorParseException thrown = assertThrows(
        FieldmarkExtractorParseException.class,
        () -> new FieldmarkDatetimeExtractor(DATE_TIME + ":123456"),
        "FieldmarkDatetimeExtractor did not throw the exception, but it was needed"
    );
    assertTrue(
        thrown.getMessage().contains("DateTime \"" + DATE_TIME + ":123456\" cannot be ingested "
            + "since it is not in the format " + DATE_TIME_PATTERN));

  }

  @Test
  void testExtractFieldValueWhenNull() {
    FieldmarkDatetimeExtractor underTest = new FieldmarkDatetimeExtractor(null);

    assertNull(underTest.getFieldValue());
    assertEquals("", underTest.extractStringFieldValue());
    assertEquals("", underTest.extractDateStringValue());
    assertEquals("", underTest.extractTimeStringValue());
  }


}