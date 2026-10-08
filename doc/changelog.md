# Change Log

## 2026-10-08 – XML: javax and jakarta each with their own output directory

- What: `javax` and `jakarta` compile into `target/classes-javax` and `target/classes-jakarta`; the goal
  of FR-001 runs once per variant and writes the metadata into that variant's directory; the copy step is
  gone. The goal takes its output directory from the configuration, and a run scans the compile classpath
  plus that directory.
- Result: Neither variant uses the module output directory, and no copy step exists (ADR-0002).
- Evidence: 23 tests pass; `mvn -pl xml -DskipTests -Djava.awt.headless=true clean install` `[SUCCESS]`;
  each variant directory holds 40 classes and its own metadata, both jars carry the file.
- Files: xml/pom.xml, maven/src/main/java/org/exlp/maven/goal/ReachabilityMetadataGoal.java,
  maven/src/main/java/org/exlp/maven/reachability/ReachabilityMetadataGenerator.java,
  maven/src/test/java/org/exlp/maven/reachability/TestReachabilityMetadataGenerator.java, README.md,
  doc/requirements/functional/FR-001-reachability-metadata.md,
  doc/decisions/ADR-0002-package-javax-jakarta-variants.md, doc/changelog.md.

## 2026-10-08 – XML: both JAXB variants from one build, published as classifier artifacts

- What: Rebuilt `xml` with POM packaging; two compile executions and two jar executions produce the
  `javax` and the `jakarta` artifact in one Maven run; the profiles no longer select a variant, and the
  profile `autojavax` is gone.
- Result: `mvn clean install` publishes `exlp-xml-<version>.pom` together with `-javax.jar` and
  `-jakarta.jar`, and no artifact without a classifier (ADR-0002).
- Evidence: `mvn -pl xml -DskipTests -Djava.awt.headless=true clean install` `[SUCCESS]`; the two jars
  carry class-file versions 52 and 55, the matching annotation packages, and the reachability metadata;
  a consumer with either classifier compiles and runs.
- Files: xml/pom.xml, README.md, doc/decisions/ADR-0002-package-javax-jakarta-variants.md,
  doc/status.md, doc/changelog.md.

## 2026-10-08 – FR-001: one configuration form for the annotation types

- What: Reduced the configuration to a list of annotation types; a selected class registers all
  declared constructors, fields, and methods.
- Result: The criteria AC-FR-001-04 and AC-FR-001-06 are merged; the following criteria are
  renumbered.
- Evidence: 22 tests pass; `mvn -o -pl xml process-classes` selects 25 classes of the module.
- Files: maven/src, xml/pom.xml, README.md,
  doc/requirements/functional/FR-001-reachability-metadata.md, doc/changelog.md.

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
