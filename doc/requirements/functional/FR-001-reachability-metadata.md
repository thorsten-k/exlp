---
id: FR-001
title: Generate annotation-based Native Image reachability metadata
type: functional
status: approved
priority: must
depends_on: []
related: []
---

# FR-001: Generate annotation-based Native Image reachability metadata

## Requirement

The Maven plugin generates reachability metadata for annotated production classes in the current
module's artifact (AC-FR-001-01).

- The generated file is accepted by the Native Image version bound in ADR-0001 (AC-FR-001-02).
- The plugin selects production classes through annotation types configured in the Maven plugin (AC-FR-001-03).
- For each annotation type, configuration selects which of the class, constructors, fields, and
  methods are registered (AC-FR-001-04).
- When a class matches multiple configured annotations, their registrations are combined in one
  entry for that class (AC-FR-001-05).
- Every declared member of a selected member kind is registered, including non-public members
  (AC-FR-001-06).
- Annotation inheritance follows Java `@Inherited` semantics (AC-FR-001-07).
- A configured annotation on an interface does not select the implementing class (AC-FR-001-07).
- The generated file contains Reflection registrations only (AC-FR-001-08).
- The plugin goal supports explicit invocation and optional binding to a Maven lifecycle phase (AC-FR-001-09).
- The generated file is packaged at
  `META-INF/native-image/<groupId>/<artifactId>/reachability-metadata.json` (AC-FR-001-10).
- Successful generation replaces existing metadata at the output path (AC-FR-001-11).
- No matching production class produces a valid metadata file with no Reflection registrations (AC-FR-001-12).
- Invalid configuration or scan/generation errors fail the goal with a diagnostic (AC-FR-001-13).
- A failed run does not package partial metadata (AC-FR-001-13).

## Rationale

Annotation-driven frameworks may access classes and members reflectively, so their reachability
information must accompany the artifact.

## Scope

In scope:

- Reflection metadata derived from configured annotation types on the current module's production classes.
- Generation of the metadata into the build output directory of the current module.
- Packaging the generated metadata with the current module's artifact.

Out of scope:

- Runtime-agent trace collection.
- Metadata categories other than Reflection.
- Classes available only in dependencies or test output.
- Selection of the lifecycle phase for the binding.
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

### AC-FR-001-04: Apply per-annotation registration selections

Given:

- A class matches a configured annotation with selected registration elements.

When:

- The plugin goal runs.

Then:

- The metadata includes the class and member kinds selected for that annotation.

### AC-FR-001-05: Combine registrations for a multiply annotated class

Given:

- A class matches multiple configured annotations with different registration selections.

When:

- The plugin goal runs.

Then:

- The metadata contains one entry for the class with the union of all selected registrations.

### AC-FR-001-06: Register all selected declared members

Given:

- A matching class has public and non-public constructors, fields, or methods.
- The configuration selects one or more of those member kinds.

When:

- The plugin goal runs.

Then:

- The metadata registers every declared member of each selected member kind.

### AC-FR-001-07: Apply Java annotation inheritance semantics

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

### AC-FR-001-08: Generate Reflection metadata only

Given:

- A class matches a configured annotation.

When:

- The plugin goal generates metadata.

Then:

- The generated file contains Reflection registrations only.

### AC-FR-001-09: Support both invocation modes

Given:

- The same module and configuration are used for explicit goal invocation and lifecycle-bound invocation.

When:

- The plugin runs in each mode.

Then:

- Both runs produce metadata with identical registrations.

### AC-FR-001-10: Package metadata at the artifact path

Given:

- The plugin goal completes successfully and the module artifact is packaged.

When:

- The artifact contents are inspected.

Then:

- The artifact contains `META-INF/native-image/<groupId>/<artifactId>/reachability-metadata.json`.

### AC-FR-001-11: Replace prior metadata on successful generation

Given:

- Metadata already exists at the generated output path.

When:

- The plugin successfully generates new metadata.

Then:

- The packaged file contains the new registrations and does not retain registrations only present in the prior file.

### AC-FR-001-12: Produce valid metadata when no class matches

Given:

- No production class in the current module matches a configured annotation.

When:

- The plugin goal runs.

Then:

- The goal succeeds and produces a valid metadata file with no Reflection registrations.

### AC-FR-001-13: Fail on configuration or scan errors

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

- This requirement – open: registrations are combined into one entry per class (AC-FR-001-05)
- This requirement – open: generated metadata is packaged at the required path (AC-FR-001-10)

### Tests

- This requirement – open: the Native Image version bound in ADR-0001 accepts the generated metadata (AC-FR-001-02)
- This requirement – open: configured annotations select the intended classes (AC-FR-001-03)
- This requirement – open: configured registration elements appear in the output (AC-FR-001-04)
- This requirement – open: every declared member of a selected member kind is registered (AC-FR-001-06)
- This requirement – open: annotation inheritance cases produce the specified classes (AC-FR-001-07)
- This requirement – open: the generated file contains Reflection registrations only (AC-FR-001-08)
- This requirement – open: dependency and test-only classes are excluded (AC-FR-001-01)
- This requirement – open: no-match execution produces valid empty metadata (AC-FR-001-12)
- This requirement – open: invalid configuration and scan errors fail the goal (AC-FR-001-13)
- This requirement – open: both invocation modes produce metadata with identical registrations (AC-FR-001-09)
- This requirement – open: a prior file at the output path is replaced on successful generation (AC-FR-001-11)

### Documentation

- This requirement – open: plugin configuration and packaged metadata location are documented (AC-FR-001-10)
