---
id: ADR-0004
title: Test strategy
status: proposed
date: 2026-10-10
---

# ADR-0004: Test strategy

## Context

The open question was what the verification of the project consists of and which test programs exist.
The choice to be decided was how test programs are named, stored, and run.

## Decision

1. The test programs of the project are located under `src/test/java`.
2. Test programs run with JUnit 5; the version is bound by the BOM of the project (`net.sf.exlp:exlp-bom`,
   module `bom`), and the assertions come from `org.junit.jupiter.api.Assertions`. No further test framework
   is introduced.
3. A test program is a class under `src/test/java` that carries at least one `@Test` method and whose name
   follows the selection pattern of the test run (the names `Test*` and `*Test`). A base or helper class
   without a `@Test` method is not a test program, even if its name matches the pattern.
4. A test program runs without an application container, without a database, and without a network and is
   therefore repeatable without preparation. A logging configuration under `src/test/resources` is allowed
   and does not stand in the way.
5. Time and inputs of a test program are parameters; it holds them as named constants in the test program or
   as test data under `src/test/resources`. Fixed inputs are allowed, a fixed expectation is not; it derives
   the expectation from its inputs. It does not use identifiers of the environment, such as the time zone,
   as a fixed value.
6. The verification command `mvn -q -DskipTests=false verify` runs the test programs and ends with the
   return value 0.

## Rationale

- JUnit 5 is already bound in the modules `util`, `maven`, and `shell` and managed in the BOM (`bom`); a
  change would bring no gain.
- The verification command evaluates only the return value; it therefore needs test programs that determine
  success and failure themselves, and not programs that only write their output.
- The verification command runs without an application container and without a database; a program that
  needs these is therefore not a test program in the sense of this decision.
- A `main` program that uses fixed calendar dates and fixed identifiers and opens connections is not
  repeatable without preparation and is therefore likewise not a test program.
- The test programs of a module are in the test scope and do not reach its jar.

## Alternatives

- Test programs without a framework as pure `main` programs – not selected; then every program would have to
  report its success itself instead of leaving it to the framework.
- Extending the verification command to all classes under `src/test/java`, also without a name pattern – not
  selected; many of them need an application container, a database, and a network and do not determine the
  return value reliably.

## Impact

- Files: `util/src/test/java`, `maven/src/test/java`, and `shell/src/test/java` take the test programs.

## Open Points

- The Maven version that the build requires is not regulated by this decision; the root POM binds
  `maven-surefire-plugin` 3.6.0, which needs Maven 3.6.3 or newer, while the modules `util` and `test`
  override the plugin with version 2.22.2.
- JUnit 4 is still managed in the BOM (`bom/pom.xml`, `junit:junit` 4.12), but no module binds it and no
  source file imports `org.junit.*`; the README still names JUnit 4 as the test library. The managed entry
  is to be removed.

## Evidence

### Implementation

- `pom.xml` (root) – binds `maven-surefire-plugin` 3.6.0 in the plugin management; the plugin runs the test
  programs through the JUnit Platform
- `bom/pom.xml` – manages `org.junit.jupiter:junit-jupiter-api` and `org.junit.jupiter:junit-jupiter-engine`
  (5.10.0, test scope); `util/pom.xml`, `maven/pom.xml`, `shell/pom.xml`, and `test/pom.xml` bind
  `junit-jupiter-api` in the test scope, and `util/pom.xml`, `maven/pom.xml`, and `test/pom.xml` add
  `junit-jupiter-engine` in the test scope (points 1 and 2)
- `util/src/test/java`, `maven/src/test/java`, and `shell/src/test/java` – carry test programs, that is
  classes with `@Test` methods; the base classes `org.exlp.test.AbstractExlpTest` (`util`),
  `org.exlp.test.AbstractExlpShellTest` (`shell`), and `de.kisner.exlp.test.AbstractExlpMavenTest`
  (`maven`) use JUnit 5 as well (points 1 to 3)
