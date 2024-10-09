package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldmarkLocation {

  private FieldmarkLocationGeometry geometry;

  private Double latitude;
  private Double longitude;

  public Double getLatitude() {
    if (latitude == null) {
      latitude = geometry.getCoordinates().get(0);
    }
    return latitude;
  }

  public Double getLongitude() {
    if (this.longitude == null) {
      this.longitude = this.geometry.getCoordinates().get(1);
    }
    return longitude;
  }
}
