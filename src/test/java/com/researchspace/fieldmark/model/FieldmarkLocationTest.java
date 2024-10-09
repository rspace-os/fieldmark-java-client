package com.researchspace.fieldmark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Test;

class FieldmarkLocationTest {

  @Test
  void testGetLatitudeAndLongitudeCorrectly(){
    FieldmarkLocation underTest = new FieldmarkLocation(
        new FieldmarkLocationGeometry(List.of(-3D, 5D)));

    assertEquals(-3D, underTest.getLatitude());
    assertEquals(5D, underTest.getLongitude());
  }

  @Test
  void testGetLatitudeAndLongitudeEmpty(){
    FieldmarkLocation underTest = new FieldmarkLocation(
        new FieldmarkLocationGeometry(List.of()));

    assertNull(underTest.getLatitude());
    assertNull(underTest.getLongitude());
  }

}