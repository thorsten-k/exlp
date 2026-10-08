# Current Index

The index lists every requirement and every architecture decision in one line each: the ID with a link to the file,
the title, and the status; for a requirement, additionally the priority.

- The source of the values is the YAML header of the respective file under `doc/requirements/` or `doc/decisions/`;
  the status appears nowhere else.
- The course of the activities is in `doc/changelog.md`; structure and maintenance are governed by
  `doc/requirements/requirements.md`.


## Functional Requirements

| ID | Title | Status | Priority |
|---|---|---|---|
| [FR-001](requirements/functional/FR-001-reachability-metadata.md) | Generate annotation-based Native Image reachability metadata | implemented | must |



## Non-functional Requirements

| ID | Title | Status | Priority |
|---|---|---|---|


## Security Requirements

| ID | Title | Status | Priority |
|---|---|---|---|


## Usability Requirements

| ID | Title | Status | Priority |
|---|---|---|---|


## Constraints

| ID | Title | Status | Priority |
|---|---|---|---|



## Architecture Decisions

| ID | Title | Status | Affects |
|---|---|---|---|
| [ADR-0001](decisions/ADR-0001-scan-module-classes-with-classgraph.md) | Scan module classes with ClassGraph | accepted | FR-001 |
| [ADR-0002](decisions/ADR-0002-package-javax-jakarta-variants.md) | Build and publish the JAXB variants as classifier artifacts | accepted | FR-001 |

