---
id: ADR-0002
title: Build and publish the JAXB variants as classifier artifacts
status: accepted
date: 2026-10-08
affects:
  - FR-001
related:
  - ADR-0001
---

# ADR-0002: Build and publish the JAXB variants as classifier artifacts

## Context

The project overview (`README.md`, `## Build and Start`) describes the build of the repository as Maven
commands, and its platform statement (`README.md`, `## Technical Assumptions`) names the JVM of the
artifacts. The open question is how the `xml` module produces its two JAXB variants and which artifacts
it publishes.

- The generated sources of the two variants are in `xml/src/main/javax` and `xml/src/main/jakarta` and
  declare the **same** fully qualified type names; only the annotation packages differ
  (`javax.xml.bind` and `jakarta.xml.bind`).
- A Maven module compiles one set of source roots into one output directory, so a single run could not
  carry both variants; the previous build selected the variant through the activation of a profile by
  the JDK that ran Maven.
- The module published a jar without a classifier whose content depended on the JDK of the build, and
  each classifier jar was a copy of that jar (`xml/pom.xml`).

## Decision

The `xml` module builds both JAXB variants in one Maven run and publishes exactly two classifier
artifacts.

- The module uses `<packaging>pom</packaging>`: it publishes its POM and the two classifier jars, and no
  artifact without a classifier exists.
- `maven-compiler-plugin` (3.13.0) compiles both variants in one run, each with its own source roots and
  its own output directory; the properties `xml.classes.javax`
  (`${project.build.directory}/classes-javax`) and `xml.classes.jakarta`
  (`${project.build.directory}/classes-jakarta`) name them:
  - `compile-javax` at phase `compile` compiles `src/main/java` and `src/main/javax` with
    `--release 8` into `xml.classes.javax`.
  - `compile-jakarta` at phase `compile` compiles `src/main/java` and `src/main/jakarta` with
    `--release 11` into `xml.classes.jakarta`.
- `maven-jar-plugin` (version of the parent) creates the artifacts at phase `process-classes`: the
  classifier `javax` from `xml.classes.javax` and the classifier `jakarta` from `xml.classes.jakarta`; the
  executions are declared after the goal of FR-001, so within the phase the metadata is written before the
  jars are packed and both jars carry it.
- The goal of FR-001 runs once per variant at phase `process-classes`; each run takes its variant
  directory as the production directory and writes
  `META-INF/native-image/<groupId>/<artifactId>/reachability-metadata.json` into it, so each classifier
  jar carries its own metadata and no copy step exists.
- `ReachabilityMetadataGoal` takes its output directory from the configuration; the default is the build
  output directory of the current module (FR-001, `## Requirement`).
- Consumers select the variant by classifier: `net.sf.exlp:exlp-xml:<version>:javax` or `:jakarta`;
  `bom/pom.xml` manages both, and `util/pom.xml` and `test/pom.xml` name `javax`.
- The module build requires JDK 11 or newer; the `javax` artifact carries class-file version 52 and the
  `jakarta` artifact version 55.
- The profiles `javax` and `jakarta` only regenerate the JAXB sources from `xml/src/main/xsd/`
  (`cxf-xjc-plugin` 3.3.2 with `javax.xjb`, 4.0.0 with `jakarta.xjb`); they no longer select the build
  variant.

## Rationale

- One command on one source revision produces both variants:
  `mvn -DskipTests -Djava.awt.headless=true clean install`.
- Without an artifact that carries no classifier, a consumer cannot bind a variant by accident; the
  previous jar without a classifier changed its content with the JDK of the build.
- Separate output directories are what make both variants possible in one module run: the two source
  trees declare the same type names and cannot share one output directory.
- Both variants compile into their own directory and generate their own metadata, so neither variant is
  privileged and no output directory holds the classes of one variant only.
- The goal of FR-001 takes its output directory from the configuration, so the metadata does not have to
  be generated in the module output directory and copied to the second variant.
- The classifier jars remain ordinary attached artifacts, so `install`, `deploy`, and the signatures of
  the release profile need no handling of their own.
- Attaching the classifier jars at `process-classes`, before the `test` phase, keeps a plain `mvn test` on
  the repository working: a module that depends on a classifier resolves it from the reactor, and the jars
  of `xml` must exist before the `compile` of that module; the default `package` phase would leave them
  missing in a `test` run and `util` would not compile.
- `--release` pins the API level of the compilation instead of switching the JDK, so the `javax`
  artifact cannot use APIs that are newer than Java 8.
- Classifier jars keep the coordinates of the module, so `bom/pom.xml` and the consumers change only by
  the classifier.

## Alternatives

### Two build commands with the variant selected by the JDK (initial situation)

Keeps one module with jar packaging, but needs two runs and two JDK switches, and the jar without a
classifier carries the variant of the last run.

### `packaging=jar` with a disabled main jar and `install-file`/`deploy-file`

Reaches the same repository content, but the deployment needs its own path, because `deploy-file` does
not sign the artifacts of the release profile.

### A JDK toolchain per compile execution

Compiles the `javax` artifact with a genuine JDK 8 and the `jakarta` artifact with a genuine JDK 11 in
one run. Requires an entry in the toolchain file of every build machine, and the toolchain does not pin
the API level.

### Two modules with distinct artifact IDs

Keeps ordinary jar packaging per variant, but changes the artifact ID and therefore the dependency of
every consumer.

### Eclipse Transformer

Compiles once and rewrites the bytecode of the `javax` jar into a `jakarta` jar. Requires one external
component, which needs a decision of its own, and the `jakarta` artifact would not be the product of the
Jakarta compiler.

## Impact

- `xml/pom.xml` holds the POM packaging, the two compile executions, the two jar executions, and the two
  goal executions.
- `maven` module: `ReachabilityMetadataGoal` takes the output directory from the configuration, and
  `ReachabilityMetadataGenerator` scans the compile classpath plus the production directory.
- FR-001: the requirement and AC-FR-001-09 name the output directory configured for the run.
- `util/pom.xml` and `test/pom.xml` name the classifier `javax`, and `bom/pom.xml` manages both
  classifiers.
- `README.md` (`## JAXB Variants`, `## Build and Start`) and the index (`doc/status.md`) name the
  decision.
- A build of the `xml` module with JDK 8 no longer succeeds.

## Open Points

- The `addon` module (`addon/pom.xml`) is pinned to the parent line 0.1.15 and names no classifier; it
  needs one as soon as its version follows the repository.
- A later `src/main/resources` of the `xml` module is copied into the module output directory only; it
  needs a copy into both variant directories.
- Which sources and javadoc artifacts a release publishes per classifier; `maven-source-plugin` skips a
  POM packaging.
- Unit tests for the `xml` module: POM packaging binds no test phases, so a later test compilation and
  the test goal need their own executions.

## Evidence

### Implementation

- `xml/pom.xml` – POM packaging, both compile executions, both jar executions, one goal run per variant
- `maven/src/main/java/org/exlp/maven/goal/ReachabilityMetadataGoal.java` – the output directory is a
  configuration parameter
- `maven/src/main/java/org/exlp/maven/reachability/ReachabilityMetadataGenerator.java` – the production
  directory is part of the scanned classpath

### Tests

- `mvn -pl xml -DskipTests -Djava.awt.headless=true clean install` `[SUCCESS]`; the local repository
  holds `exlp-xml-<version>.pom`, `exlp-xml-<version>-javax.jar`, and `exlp-xml-<version>-jakarta.jar`,
  and no jar without a classifier
- `mvn -o clean test` `[SUCCESS]` on the whole repository: the classifier jars of `xml` exist before the
  `test` phase of `util`, so `util` compiles and its tests resolve the variant artifact from the reactor
- `xml/target/classes-javax` and `xml/target/classes-jakarta` hold 40 classes each and their own
  metadata; the module output directory holds no class
- The `javax` jar carries class-file version 52 and the `javax.xml.bind` annotations, the `jakarta` jar
  version 55 and the `jakarta.xml.bind` annotations; both carry the reachability metadata
- `TestReachabilityMetadataGenerator.scansProductionDirectoryOutsideConfiguredClasspath` – a production
  directory outside the configured classpath is scanned
- A consumer with the classifier `javax` and a consumer with the classifier `jakarta` compile and run
  against the installed artifacts

