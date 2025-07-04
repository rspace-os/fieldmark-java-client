package com.researchspace.fieldmark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class FieldmarkDoiIdentifierTest {

  private static final String DOI_IDENTIFIER = "10.12133/asdf-tres";

  @Test
  void testValueNotEmpty(){
    FieldmarkDoiIdentifier underTest = new FieldmarkDoiIdentifier(DOI_IDENTIFIER);
    assertEquals(DOI_IDENTIFIER, underTest.getDoiIdentifier());
  }

  @Test
  void testGetLatitudeAndLongitudeEmpty(){
    FieldmarkDoiIdentifier underTest = new FieldmarkDoiIdentifier(null);
    assertNull(underTest.getDoiIdentifier());
  }

}