---
id: ADR-0004
title: Test strategy
status: accepted
date: 2026-10-10
---

# ADR-0004: Test strategy

## Context

The open question was what the verification of the project consists of and which test programs
exist. It was open in the project overview (`README.md`, `## Open Fundamental Decisions`) and in the
architecture file (`doc/requirements/architecture.md`, `## Technology`, `Testing`). The choice to be
decided was how test programs are named, stored, and run.

## Decision

1. The test programs of the project are located under `src/test/java`.
2. Test programs run with JUnit 5 (Jupiter 5.10.0, managed in the BOM `net.sf.exlp:exlp-bom`, module
   `bom`), and the assertions come from `org.junit.jupiter.api.Assertions`. No further test framework
   is introduced.
3. A test program is a class under `src/test/java` that carries at least one `@Test` method and whose
   name follows the selection pattern of the test run (the names `Test*` and `*Test`). A base or
   helper class without a `@Test` method is not a test program, even if its name matches the pattern.
4. A test program runs without an application container, without a database, and without a network
   and is therefore repeatable without preparation. A logging configuration under
   `src/test/resources` is allowed and does not stand in the way.
5. Time and inputs of a test program are parameters; it holds them as named constants in the test
   program or as test data under `src/test/resources`. Fixed inputs are allowed, a fixed expectation
   is not; it derives the expectation from its inputs. It does not use identifiers of the
   environment, such as the time zone, as a fixed value.
6. The verification command `mvn -q verify` runs the test programs under JDK 11 or newer and ends with
   the return value 0.

## Rationale

- JUnit 5 is already bound in the modules `util`, `maven`, and `shell` and managed in the BOM
  (`bom`); a change would bring no gain.
- The verification command evaluates only the return value; it therefore needs test programs that
  determine success and failure themselves, and not programs that only write their output.
- The verification command runs without an application container and without a database; a program
  that needs these is therefore not a test program in the sense of this decision.
- A `main` program that uses fixed calendar dates and fixed identifiers and opens connections is not
  repeatable without preparation and is therefore likewise not a test program.
- The test programs of a module are in the test scope and do not reach its jar.

## Alternatives

- Test programs without a framework as pure `main` programs – not selected; then every program would
  have to report its success itself instead of leaving it to the framework.
- Extending the verification command to all classes under `src/test/java`, also without a name
  pattern – not selected; many of them need an application container, a database, and a network and
  do not determine the return value reliably.

## Impact

- Requirements: FR-001, FR-002, and FR-003, whose verification uses the test programs.
- Files: `util/src/test/java`, `maven/src/test/java`, and `shell/src/test/java` take the test programs.
- `README.md` (`## Technical Assumptions`) and the index (`doc/status.md`) name the decision.

## Open Points

- The Maven version that the build requires is not regulated by this decision; the root POM binds
  `maven-surefire-plugin` 3.6.0, which needs Maven 3.6.3 or newer.

## Evidence

### Implementation

- `pom.xml` (root) – binds `maven-surefire-plugin` 3.6.0 in the plugin management; every module
  inherits the version, and the plugin runs the test programs through the JUnit Platform
- `bom/pom.xml` – manages `org.junit.jupiter:junit-jupiter-api` and
  `org.junit.jupiter:junit-jupiter-engine` (5.10.0, test scope); `util/pom.xml`, `maven/pom.xml`, and
  `shell/pom.xml` bind `junit-jupiter-api` in the test scope, `util/pom.xml` and `maven/pom.xml` add
  `junit-jupiter-engine`, and `test/pom.xml` provides both artifacts for its support classes without
  running a test program (points 1 and 2)
- `util/src/test/java`, `maven/src/test/java`, and `shell/src/test/java` – carry test programs, that
  is classes with `@Test` methods; the base classes `org.exlp.test.AbstractExlpTest` (`util`),
  `org.exlp.test.AbstractExlpShellTest` (`shell`), and `de.kisner.exlp.test.AbstractExlpMavenTest`
  (`maven`) use JUnit 5 as well (points 1 to 3)
