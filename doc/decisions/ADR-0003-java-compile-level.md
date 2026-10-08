---
id: ADR-0003
title: Compile all modules against the Java 8 API
status: accepted
date: 2026-10-08
related:
  - ADR-0002
---

# ADR-0003: Compile all modules against the Java 8 API

## Context

The project overview (`README.md`, `## Technical Assumptions`) names Java 8 as the platform of the
artifacts, and the root `pom.xml` configured the compiler with `source` and `target` 8. The build of the
repository runs on JDK 11 or newer (ADR-0002). The open question is how the artifacts of the modules of
the layered chain keep running on Java 8 although a newer JDK compiles them.

- `source` and `target` select the source level and the class-file version, but the compilation uses the
  API of the JDK that runs the compiler.
- A build on JDK 11 could therefore use types and members that Java 8 does not have; the class files
  stay at version 52, and the failure appears only at runtime on Java 8.

## Decision

Every module of the repository compiles against the Java 8 API.

- The root `pom.xml` configures `maven-compiler-plugin` with the parameter `release` and the value `8`;
  the parameters `source` and `target` are not used.
- The `xml` module keeps one value per run: `--release 8` for the `javax` artifact and `--release 11`
  for the `jakarta` artifact (ADR-0002); the configuration of an execution overrides the inherited value.
- The build requires JDK 9 or newer for the `release` argument, in practice JDK 11 or newer (ADR-0002).
- The artifacts of the modules of the layered chain carry class-file version 52 and use the Java 8 API
  only; the test compilation is checked in the same way.

## Rationale

- `--release` selects the API data of that Java version, so the compiler rejects types and members that
  the version does not provide.
- One value in the root `pom.xml` covers every module; no module repeats it.
- The failure appears while compiling instead of in the runtime of a Java 8 consumer.

## Alternatives

### `source` and `target` 8 (initial situation)

Keeps class-file version 52 but compiles against the API of the build JDK, so a Java 9 or newer API
stays unnoticed.

### Building with JDK 8

Rejects a newer API by definition, but ADR-0002 requires JDK 11 or newer for the `xml` module, so the
repository cannot be built with a JDK 8 throughout.

### Signature check of the Java 8 API after the compilation

Checks the bytecode and works with any JDK, but adds an external component, which requires a decision of
its own, and a separate signature artifact.

## Impact

- `pom.xml` (root) – the parameter `release` replaces `source` and `target` for every module.
- `xml/pom.xml` – the two runs keep their values 8 and 11 (ADR-0002).
- `README.md` (`## Technical Assumptions`, `## Build and Start`) and the index (`doc/status.md`) name the
  decision.

## Open Points

- A module that later needs Java 11 APIs configures its own `release` value; the decision does not
  regulate which module that is.

## Evidence

### Implementation

- `pom.xml` – the compiler configuration of the root POM carries `release` with the value 8

### Tests

- `mvn -Pram -DskipTests -Djava.awt.headless=true clean install` `[SUCCESS]`; every module compiles, and
  the test sources compile as well
- `mvn -pl interfaces compile` with a class that uses `List.of` fails with `cannot find symbol`; the same
  class compiles with the previous `source` and `target` configuration
- The installed jars of the modules carry class-file version 52
