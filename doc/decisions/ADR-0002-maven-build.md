---
id: ADR-0002
title: Maven as build tool
status: accepted
date: 2026-10-11
affects: []
---

# ADR-0002: Maven as build tool

## Context

The repository is a multi-module Java project whose modules, dependencies, and build extensions are to
be built in one run. The open question was which build tool builds the modules, resolves the
dependencies, and binds the extensions of the BOM and the target platform.

## Decision

1. Maven builds the project; the root `net.sf.exlp:exlp` with packaging `pom` leads the modules `bom`,
   `client`, `core`, `doc`, `ejb`, `interfaces`, `maven`, `shell`, `util`, `test`, and `xml` in one
   reactor.
2. The packagings are `pom` for `bom` and `xml`, `maven-plugin` for `maven`, and `jar` for the remaining
   modules.
3. The dependency versions come from the BOM `bom/pom.xml` (`net.sf.exlp:exlp-bom`), which every module
   imports with `<scope>import</scope>`.
4. Maven enforces selected dependencies through `maven-enforcer-plugin` and, in the `release` profile,
   creates the documentation through `maven-javadoc-plugin`.
5. The build requires Maven 3.6.3 or newer.

## Rationale

- The repository is a Maven multi-module build, and the BOM and the packaging types are Maven artifacts.
- The packaging `maven-plugin` maps the build tooling of the `maven` module to a Maven plugin.
- The dependency versions live in one BOM, so no module repeats a version.
- The enforcer rule keeps a banned dependency out of the reactor, and the javadoc plugin documents the
  artifacts of a release.
- The bound `maven-surefire-plugin` 3.6.0 (ADR-0003) needs Maven 3.6.3 or newer, which sets the required
  version of the build.

## Alternatives

- Another build tool (Gradle) – not selected; the BOM, the Maven plugin, and the packaging types are laid
  out for Maven.

## Impact

- Affected requirements: none.
- Files: `pom.xml` and the module POMs of the named modules.

## Open Points

None.

## Evidence

### Implementation

- `pom.xml` – packaging `pom`, root `net.sf.exlp:exlp`, eleven `<module>` entries
- `pom.xml` – `maven-enforcer-plugin` with `bannedDependencies` and `maven-javadoc-plugin` in the
  `release` profile
- `bom/pom.xml` – packaging `pom`, manages the dependency versions for all modules; the modules import it
- `xml/pom.xml` and `maven/pom.xml` – packagings `pom` and `maven-plugin`

### Documentation

- `README.md` (`## Build and Start`) – names Maven 3.6.3 or newer (display location)
- `doc/status.md` – lists this decision
