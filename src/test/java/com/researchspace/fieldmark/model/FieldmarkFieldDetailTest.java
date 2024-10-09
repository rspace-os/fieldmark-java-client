package com.researchspace.fieldmark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FieldmarkFieldDetailTest {

  @Test
  void testGetFieldCorrect(){
    FieldmarkFieldDetail underTest = new FieldmarkFieldDetail();
    underTest.setFieldType("something::Type");

    assertEquals("Type", underTest.getFieldType());
  }

  @Test
  void testGetFieldEmpty(){
    FieldmarkFieldDetail underTest = new FieldmarkFieldDetail();
    underTest.setFieldType("");

    assertEquals("", underTest.getFieldType());
  }

  @Test
  void testGetFieldNotCorrectlyFormatted(){
    FieldmarkFieldDetail underTest = new FieldmarkFieldDetail();
    underTest.setFieldType("Something::");

    assertEquals("", underTest.getFieldType());
  }



}