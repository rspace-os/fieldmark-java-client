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

  public Double getLatitude(){
    return geometry.getCoordinates().get(0);
  }
  public Double getLongitude(){
    return geometry.getCoordinates().get(1);
  }
}
