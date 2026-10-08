---
id: ADR-0001
title: Scan module classes with ClassGraph
status: proposed
---

# ADR-0001: Scan module classes with ClassGraph

## Context

FR-001 requires the Maven plugin to find production classes in the module being packaged from
annotation types supplied in the plugin configuration. It excludes dependency and test-output
classes as metadata candidates and requires the generated registrations for a class to be combined
into one entry.

The repository is a Java 8, multi-module Maven project with an existing `maven` module. Class
selection is build-time work; FR-001 does not call for agent-based runtime tracing.

## Decision Drivers

- Identify classes by configured annotation types without requiring the application to run.
- Limit metadata candidates to the current module's production classes.
- Avoid maintaining a custom classpath and class-file scanner.
- Keep the scanner out of the artifact whose reachability metadata is generated.

## Proposed Decision

Use ClassGraph (`io.github.classgraph`) as the build-time class-scanning component of the existing
Maven plugin.

- The candidate classes are the current module's compiled production classes; dependency and test
  classes are not metadata candidates.
- Annotation types come from the plugin configuration described by FR-001.
- When a class matches multiple configured annotations, combine their registrations into one
  metadata entry for that class, as specified by FR-001.
- ClassGraph is a dependency of the Maven plugin, not of the artifact being scanned.
- Manage the ClassGraph version through the project's existing BOM; select no version in this ADR.
- Verify the selected version against the project's Java 8 baseline before implementation.

This decision is proposed and does not authorize use of the dependency until the responsible person
accepts it.

## Alternatives Considered

### JDK reflection with a custom classpath enumerator

This avoids a scanner dependency but requires project code to enumerate class names and load them
before evaluating annotations. That duplicates classpath-scanning behavior in the plugin.

### Custom class-file scanning

This avoids runtime class loading but requires the project to implement and maintain annotation and
class-hierarchy scanning itself.

### Native Image Agent output

This gathers metadata from executed application behavior rather than selecting classes from the
configured annotations. It does not implement FR-001's annotation-driven discovery behavior.

## Consequences

- The Maven plugin gains a build-time dependency on ClassGraph.
- The scanner must be restricted to the current module's production classes.
- The chosen dependency version must remain compatible with the project's Java 8 baseline.
- Tests must verify annotation matching, inheritance, and classpath boundaries required by FR-001.
- The Native Image metadata format and registration behavior remain governed by FR-001.

## References

- FR-001: Generate annotation-based Native Image reachability metadata
