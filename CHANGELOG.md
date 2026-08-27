# Changelog
All notable changes to this project will be documented in this file.

## [4.1.0]
- Adapting client to the Fieldmark/FAIMS3 release `1.6.2` (RSDEV-1308): the notebook endpoints
  moved to the "projects DB v4" document shape (`_id`, top-level `description`, nested
  `uiSpecification`). The client maps both API generations onto the existing model, keeping the
  getters and the serialized JSON key set unchanged for consumers. Known value-level degradations
  on 1.6.2 responses: `listing_id` has no equivalent and is null; `Age` and `Size` are read from
  the design's `metadata.custom` bag when present; `project_status` never reaches `metadata.custom`
  (upstream migrates the legacy key instead), so it maps to the document's top-level `status`;
  `ispublic` and `isrequest` are dropped outright by upstream and always come out `false` (never
  null, since the rspace-web import calls `toString()` on them), so the import dialog's "Public"
  column reads false even for genuinely public notebooks; the notebook LIST response carries no
  design metadata, so `project_lead`, `lead_institution`, `notebook_version`, `schema_version` and
  `showQRCodeButton` are null there (they are populated on the single-notebook GET, which is what
  the import flow uses).
- Heads-up for operators: the `/notebooks/{id}/records/{viewID}.csv` and `.zip` routes behind
  `getNotebookCsv`/`getNotebookFiles` are deprecated at 1.6.2 and now answer a 302 redirect to a
  signed download URL. The client keeps working because its `RestTemplate` follows redirects, but
  the documented replacement is `/notebooks/{id}/records/export`; the client should migrate before
  upstream retires the compatibility routes.

## [4.0.0]
- Spring 6 / Hibernate 6 / Jakarta migration (RSDEV-444)
- Upgrade to rspace-parent 3.0.0

## [3.2.0]
- Adapting client to the new Fieldmark release `1.4.2`

## [3.1.0]
- switch parent pom from rspace-os-parent to rspace-parent (updates/changes a lot of dependencies)

## [3.0.0]
- Changing domain model to be inline with the new Fieldmark release rolled out on 25/07/2025

## [2.0.0]
- Extended domain model to import IGSN DOI Identifiers while importing from Fieldmark

## [1.0.0]
- First version of the Fieldmark java client supporting request to get notebooks, records, CSV and ZIP file


The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
