---
id: ADR-0001
title: Scan module classes with ClassGraph
status: accepted
date: 2026-10-08
affects:
  - FR-001
---

# ADR-0001: Scan module classes with ClassGraph

## Context

FR-001 (`## Requirement`) requires the Maven plugin to generate Native Image reachability metadata
from annotated production classes; FR-001 (`## Scope`) excludes agent-based runtime tracing. Two
technical choices behind that requirement remain with this decision: the library that performs the
class scan, and the Native Image version that accepts the generated metadata (AC-FR-001-02).

The repository is a Java 8, multi-module Maven project with an existing `maven` module. Class
selection is build-time work, and the generated metadata is accepted by one Native Image version.

## Decision

Use ClassGraph (`io.github.classgraph`) as the build-time class-scanning library of the existing
Maven plugin.

- Bound version: `io.github.classgraph:classgraph:4.8.197`; the artifact carries class-file
  version 52 and therefore runs on the project's Java 8 platform.
- Bound Native Image version: `GraalVM Native Image 25`.
- Version updates keep the library compatible with the project's Java 8 platform.
- ClassGraph is a build-time dependency of the Maven plugin, not of the artifact being scanned.
- ClassGraph is a single self-contained artifact that requires no further dependencies.
- The Maven plugin declares the dependency; its version is declared in the project's BOM
  (`bom/pom.xml`).

## Rationale

- Select classes without requiring the application to run.
- Avoid maintaining a custom classpath and class-file scanner.
- Keep the scanner out of the artifact whose reachability metadata is generated.
- Releases up to 4.8.181 carry class-file version 51 and do not run on the project's Java 8
  platform.
- The acceptance check of FR-001 (AC-FR-001-02) requires one fixed Native Image version; a pinned
  version is maintained by a constraint or a decision (`doc/requirements/architecture.md`,
  `## Authoritative Locations`).

## Alternatives

### JDK reflection with a custom classpath enumerator

This avoids a scanner dependency but requires project code to enumerate class names and load them
before evaluating annotations. That duplicates classpath-scanning behavior in the plugin.

### Custom class-file scanning

This avoids runtime class loading but requires the project to implement and maintain annotation and
class-hierarchy scanning itself.

## Impact

- FR-001 covers the class selection, the registrations, and the metadata format; this decision adds
  the library, its bound version, and the bound Native Image version.
- `bom/pom.xml` manages the version; `maven/pom.xml` declares the dependency.
- The tests of the Maven plugin cover the use of the library; FR-001 maintains the evidence
  (`## Evidence`).
- `doc/status.md`, `README.md`, and `doc/requirements/architecture.md` list the decision and the
  plugin goals.

## Open Points

- How the goal obtains the classpath of the current module is an implementation detail of the
  `maven` module.
- The consuming build selects the Maven lifecycle phase for the goal binding (FR-001,
  `## Scope`).

## Evidence

### Implementation

- `bom/pom.xml` – the version of ClassGraph is managed for the Maven plugin (AC-FR-001-01)
- `maven/pom.xml` – ClassGraph is declared as a build-time dependency of the plugin (AC-FR-001-01)

### Tests

- `mvn -pl maven org.apache.maven.plugins:maven-surefire-plugin:3.2.5:test` – the plugin scans the
  current module with ClassGraph and writes the metadata (AC-FR-001-10)
- `mvn -pl maven org.apache.maven.plugins:maven-surefire-plugin:3.2.5:test` – the scanning behavior
  and the registrations match FR-001 (AC-FR-001-04)
