package com.researchspace.fieldmark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class FieldmarkRecordTest {

  @Test
  void testGetFieldCorrectly() {
    FieldmarkRecord underTest = new FieldmarkRecord();
    underTest.setFieldTypes(Map.of(
        "fieldName1", "something::fieldType1",
        "fieldName2", "something::fieldType2")
    );

    assertTrue(underTest.getFieldType("fieldName2").isPresent());
    assertEquals("fieldType2", underTest.getFieldType("fieldName2").get());
  }

  @Test
  void testGetFieldWhenTotallyEmpty() {
    FieldmarkRecord underTest = new FieldmarkRecord();

    assertTrue(underTest.getFieldType("fieldName").isEmpty());
  }

  @Test
  void testGetFieldWhenFieldNameDoesNotExist() {
    FieldmarkRecord underTest = new FieldmarkRecord();
    underTest.setFieldTypes(Map.of("fieldName1", "something::fieldType1"));

    assertTrue(underTest.getFieldType("fieldName2").isEmpty());
  }

  @Test
  void testGetFieldWhenFieldTypesAreNotCorrectlyFormatted() {
    FieldmarkRecord underTest = new FieldmarkRecord();

    underTest.setFieldTypes(Map.of("fieldName1", "something::"));
    assertTrue(underTest.getFieldType("fieldName2").isEmpty());

    underTest.setFieldTypes(Map.of("fieldName1", "something:fieldType1"));
    assertTrue(underTest.getFieldType("fieldName2").isEmpty());
  }

}