package com.researchspace.fieldmark.model.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.researchspace.fieldmark.model.FieldmarkDoiIdentifier;
import org.junit.jupiter.api.Test;


class FieldmarkDoiIdentifierExtractorTest {

  private static final String DOI_IDENTIFIER = "10.12133/asdf-tres";

  @Test
  void testExtractFieldValueVanilla() {
    FieldmarkDoiIdentifierExtractor underTest = new FieldmarkDoiIdentifierExtractor(DOI_IDENTIFIER);

    assertEquals(new FieldmarkDoiIdentifier(DOI_IDENTIFIER), underTest.getFieldValue());
    assertEquals(FieldmarkDoiIdentifier.class, underTest.getFieldType());
    assertEquals("FieldmarkDoiIdentifier", underTest.getSimpleTypeName());
    assertTrue(underTest.isDoiIdentifier());
  }

  @Test
  void testExtractFieldValueWhenNull() {
    FieldmarkDoiIdentifierExtractor underTest = new FieldmarkDoiIdentifierExtractor(null);

    assertNull(underTest.getFieldValue());
    assertTrue(underTest.isDoiIdentifier());
  }

}