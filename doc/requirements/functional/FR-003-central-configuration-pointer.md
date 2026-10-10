---
id: FR-003
title: Resolve configuration files through a central pointer file
type: functional
status: implemented
priority: should
depends_on: []
related: []
---

# FR-003: Resolve configuration files through a central pointer file

## Requirement

The utility of the `util` module resolves the configuration file of an application for a configuration
code through a central pointer file (AC-FR-003-01).

- The caller names the application through an application code given when the utility is created
  (AC-FR-003-01).
- The caller names the configuration through a configuration code given to the resolution (AC-FR-003-01).
- The application code is accepted as an enumeration constant or as a string (AC-FR-003-02).
- Without an explicitly given pointer file, the utility uses the default location (Assumption 1).
- An explicitly given pointer file replaces the default location (AC-FR-003-03).
- The pointer file is an XML document with one `<dir>` element per application code and one `<file>`
  element per configuration code (AC-FR-003-04).
- A `<file>` element names the configuration file that belongs to its code (AC-FR-003-01).
- The resolution returns the configuration file as `java.io.File` (AC-FR-003-05).
- The resolution returns the configuration file as `java.nio.file.Path` (AC-FR-003-06).
- A missing pointer file is created with a dummy application entry and a dummy configuration entry
  before the resolution fails (AC-FR-003-07).
- A missing application entry is appended as a dummy application entry before the resolution fails
  (AC-FR-003-08).
- The resolution fails with `ExlpConfigurationException` when the configuration code is absent from the
  application entry (AC-FR-003-09).
- The resolution fails with `ExlpConfigurationException` when the named configuration file does not
  exist (AC-FR-003-10).
- The resolution fails with `ExlpConfigurationException` when an application code or a configuration
  code is not unique in the pointer file (AC-FR-003-11).
- The path variant of the resolution yields `null` when the resolution fails (AC-FR-003-12).

## Rationale

A CLI class needs its configuration at every invocation, and a property per invocation in the
development environment is error-prone; a pointer file per user and machine names the configuration
once. The pointer file is an XML document, so the existing JAXB tooling of the project reads and writes
it (Assumption 2). A dummy entry with a message that names the location guides a developer to the
missing configuration.

## Scope

In scope:

- The resolution of a configuration file from an application code and a configuration code.
- The default location and the structure of the pointer file.
- The creation of the pointer file and its entries.
- The error behavior of the resolution.

Out of scope:

- The content and the format of the resolved configuration file.
- The interpretation of configuration values (`ConfigKey`, `Configuration`, `Profile`).
- The combination of several configurations into one (`ConfigLoader`).
- The graphical management of the pointer file (`client` tray).

## Assumptions

1. The default pointer file is `$HOME/.m2/exlp.xml`, with `$HOME` taken from the system property
   `user.home`.
2. The pointer file is an XML document of the JAXB model `org.exlp.model.xml.io.Dir` with `File`
   children; the resolution reads and writes it with the JAXB utility of the project.
3. A dummy entry names the file `/change/me`.

## Open Questions

None.

## Decided Questions

None.

## Acceptance Criteria

### AC-FR-003-01: Resolve the configuration file of an application and configuration code

Given:

- A pointer file with an application entry for the application code.
- The application entry holds a configuration entry that names an existing configuration file.

When:

- The resolution runs for the application code and the configuration code.

Then:

- The resolution returns that configuration file.

### AC-FR-003-02: Accept an enumeration constant and a string as the application code

Given:

- A pointer file with an application entry whose code equals an enumeration constant.

When:

- The utility is created from the enumeration constant and from the string of that constant, and each
  resolution runs.

Then:

- Both resolutions return the same configuration file.

### AC-FR-003-03: Replace the default location with an explicitly given pointer file

Given:

- A pointer file at a location other than the default location.

When:

- The utility is created with that pointer file and the resolution runs.

Then:

- The resolution returns the configuration file that the given pointer file names for the code.

### AC-FR-003-04: Select the entries through the application code and the configuration code

Given:

- A pointer file with two application entries and two configuration entries per application entry.

When:

- The resolution runs for one application code and one configuration code.

Then:

- The resolution returns the configuration file of the named configuration entry.

### AC-FR-003-05: Return the configuration file as a file

Given:

- A resolvable configuration code.

When:

- The file variant of the resolution runs.

Then:

- The result is a `java.io.File` whose path is the named configuration file.

### AC-FR-003-06: Return the configuration file as a path

Given:

- A resolvable configuration code.

When:

- The path variant of the resolution runs.

Then:

- The result is a `java.nio.file.Path` equal to the path of the named configuration file.

### AC-FR-003-07: Create a dummy pointer file when it is missing

Given:

- No pointer file at the used location.

When:

- The resolution runs.

Then:

- The resolution fails with `ExlpConfigurationException`.
- The pointer file exists afterwards with a dummy application entry and a dummy configuration entry.

### AC-FR-003-08: Append a dummy application entry when the application code is missing

Given:

- A pointer file without an entry for the application code.

When:

- The resolution runs.

Then:

- The resolution fails with `ExlpConfigurationException`.
- The pointer file afterwards holds a dummy application entry for the application code with a dummy
  configuration entry.

### AC-FR-003-09: Fail when the configuration code is absent

Given:

- A pointer file with an application entry for the application code without the requested configuration
  code.

When:

- The resolution runs.

Then:

- The resolution fails with `ExlpConfigurationException`.

### AC-FR-003-10: Fail when the named configuration file does not exist

Given:

- A configuration entry for the configuration code whose named file does not exist.

When:

- The resolution runs.

Then:

- The resolution fails with `ExlpConfigurationException`.
- The message states `does not exist for app=<application code> code=<configuration code>`.

### AC-FR-003-11: Fail for a non-unique application code or configuration code

Given:

- A pointer file with two application entries of the same code, or two configuration entries of the
  same code.

When:

- The resolution runs.

Then:

- The resolution fails with `ExlpConfigurationException`.

### AC-FR-003-12: Yield null in the path variant when the resolution fails

Given:

- The resolution fails for the configuration code.

When:

- The path variant runs.

Then:

- The result is `null`.

## Evidence

### Implementation

- `util/src/main/java/org/exlp/util/io/config/ExlpCentralConfigPointer.java` – resolves the configuration
  file, uses the default location, creates dummy entries, and fails with `ExlpConfigurationException`
  (AC-FR-003-01, AC-FR-003-03, AC-FR-003-05, AC-FR-003-06, AC-FR-003-07, AC-FR-003-08, AC-FR-003-09,
  AC-FR-003-10, AC-FR-003-11, AC-FR-003-12)
- `util/src/main/java/org/exlp/util/io/config/ExlpCentralConfigPointer.java` – `instance(E)` and
  `instance(String)` accept the application code as an enumeration constant and as a string
  (AC-FR-003-02)
- `util/src/main/java/org/exlp/util/query/xpath/IoXpath.java` – selects the `<dir>` and `<file>` elements
  by their code (AC-FR-003-04)
- `util/src/main/java/org/exlp/util/io/config/ExlpCentralConfigPointer.java` – reads and writes the XML
  pointer file through the `Dir`/`File` models (Assumption 2)

### Tests

- `util/src/test/java/org/exlp/util/xpath/TestIoXpathDir.java` and
  `util/src/test/java/org/exlp/util/xpath/TestIoXpathFile.java` – select an element by its code among
  several entries (AC-FR-003-04)
- `util/src/test/java/org/exlp/util/config/TestExlpCentralConfigPointer.java` – `resolvesConfiguredFile`
  returns the named file (AC-FR-003-01, AC-FR-003-05)
- `util/src/test/java/org/exlp/util/config/TestExlpCentralConfigPointer.java` – `instanceUsesEnumAppCode`
  and `instanceUsesStringAppCode` cover both forms of the application code (AC-FR-003-02)
- `util/src/test/java/org/exlp/util/config/TestExlpCentralConfigPointer.java` – every test creates the
  utility with an explicitly given pointer file (AC-FR-003-03)
- `util/src/test/java/org/exlp/util/config/TestExlpCentralConfigPointer.java` – `toPathReturnsConfiguredPath`
  returns the named path (AC-FR-003-06)
- `util/src/test/java/org/exlp/util/config/TestExlpCentralConfigPointer.java` – `createsPointerWhenMissing`
  asserts the dummy pointer file and the failure (AC-FR-003-07)
- `util/src/test/java/org/exlp/util/config/TestExlpCentralConfigPointer.java` – `appendsDummyDirWhenAppMissing`
  asserts the appended dummy application entry and the failure (AC-FR-003-08)
- `util/src/test/java/org/exlp/util/config/TestExlpCentralConfigPointer.java` – `failsWhenFileCodeMissing`
  asserts the failure for an absent configuration code (AC-FR-003-09)
- `util/src/test/java/org/exlp/util/config/TestExlpCentralConfigPointer.java` –
  `failsWhenReferencedFileDoesNotExist` asserts the message with the application code and the
  configuration code (AC-FR-003-10)
- `util/src/test/java/org/exlp/util/config/TestExlpCentralConfigPointer.java` – `failsWhenAppCodeNotUnique`
  and `failsWhenFileCodeNotUnique` assert the failure for a non-unique code (AC-FR-003-11)
- `util/src/test/java/org/exlp/util/config/TestExlpCentralConfigPointer.java` – `toPathReturnsNullWhenNotAvailable`
  asserts `null` (AC-FR-003-12)
- `mvn -o -pl util -am clean test` – `TestExlpCentralConfigPointer` runs 11 tests without failure
  (AC-FR-003-01)
