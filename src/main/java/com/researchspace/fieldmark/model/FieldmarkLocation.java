package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldmarkLocation {

  private FieldmarkLocationGeometry geometry;

  private Double latitude;
  private Double longitude;


  public FieldmarkLocation(FieldmarkLocationGeometry geometry){
    this.geometry = geometry;
  }

  public Double getLatitude() {
    if (latitude == null && geometry.getCoordinates().size() == 2) {
      latitude = geometry.getCoordinates().get(0);
    }
    return latitude;
  }

  public Double getLongitude() {
    if (this.longitude == null && geometry.getCoordinates().size() == 2) {
      this.longitude = this.geometry.getCoordinates().get(1);
    }
    return longitude;
  }


}
