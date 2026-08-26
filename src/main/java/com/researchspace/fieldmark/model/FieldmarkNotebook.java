package com.researchspace.fieldmark.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Fieldmark notebook, deserializable from both API generations: the pre-1.6.2 shape (flat
 * {@code metadata} and {@code ui-specification} keys) and the Fieldmark/FAIMS3 1.6.2 shape
 * (projects DB v4: {@code _id}, top-level {@code description}, nested {@code uiSpecification}).
 * The pre-1.6.2 getters and serialized JSON key set are preserved either way, so rspace-web and
 * its frontend contract are unaffected by the upstream restructure. The v1.6.2-only inputs are
 * write-only with no public accessors. Getters are pure: reading (or serializing) a notebook
 * never mutates it.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldmarkNotebook {

  private String name;
  private String status;

  @JsonAlias("_id")
  private String id;

  @JsonProperty("project_id")
  private String projectId;

  @JsonProperty("listing_id")
  private String listingId;

  /** v1.6.2 top-level description; feeds the synthesized metadata, never serialized. */
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String description;

  /** v1.6.2 nested notebook design; mapped back onto the pre-1.6.2 getters, never serialized. */
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @JsonProperty(value = "uiSpecification", access = Access.WRITE_ONLY)
  private FieldmarkNotebookDefinition definition;

  private FieldmarkNotebookMetadata metadata;

  @JsonProperty("ui-specification")
  private FieldmarkUiSpecification uiSpecification;

  public String getProjectId() {
    return projectId != null ? projectId : id;
  }

  public FieldmarkUiSpecification getUiSpecification() {
    if (uiSpecification == null && definition != null && definition.getUiSpec() != null) {
      FieldmarkUiSpecification fromDefinition = new FieldmarkUiSpecification();
      fromDefinition.setFields(definition.getUiSpec().getFields());
      return fromDefinition;
    }
    return uiSpecification;
  }

  /**
   * The pre-1.6.2 API returned a {@code metadata} object; the 1.6.2 API spread those values over
   * the document. When the old key is absent, synthesize an equivalent metadata object so
   * consumers (and the JSON rspace-web serves to its frontend) see the same names as before.
   */
  public FieldmarkNotebookMetadata getMetadata() {
    if (metadata == null && (id != null || definition != null)) {
      return synthesizeMetadata();
    }
    return metadata;
  }

  private FieldmarkNotebookMetadata synthesizeMetadata() {
    FieldmarkNotebookMetadata result = new FieldmarkNotebookMetadata();
    result.setProjectId(id);
    result.setName(name);
    result.setPreDescription(description);
    if (definition == null) {
      return result;
    }
    FieldmarkNotebookDefinition.Information information =
        definition.getMetadata() == null ? null : definition.getMetadata().getInformation();
    if (information != null) {
      result.setNotebookVersion(information.getNotebookVersion());
      result.setProjectLead(information.getProjectLeadLabel());
      result.setLeadInstitution(information.getLeadInstitution());
      if (information.getPurposeMarkdown() != null) {
        // purposeMarkdown is the renamed pre_description, so it wins over description
        result.setPreDescription(information.getPurposeMarkdown());
      }
    }
    Map<String, Object> custom =
        definition.getMetadata() == null ? null : definition.getMetadata().getCustom();
    if (custom != null) {
      // arbitrary pre-1.6.2 metadata keys live on in the design's custom bag
      result.setAge(Objects.toString(custom.get("Age"), null));
      result.setSize(Objects.toString(custom.get("Size"), null));
      result.setProjectStatus(Objects.toString(custom.get("project_status"), null));
    }
    FieldmarkNotebookDefinition.UiSpec uiSpec = definition.getUiSpec();
    if (uiSpec != null) {
      result.setSchemaVersion(uiSpec.getSchemaVersion());
      if (uiSpec.getSettings() != null && uiSpec.getSettings().getShowQrCodeButton() != null) {
        result.setShowQRCodeButton(String.valueOf(uiSpec.getSettings().getShowQrCodeButton()));
      }
    }
    return result;
  }
}
