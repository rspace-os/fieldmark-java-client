package com.researchspace.fieldmark.model.utils.factory;

import com.researchspace.fieldmark.model.utils.FieldmarkDateExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkDatetimeExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkDoiIdentifierExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkFileExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkLocationExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkTypeExtractor;
import java.util.Locale;

public class FieldmarkTypeExtractorFactory {

  private FieldmarkTypeExtractorFactory() {
  }

  public static FieldmarkTypeExtractor getTypeExtractorInstance(Object fieldValue,
      String fieldmarkType, boolean isIdentifier) {
    switch (fieldmarkType.toUpperCase(Locale.ROOT)) {
      case "EMAIL":
      case "JSON":
        return new FieldmarkTypeExtractor<>(fieldValue, String.class);
      case "STRING":
        if (isIdentifier) {
          return new FieldmarkDoiIdentifierExtractor(fieldValue);
        } else {
          return new FieldmarkTypeExtractor<>(fieldValue, String.class);
        }
      case "INTEGER":
        return new FieldmarkTypeExtractor<>(fieldValue, Integer.class);
      case "FILES":
        return new FieldmarkFileExtractor(); // value is null because the actual value is taken from the ZIP file
      case "LOCATION":
        return new FieldmarkLocationExtractor(fieldValue);
      case "BOOL":
        return new FieldmarkTypeExtractor<>(fieldValue, Boolean.class);
      case "DATE":
        return new FieldmarkDateExtractor(fieldValue);
      case "DATETIME":
        return new FieldmarkDatetimeExtractor(fieldValue);
      default:
        throw new UnsupportedOperationException(
            "The type \"" + fieldmarkType
                + "\" is not yet supported while importing to RSpace");
    }
  }
}