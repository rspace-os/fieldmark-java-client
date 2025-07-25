package com.researchspace.fieldmark.model.utils.factory;

import static com.researchspace.fieldmark.model.utils.factory.FieldmarkTypeExtractorFactory.getTypeExtractorInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.researchspace.fieldmark.model.FieldmarkLocation;
import com.researchspace.fieldmark.model.utils.FieldmarkDateExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkDatetimeExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkFileExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkLocationExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkTypeExtractor;
import org.junit.jupiter.api.Test;

class FieldmarkTypeExtractorFactoryTest {

  @Test
  void testFactoryReturnsTheRightInstance() {
    FieldmarkTypeExtractor extractor = null;

    extractor = getTypeExtractorInstance(null, "Files", false);
    assertTrue(FieldmarkFileExtractor.class.isAssignableFrom(extractor.getClass()));
    assertEquals(byte[].class, extractor.getFieldType());

    extractor = getTypeExtractorInstance(null, "Bool", false);
    assertEquals(Boolean.class, extractor.getFieldType());

    extractor = getTypeExtractorInstance(null, "Date", false);
    assertTrue(FieldmarkDateExtractor.class.isAssignableFrom(extractor.getClass()));
    assertEquals(String.class, extractor.getFieldType());

    extractor = getTypeExtractorInstance(null, "Datetime", false);
    assertTrue(FieldmarkDatetimeExtractor.class.isAssignableFrom(extractor.getClass()));
    assertEquals(String.class, extractor.getFieldType());

    extractor = getTypeExtractorInstance(null, "Email", false);
    assertEquals(String.class, extractor.getFieldType());

    extractor = getTypeExtractorInstance(null, "Integer", false);
    assertEquals(Integer.class, extractor.getFieldType());

    extractor = getTypeExtractorInstance(null, "JSON", false);
    assertEquals(String.class, extractor.getFieldType());

    extractor = getTypeExtractorInstance(null, "String", false);
    assertEquals(String.class, extractor.getFieldType());

    extractor = getTypeExtractorInstance(null, "Location", false);
    assertTrue(FieldmarkLocationExtractor.class.isAssignableFrom(extractor.getClass()));
    assertEquals(FieldmarkLocation.class, extractor.getFieldType());
  }

  @Test
  void testFactoryThrowsExceptionForOtherTypes() {

    UnsupportedOperationException thrown = assertThrows(
        UnsupportedOperationException.class,
        () -> getTypeExtractorInstance(null, "Array", false),
        "FieldmarkTypeExtractorFactory didnt throw the exception, but it was needed"
    );
    assertTrue(
        thrown.getMessage()
            .contains("The type \"Array\" is not yet supported while importing to RSpace"));

    thrown = assertThrows(
        UnsupportedOperationException.class,
        () -> getTypeExtractorInstance(null, "Relationship", false),
        "FieldmarkTypeExtractorFactory didnt throw the exception, but it was needed"
    );
    assertTrue(
        thrown.getMessage()
            .contains("The type \"Relationship\" is not yet supported while importing to RSpace"));

    thrown = assertThrows(
        UnsupportedOperationException.class,
        () -> getTypeExtractorInstance(null, "AnyOtherType", false),
        "FieldmarkTypeExtractorFactory didnt throw the exception, but it was needed"
    );
    assertTrue(
        thrown.getMessage()
            .contains("The type \"AnyOtherType\" is not yet supported while importing to RSpace"));

  }

}