---
id: FR-001
title: Generate annotation-based Native Image reachability metadata
type: functional
status: implemented
priority: must
depends_on: []
related: []
---

# FR-001: Generate annotation-based Native Image reachability metadata

## Requirement

The Maven plugin generates reachability metadata for annotated production classes in the current
module's artifact (AC-FR-001-01).

- The goal writes the metadata into the output directory configured for the goal run; the default is the
  build output directory of the current module (AC-FR-001-09).
- The generated file is accepted by the Native Image version bound in ADR-0001 (AC-FR-001-02).
- The plugin selects production classes through annotation types configured in the Maven plugin (AC-FR-001-03).
- Every selected class is registered with all its declared constructors, fields, and methods (AC-FR-001-04).
- When a class matches multiple configured annotations, one entry covers it (AC-FR-001-05).
- Annotation inheritance follows Java `@Inherited` semantics (AC-FR-001-06).
- A configured annotation on an interface does not select the implementing class (AC-FR-001-06).
- The generated file contains Reflection registrations only (AC-FR-001-07).
- The plugin goal supports explicit invocation and optional binding to a Maven lifecycle phase (AC-FR-001-08).
- The generated file is packaged at
  `META-INF/native-image/<groupId>/<artifactId>/reachability-metadata.json` (AC-FR-001-09).
- Successful generation replaces existing metadata at the output path (AC-FR-001-10).
- No matching production class produces a valid metadata file with no Reflection registrations (AC-FR-001-11).
- Invalid configuration or scan/generation errors fail the goal with a diagnostic (AC-FR-001-12).
- A failed run does not package partial metadata (AC-FR-001-12).

## Rationale

Annotation-driven frameworks may access classes and members reflectively, so their reachability
information must accompany the artifact.

## Scope

In scope:

- Reflection metadata derived from configured annotation types on the current module's production classes.
- Generation of the metadata into the output directory configured for the goal run (default: the build
  output directory of the current module).
- Packaging the generated metadata with the current module's artifact.

Out of scope:

- Runtime-agent trace collection.
- Metadata categories other than Reflection.
- Classes available only in dependencies or test output.
- Selection of the lifecycle phase for the binding.
- Selection of individual member kinds per annotation type.
- Modification of source files.

## Open Questions

None.

## Decided Questions

None.

## Acceptance Criteria

### AC-FR-001-01: Generate metadata for the current module artifact

Given:

- The Maven module contains production classes selected by configured annotations.

When:

- The plugin goal runs successfully.

Then:

- The generated artifact contains reachability metadata for the selected classes.

### AC-FR-001-02: Acceptance by GraalVM Native Image

Given:

- A module artifact contains generated reachability metadata.

When:

- GraalVM Native Image in the version bound in ADR-0001 consumes the artifact.

Then:

- The metadata is accepted without a format error.

### AC-FR-001-03: Select classes through configured annotations

Given:

- A production class in the current module bears a configured annotation.

When:

- The plugin goal runs.

Then:

- The generated metadata includes that class.

### AC-FR-001-04: Register all declared members of a selected class

Given:

- A class selected by a configured annotation has public and non-public constructors, fields, and
  methods.

When:

- The plugin goal runs.

Then:

- The metadata registers all its declared constructors, fields, and methods.

### AC-FR-001-05: Combine registrations for a multiply annotated class

Given:

- A class matches multiple configured annotations.

When:

- The plugin goal runs.

Then:

- The metadata contains one entry for the class.

### AC-FR-001-06: Apply Java annotation inheritance semantics

Given:

- A configured annotation marked `@Inherited` is on a superclass but not its subclass.
- Another configured annotation without `@Inherited` is on a different superclass.
- A configured annotation is on an interface but not its implementing class.

When:

- The plugin scans the current module.

Then:

- The subclass matches only through the `@Inherited` annotation.
- The subclass does not match through the annotation without `@Inherited`.
- The implementing class does not match only through the interface annotation.

### AC-FR-001-07: Generate Reflection metadata only

Given:

- A class matches a configured annotation.

When:

- The plugin goal generates metadata.

Then:

- The generated file contains Reflection registrations only.

### AC-FR-001-08: Support both invocation modes

Given:

- The same module and configuration are used for explicit goal invocation and lifecycle-bound invocation.

When:

- The plugin runs in each mode.

Then:

- Both runs produce metadata with identical registrations.

### AC-FR-001-09: Package metadata at the artifact path

Given:

- The plugin goal completes successfully with an output directory configured, and the module artifact is
  packaged.

When:

- The output directory and the artifact contents are inspected.

Then:

- The goal wrote `META-INF/native-image/<groupId>/<artifactId>/reachability-metadata.json` into the
  configured output directory.
- The artifact contains `META-INF/native-image/<groupId>/<artifactId>/reachability-metadata.json`.

### AC-FR-001-10: Replace prior metadata on successful generation

Given:

- Metadata already exists at the generated output path.

When:

- The plugin successfully generates new metadata.

Then:

- The packaged file contains the new registrations and does not retain registrations only present in the prior file.

### AC-FR-001-11: Produce valid metadata when no class matches

Given:

- No production class in the current module matches a configured annotation.

When:

- The plugin goal runs.

Then:

- The goal succeeds and produces a valid metadata file with no Reflection registrations.

### AC-FR-001-12: Fail on configuration or scan errors

Given:

- The configuration is invalid or class scanning/generation fails.

When:

- The plugin goal runs.

Then:

- The goal fails with a diagnostic identifying the cause.
- Partial metadata is not packaged.

## Dependencies

None.

## Evidence

### Implementation

- `maven/src/main/java/org/exlp/maven/goal/ReachabilityMetadataGoal.java` – the goal scans the
  module and writes the metadata (AC-FR-001-01)
- `maven/src/main/java/org/exlp/maven/reachability/ReachabilityMetadataGenerator.java` – one entry
  per selected class (AC-FR-001-05)
- `maven/src/main/java/org/exlp/maven/reachability/ReachabilityMetadataWriter.java` – the metadata
  is written to the required path (AC-FR-001-09)

### Tests

- `TestReachabilityMetadataGenerator.selectsClassesWithConfiguredAnnotation` – configured
  annotations select the intended classes (AC-FR-001-03)
- `TestReachabilityMetadataGenerator.registersAllDeclaredMembersOfSelectedClasses` – every declared
  constructor, field, and method of a selected class is registered (AC-FR-001-04)
- `TestReachabilityMetadataGenerator.combinesRegistrationsOfMultipleAnnotations` – a class matching
  two configured annotations has one entry (AC-FR-001-05)
- `TestReachabilityMetadataGenerator.appliesInheritedAnnotationSemantics` – annotation inheritance
  cases produce the specified classes (AC-FR-001-06)
- `TestReachabilityMetadataGenerator.generatesReflectionRegistrationsOnly` – the generated file
  contains Reflection registrations only (AC-FR-001-07)
- `TestReachabilityMetadataGenerator.excludesClassesOutsideProductionDirectory` – classes outside
  the production output directory are excluded (AC-FR-001-01)
- `TestReachabilityMetadataGenerator.producesEmptyMetadataWithoutMatches` – no-match execution
  produces valid empty metadata (AC-FR-001-11)
- `TestReachabilityMetadataGoal.failsWithoutConfiguredAnnotations` – invalid configuration fails
  the goal with a diagnostic (AC-FR-001-12)
- `TestReachabilityMetadataGoal.writesMetadataAtArtifactPath` – the goal writes the metadata into the
  configured output directory (AC-FR-001-09)
- `TestReachabilityMetadataGenerator.scansProductionDirectoryOutsideConfiguredClasspath` – the
  production directory is scanned although the configured classpath does not contain it (AC-FR-001-01)
- `TestReachabilityMetadataGoal.producesIdenticalRegistrationsOnEachRun` – repeated runs produce
  identical registrations (AC-FR-001-08)
- `TestReachabilityMetadataWriter.replacesPriorMetadata` – a prior file at the output path is
  replaced on successful generation (AC-FR-001-10)
- `mvn -pl maven org.apache.maven.plugins:maven-surefire-plugin:3.2.5:test` – 22 tests pass
  (AC-FR-001-03)
- `mvn -o -pl xml process-classes` – the configured annotation types of the `xml` module select 25
  classes with all declared members (AC-FR-001-04)
- `mvn package` – a lifecycle-bound run in a sample module packs the metadata file into the JAR
  (AC-FR-001-09)
- `mvn package` – a lifecycle-bound run in a sample module excludes a test-only annotated class
  (AC-FR-001-01)
- `mvn package` – a lifecycle-bound run in a sample module inherits an annotation of a dependency
  (AC-FR-001-06)
- `mvn net.sf.exlp:exlp-maven:0.1.18-SNAPSHOT:reachabilityMetadata` – an explicit run in a sample
  module produces the same registrations as the lifecycle-bound run (AC-FR-001-08)
- `mvn net.sf.exlp:exlp-maven:0.1.18-SNAPSHOT:reachabilityMetadata` – a run without a matching
  class produces a valid file with an empty Reflection array (AC-FR-001-11)
- `mvn net.sf.exlp:exlp-maven:0.1.18-SNAPSHOT:reachabilityMetadata` – a run without configured
  annotations fails the goal with a diagnostic and leaves no metadata (AC-FR-001-12)
- `reachability-metadata-schema-v1.2.0.json` – the generated file validates against the schema
  (AC-FR-001-02)
- This requirement – open: GraalVM Native Image 25 consumes the packaged metadata without a
  format error (AC-FR-001-02)

### Documentation

- `README.md` – plugin configuration and the packaged metadata location are documented (AC-FR-001-09)
