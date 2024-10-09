package com.researchspace.fieldmark.model.utils;

import com.researchspace.fieldmark.model.FieldmarkLocation;
import java.util.List;
import java.util.Map;

public class FieldmarkLocationExtractor extends
    FieldmarkTypeExtractor<FieldmarkLocation> {

  public FieldmarkLocationExtractor(Object fieldValue) {
    super(fieldValue);
  }

  public FieldmarkLocation extractFieldValue() {
    Map dataRaw = (Map) this.fieldValue;
    com.researchspace.fieldmark.model.FieldmarkLocation resulObject = new FieldmarkLocation();
    List<Double> coordinates = ((List) ((Map) dataRaw.get("geometry")).get("coordinates"));
    resulObject.setLatitude(coordinates.get(0));
    resulObject.setLongitude(coordinates.get(1));
    return resulObject;
  }

}
