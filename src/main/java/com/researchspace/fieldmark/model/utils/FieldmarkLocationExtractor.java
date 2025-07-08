package com.researchspace.fieldmark.model.utils;

import com.researchspace.fieldmark.model.FieldmarkLocation;
import java.util.List;
import java.util.Map;
import lombok.Setter;

public class FieldmarkLocationExtractor extends FieldmarkTypeExtractor<FieldmarkLocation> {

  @Setter
  private String latitudeStringValue;

  @Setter
  private String longitudeStringValue;

  public FieldmarkLocationExtractor(Object fieldValue) {
    super(fieldValue, FieldmarkLocation.class);
    if (fieldValue != null) {
      FieldmarkLocation locationValue = null;
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
      this.fieldValue = locationValue;
    }
  }

  @Override
  public String extractStringFieldValue() {
    throw new UnsupportedOperationException(
        "FieldmarkLocationExtractor does not have a single field: "
            + "use extractLatitudeStringValue() or extractLongitudeStringValue()");
  }

  public String getLatitudeStringValue() {
    if (this.latitudeStringValue == null) {
      FieldmarkLocation value = this.getFieldValue();
      this.latitudeStringValue = value != null ? value.getLatitude().toString() : "";
    }
    return this.latitudeStringValue;
  }

  public String getLongitudeStringValue() {
    if (this.longitudeStringValue == null) {
      FieldmarkLocation value = this.getFieldValue();
      this.longitudeStringValue = value != null ? value.getLongitude().toString() : "";
    }
    return this.longitudeStringValue;
  }

}
