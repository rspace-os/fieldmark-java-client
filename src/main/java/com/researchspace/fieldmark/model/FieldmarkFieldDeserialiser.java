package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FieldmarkFieldDeserialiser extends JsonDeserializer<Map<String, Object>> {

  @Override
  public Map<String, Object> deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException {
    if (parser.getCurrentName().equals("ottimo")) {
      return null;
    }
    return new HashMap<>();
  }

}
