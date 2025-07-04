package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldmarkDoiIdentifier {

  @Getter
  private String doiIdentifier;

  public FieldmarkDoiIdentifier(String doiIdentifier) {
    this.doiIdentifier = doiIdentifier;
  }

}
