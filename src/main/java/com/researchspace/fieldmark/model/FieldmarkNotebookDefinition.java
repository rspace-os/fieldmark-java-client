package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code uiSpecification} wrapper introduced by the Fieldmark/FAIMS3 1.6.2 API (projects DB
 * v4): the notebook design and its metadata now live nested on the project document. Used for
 * deserialization only; {@link FieldmarkNotebook} maps it back onto the pre-1.6.2 field names and
 * never serializes it, so nothing here can leak into the JSON rspace-web serves.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldmarkNotebookDefinition {

  private UiSpec uiSpec;
  private DesignMetadata metadata;

  @Data
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class UiSpec {

    private Map<String, FieldmarkFieldDetail> fields;
    private String schemaVersion;
    private Settings settings;
  }

  @Data
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Settings {

    private Boolean showQrCodeButton;
  }

  @Data
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class DesignMetadata {

    private Information information;

    /** Free key/value bag; carries what were arbitrary metadata keys (e.g. Age, Size) pre-1.6.2. */
    private Map<String, Object> custom;
  }

  @Data
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Information {

    private String notebookVersion;

    /** Formerly {@code pre_description}. */
    private String purposeMarkdown;

    /** Formerly {@code project_lead}. */
    private String projectLeadLabel;

    private String leadInstitution;
  }
}
