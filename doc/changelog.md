# Change Log

## 2026-10-11 – ADR-0005: approval

- What: Set ADR-0005 to `accepted`; the review findings are addressed (the Maven version 3.6.3 stands in
  `## Decision`, the location reference is struck on instruction), and the display locations follow.
- Result: The decision is binding; no open item remains, and the one form note ("missing location") is
  the responsible person's decision.
- Evidence: `doc/status.md` names `accepted`; `grep -n 'Maven 3.6.3' README.md
  doc/decisions/ADR-0005-maven-build.md` finds both locations.
- Files: doc/decisions/ADR-0005-maven-build.md, doc/status.md, README.md, doc/changelog.md.

## 2026-10-11 – ADR-0005: Maven build adopted as a proposal

- What: Adopted the copied `ADR-0002-maven-build.md` as ADR-0005 (status `proposed`) and adapted it to
  this repository: the root aggregator, the eleven modules, their packagings, the BOM `exlp-bom`, and the
  required Maven version 3.6.3 replace the references of the source project.
- Result: The decision repository and the index name ADR-0005 in English; no foreign module, BOM, or
  parent reference remains, and the numbering follows ADR-0001 to ADR-0004.
- Evidence: `grep -rn 'bom-parent8\|geojsf\|jsf\|entities' doc/decisions/ADR-0005-maven-build.md` finds no
  match; `doc/status.md` names `proposed`.
- Files: doc/decisions/ADR-0005-maven-build.md, doc/decisions/ADR-0002-maven-build.md (removed),
  README.md, doc/status.md, doc/changelog.md.

## 2026-10-11 – ADR-0004: test module runs XML test programs

- What: `AbstractXmlTest` in `test/src/main/java` received a `@Test` method that compares the marshalled
  model against the reference XML under `test/src/test/resources`, `AbstractExlpXmlTest` became abstract,
  and `test/pom.xml` binds `junit-jupiter-api` in the provided scope and adds the JAXB API and Runtime in
  the test scope.
- Result: The `test` module runs its XML test programs on JUnit 5; ADR-0004 names the module, the
  inherited test method, and the JAXB dependency.
- Evidence: `mvn -o -pl test clean verify` `[SUCCESS]`, 12 tests, 0 failures.
- Files: test/pom.xml, test/src/main/java/org/exlp/test/AbstractXmlTest.java,
  test/src/test/java/org/exlp/test/AbstractExlpXmlTest.java, doc/decisions/ADR-0004-test-strategy.md,
  doc/changelog.md.

## 2026-10-11 – ADR-0004: review corrections and acceptance

- What: Named the location of the open question in `## Context`, the bound JUnit version and the JDK 11
  prerequisite in `## Decision`, and the affected requirements and display locations in `## Impact`;
  corrected the `test` module in the evidence and wrapped the file to the house width. Set the decision
  to `accepted`.
- Result: The two content findings of the review (missing location, missing requirement IDs) are
  addressed, and the decision is binding.
- Evidence: `grep -n 'FR-00' doc/decisions/ADR-0004-test-strategy.md` finds the three requirements;
  the file is within the house line width; `doc/status.md` names `accepted`.
- Files: doc/decisions/ADR-0004-test-strategy.md, doc/status.md, README.md, doc/changelog.md.

## 2026-10-11 – Build: surefire overrides and JUnit 4 removed

- What: Removed the `maven-surefire-plugin` 2.22.2 overrides in `util` and `test`; every module now
  inherits 3.6.0 from the root POM. Removed the managed `junit:junit` 4.12 entry from the BOM.
- Result: The repository carries one test framework (JUnit 5); no module binds or references JUnit 4, and
  the README and the architecture file name JUnit 5.
- Evidence: `mvn -o -DskipTests=false verify` `[SUCCESS]` under JDK 11 (util 82, maven 23, shell 6 tests,
  all runs on `surefire:3.6.0`); `grep -rn --include='*.java' 'org.junit.'` finds only `org.junit.jupiter`.
- Files: util/pom.xml, test/pom.xml, bom/pom.xml.xml,
  README.md, doc/requirements/architecture.md, doc/decisions/ADR-0004-test-strategy.md, doc/changelog.md.

## 2026-10-11 – ADR-0004: test strategy recorded as a proposal

- What: Adopted the test strategy as ADR-0004 (status `proposed`) and adapted the copied draft to this
  repository; the JUnit 5 binding, the modules with test programs, the surefire versions, and the still
  managed JUnit 4 entry are named.
- Result: The decision repository and the index name ADR-0004; the copied `ADR-0003-test-strategy.md` is
  replaced by the renumbered and adapted file.
- Evidence: `grep -rl '@Test' */src` finds test programs in `util`, `maven`, and `shell`;
  `grep -rn 'org.junit.jupiter' */pom.xml` finds the Jupiter binding in `bom`, `util`, `maven`, `shell`, and
  `test`.
- Files: doc/decisions/ADR-0004-test-strategy.md, doc/decisions/ADR-0003-test-strategy.md (removed),
  doc/status.md, doc/changelog.md.

## 2026-10-10 – FR-003: central configuration pointer recorded

- What: Recorded `ExlpCentralConfigPointer` as the FR-003 feature of the library.
- Result: FR-003 has no open questions.
- Evidence: `mvn -o -pl util -am clean test` `[SUCCESS]`; `TestExlpCentralConfigPointer` runs 11 tests.
- Files: doc/requirements/functional/FR-003-central-configuration-pointer.md, doc/status.md,
  doc/changelog.md.

## 2026-10-09 – Build: the `addon` module is removed

- What: The directory `addon` is deleted (`addon/pom.xml`, its ignore file, and seven test resources); the
  module had left the reactor on 2022-05-01 (`dc1464f`, the module entry was commented out there and the
  comment was removed on 2022-11-02 in `b1bdfd9`) and still hung on the parent line 0.1.15, so no build ran
  it.
- Result: The orphaned module is gone, and with it the only consumer that named `exlp-xml` without a
  classifier (the open point of ADR-0002); the aggregator, the layer chain, and the published artifacts stay
  unchanged.
- Evidence: `git status --short` lists nine deleted files under `addon/`; `grep -n addon pom.xml` finds no
  module entry; no artifact `exlp-addon` is in the local repository.
- Files: addon/ (removed), doc/changelog.md.

## 2026-10-09 – XML: classifier jars before the test phase

- What: The two jar executions of the `xml` module run at phase `process-classes` (after the goal of
  FR-001) instead of `package`.
- Result: The classifier jars exist before the `test` phase, so a plain `mvn test` on the repository
  resolves the variant artifact from the reactor and `util` compiles; the two jars, their class-file
  versions, and their reachability metadata are unchanged (ADR-0002).
- Evidence: `mvn -o clean test` `[SUCCESS]` (all twelve modules, 83 tests in `util`); `mvn -o clean
  install` `[SUCCESS]`; `exlp-xml-<version>-javax.jar` carries class-file version 52 and the metadata,
  `-jakarta.jar` version 55 and the metadata.
- Files: xml/pom.xml, doc/decisions/ADR-0002-package-javax-jakarta-variants.md, doc/changelog.md.

## 2026-10-09 – FR-002: review, correction, approval, implementation

- What: Reviewed FR-002, applied the corrections, approved it, and implemented it with an adapter per
  variant.
- Result: All findings addressed; the mapper is adapted to the RI type and a rejected mapper logs the
  warning; status implemented.
- Evidence: `mvn -o -pl util test` passes with 59 tests, four per variant.
- Files: util/pom.xml, util/src, README.md, doc/requirements/functional/FR-002-namespace-prefixes.md,
  doc/status.md, doc/changelog.md.

## 2026-10-08 – Build: every module compiles against the Java 8 API

- What: The root POM configures `maven-compiler-plugin` with `release` 8 instead of `source` and
  `target` 8.
- Result: A build on JDK 11 rejects types and members that Java 8 does not provide; the artifacts keep
  class-file version 52 (ADR-0003).
- Evidence: `mvn -Pram -DskipTests -Djava.awt.headless=true clean install` `[SUCCESS]`; a class that
  uses `List.of` fails with `cannot find symbol`; the installed jars carry class-file version 52.
- Files: pom.xml, README.md, doc/decisions/ADR-0003-java-compile-level.md, doc/status.md,
  doc/changelog.md.

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
