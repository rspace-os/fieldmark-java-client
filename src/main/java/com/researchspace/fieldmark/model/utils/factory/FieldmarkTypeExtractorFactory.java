package com.researchspace.fieldmark.model.utils.factory;

import com.researchspace.fieldmark.model.FieldmarkLocation;
import com.researchspace.fieldmark.model.utils.FieldmarkDateExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkDatetimeExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkFileExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkLocationExtractor;
import com.researchspace.fieldmark.model.utils.FieldmarkTypeExtractor;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class FieldmarkTypeExtractorFactory {

  private FieldmarkTypeExtractorFactory() {
  }

  public static FieldmarkTypeExtractor getTypeExtractorInstance(Object fieldValue,
      String fieldmarkType) {
    if (StringUtils.isNotBlank(fieldmarkType)) {
      switch (fieldmarkType.toUpperCase(Locale.ROOT)) {
        case "STRING":
        case "EMAIL":
        case "JSON":
          return new FieldmarkTypeExtractor<>((String) fieldValue, String.class);
        case "INTEGER":
          return new FieldmarkTypeExtractor<>((Integer) fieldValue, Integer.class);
        case "FILES":
          return new FieldmarkFileExtractor(); // value is null because the actual value is taken from the ZIP file
        case "LOCATION":
          FieldmarkLocation locationValue = null;
          if (fieldValue != null) {
            if (!FieldmarkLocation.class.isAssignableFrom(fieldValue.getClass())) {
              Map dataRaw = (Map) fieldValue;
              List<Double> coordinates =
                  ((List) ((Map) dataRaw.get("geometry")).get("coordinates"));
              locationValue = new FieldmarkLocation();
              locationValue.setLatitude(coordinates.get(0));
              locationValue.setLongitude(coordinates.get(1));
            } else {
              locationValue = (FieldmarkLocation) fieldValue;
            }
          }
          return new FieldmarkLocationExtractor(locationValue);
        case "BOOL":
          return new FieldmarkTypeExtractor<>((Boolean) fieldValue, Boolean.class);
        case "DATE":
          return new FieldmarkDateExtractor((String) fieldValue);
        case "DATETIME":
          return new FieldmarkDatetimeExtractor((String) fieldValue);
        default:
          throw new UnsupportedOperationException(
              "The type \"" + fieldmarkType + "\" is not yet supported while importing to RSpace");
      }
    }
    return null;
  }

}
