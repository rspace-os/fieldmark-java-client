package com.researchspace.fieldmark.model.utils;

import com.researchspace.fieldmark.model.FieldmarkLocation;
import lombok.Setter;

public class FieldmarkLocationExtractor extends FieldmarkTypeExtractor<FieldmarkLocation> {

  @Setter
  private String latitudeStringValue;

  @Setter
  private String longitudeStringValue;

  public FieldmarkLocationExtractor(FieldmarkLocation fieldValue) {
    super(fieldValue, FieldmarkLocation.class);
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
