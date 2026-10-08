---
id: ADR-0001
title: Scan module classes with ClassGraph
status: proposed
---

# ADR-0001: Scan module classes with ClassGraph

## Context

FR-001 requires the Maven plugin to generate Native Image reachability metadata from annotated
production classes. It leaves the scanner for that task open in `## Assumptions` (item 1), where
ClassGraph (`io.github.classgraph`) is named as a candidate whose use requires an accepted
architecture decision. This decision answers which library performs the class scan; which classes
are selected and how registrations are formed are governed by FR-001.

The repository is a Java 8, multi-module Maven project with an existing `maven` module. Class
selection is build-time work; FR-001 does not call for agent-based runtime tracing.

## Decision Drivers

- Select classes without requiring the application to run.
- Avoid maintaining a custom classpath and class-file scanner.
- Keep the scanner out of the artifact whose reachability metadata is generated.
- Use a library that supports the project's Java 8 baseline.

## Proposed Decision

Use ClassGraph (`io.github.classgraph`) as the build-time class-scanning library of the existing
Maven plugin. The decision covers the library and its version; the scanning behaviour follows
FR-001.

- Version: `io.github.classgraph:classgraph:4.8.197` - the latest release and compatible with the
  project's Java 8 baseline (class-file version 52, Java-Version 8; verified on JDK 8).
- ClassGraph is a build-time dependency of the Maven plugin, not of the artifact being scanned.
- ClassGraph is a single self-contained artifact that requires no further dependencies.
- The Maven plugin declares the dependency; its version is declared in the project's BOM
  (`bom/pom.xml`).

This decision is proposed and does not authorize use of the dependency until the responsible person
accepts it.

## Alternatives Considered

### JDK reflection with a custom classpath enumerator

This avoids a scanner dependency but requires project code to enumerate class names and load them
before evaluating annotations. That duplicates classpath-scanning behavior in the plugin.

### Custom class-file scanning

This avoids runtime class loading but requires the project to implement and maintain annotation and
class-hierarchy scanning itself.

## Consequences

- The Maven plugin gains a build-time dependency on ClassGraph.
- Version updates must keep the library compatible with the project's Java 8 baseline; from 4.8.182
  on, ClassGraph targets Java 8, while earlier releases target Java 7.
- The use of the library is covered by the tests of the Maven plugin; the evidence is maintained by
  FR-001 (`## Evidence`).
- The metadata format, the registrations, and the scanning behaviour remain governed by FR-001.

## References

- FR-001, `## Assumptions` - names ClassGraph as a candidate scanner and requires this decision
- FR-001: Generate annotation-based Native Image reachability metadata
