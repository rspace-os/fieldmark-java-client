package com.researchspace.fieldmark.model.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.researchspace.fieldmark.model.FieldmarkLocation;
import com.researchspace.fieldmark.model.FieldmarkLocationGeometry;
import java.util.List;
import org.junit.jupiter.api.Test;

class FieldmarkLocationExtractorTest {

  @Test
  void testExtractFieldValueVanilla() {
    FieldmarkLocation locationValue = new FieldmarkLocation(
        new FieldmarkLocationGeometry(List.of(-3D, 5D)));
    FieldmarkLocationExtractor underTest = new FieldmarkLocationExtractor(locationValue);

    assertEquals(locationValue, underTest.getFieldValue());
    assertEquals(FieldmarkLocation.class, underTest.getFieldType());
    assertEquals("FieldmarkLocation", underTest.getSimpleTypeName());
    assertEquals("-3.0", underTest.getLatitudeStringValue());
    assertEquals("5.0", underTest.getLongitudeStringValue());
    assertFalse(underTest.isDoiIdentifier());
  }

  @Test
  void testExtractFieldStringValueThrowException() {
    FieldmarkLocation locationValue = new FieldmarkLocation(
        new FieldmarkLocationGeometry(List.of(-3D, 5D)));
    FieldmarkLocationExtractor underTest = new FieldmarkLocationExtractor(locationValue);

    UnsupportedOperationException thrown = assertThrows(
        UnsupportedOperationException.class,
        () -> underTest.extractStringFieldValue(),
        "FieldmarkLocationExtractor did not throw the exception, but it was needed"
    );
    assertTrue(
        thrown.getMessage().contains("FieldmarkLocationExtractor does not have a single field"));

  }

  @Test
  void testExtractFieldValueWhenNull() {
    FieldmarkLocationExtractor underTest = new FieldmarkLocationExtractor(null);

    assertNull(underTest.getFieldValue());
    assertEquals("", underTest.getLatitudeStringValue());
    assertEquals("", underTest.getLongitudeStringValue());
  }


}