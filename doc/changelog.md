# Change Log

## 2026-10-08 – XML: reachability metadata in the build

- What: Decoupled the Maven plugin from the layered modules and bound the goal in `xml`.
- Result: `mvn clean install` writes the metadata and the packaging places it in the module JAR.
- Evidence: `mvn clean install -DskipTests` `[SUCCESS]`; 24 tests pass; the JAR contains the file.
- Files: maven/pom.xml, maven/src, xml/pom.xml, README.md, doc/requirements/architecture.md.

## 2026-10-08 – FR-001: implementation of the metadata goal

- What: Implemented FR-001 and ADR-0001; tests use a dependency's annotations.
- Result: The goal resolves the module classpath, scans, and packages the metadata.
- Evidence: 21 tests pass; both invocation modes produce identical registrations; the file
  validates against the metadata schema 1.2.0; tests need Surefire 3.
- Files: bom/pom.xml, maven/pom.xml, maven/src/main/java/org/exlp/maven/, README.md,
  doc/requirements/architecture.md, doc/status.md, doc/changelog.md.

## 2026-10-08 – FR-001: review, correction, approval

- What: Reviewed FR-001, applied the instructed corrections, and set it to approved.
- Result: Review "Approval not recommended" (2 findings, 8 notes); acceptance: all findings
  addressed; corrections in FR-001 and in ADR-0001.
- Evidence: Five patterns without a match; line width and target values reached.
- Files: doc/requirements/functional/FR-001-reachability-metadata.md, doc/status.md,
  doc/decisions/ADR-0001-scan-module-classes-with-classgraph.md, doc/changelog.md.

## 2026-10-08 – ADR-0001: review, correction, acceptance

- What: Reviewed ADR-0001, corrected it on instruction, and set it to accepted.
- Result: Review "Approval not recommended" (1 finding against the recommendation, 9 notes); the
  notes are decided except the FR-001 follow-up; acceptance: all findings addressed.
- Evidence: Line width, body length, and narration patterns without a match; heading date as
  specified.
- Files: doc/decisions/ADR-0001-scan-module-classes-with-classgraph.md, doc/status.md, doc/changelog.md.
