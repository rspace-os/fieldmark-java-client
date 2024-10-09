package com.researchspace.fieldmark.model.utils;

import java.util.Locale;
import org.apache.commons.lang.StringUtils;

public class FieldmarkTypeExtractorFactory {

  private FieldmarkTypeExtractorFactory() {
  }

  public static FieldmarkTypeExtractor getTypeExtractor(Object fieldValue, String fieldmarkType) {
    if (StringUtils.isNotBlank(fieldmarkType)) {
      switch (fieldmarkType.toUpperCase(Locale.ROOT)) {
        case "STRING":
        case "EMAIL":
        case "JSON":
          return new FieldmarkTypeExtractor<String>(fieldValue);
        case "INTEGER":
          return new FieldmarkTypeExtractor<Integer>(fieldValue);
        case "FILES":
          return new FieldmarkTypeExtractor<String>(fieldValue); // get the path
        case "LOCATION":
          return new FieldmarkLocationExtractor(fieldValue);
        case "BOOL":
          return new FieldmarkTypeExtractor<Boolean>(fieldValue);
        case "DATE":
          return new FieldmarkDateExtractor(fieldValue);
        case "DATETIME":
          return new FieldmarkDatetimeExtractor(fieldValue);
        default:
//        case "ARRAY":
//        case "RELATIONSHIP":
          throw new UnsupportedOperationException(
              "The type \"" + fieldmarkType + "\" is not yet supported while importing to RSpace");
      }
    }
    return null;
  }

}
