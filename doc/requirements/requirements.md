# Requirements Rules

These rules define the requirements for an application's functional, technical, and quality characteristics.

Each requirement is maintained in its own Markdown file. These rules are solely the working instructions; the **Index File** maintains the index (`## Terms`). The complete description of a requirement is in its own file.

These rules describe **rules and formats**, not the existing state: specific requirements and values do **not** appear here; file names appear here **only** where they are the same in **every** project; **this list is closed** (`## Requirement Types`, `## Related Project Documentation`). The **Index File** states which requirements exist; each requirement's file states what it specifies.

These rules and their **visible** wording—the section headings, labels such as `None.`, `Question:`, and `Applies in:`, as well as the automated-check patterns—are written in the **document language** (`## Terms`, `README.md`); in a project with a different document language, their translated forms take their place.

These rules are **project-independent**: when adopting them in another project, keep the names in `## Requirement Types` and `## Related Project Documentation` unchanged, and use the **document language** of the receiving project for visible wording.

These rules contain **no** project, product, device, or tool names; **no** technology or version details; **no** dates; and **no** paths outside the two closed lists (`## Requirement Types`, `## Related Project Documentation`). Examples use **fictional** names and numbers and do not describe existing entries.

**Identifiers** are outside the document language and remain **unchanged**: the YAML keys `id`, `title`, `type`, `status`, `priority`, `depends_on`, and `related`; the values of `status`, `priority`, and `type`; the prefixes `FR`, `NFR`, `SEC`, `UX`, and `CON` and the IDs (`AC-<ID>-NN`); and the keywords `Given`, `When`, and `Then`.

## Terms

The rules refer to directories and files by their roles. Each project has exactly one directory or file for each role; their names are in the **Project Overview**. The **Project Instructions** refer to them and do **not** repeat their names—except for names that are the same in **every** project and appear in `## Requirement Types` and `## Related Project Documentation`; **these lists are closed**.

| Role | Meaning |
|---|---|
| Project Overview | File containing the project goal, current status, and starting point for people |
| Project Instructions | Files containing the rules and working practices for the AI assistant; only `docs/llm.md` has a fixed name (`## Related Project Documentation`) |
| AI tool rule files | Files containing the AI tool's rules; their names and locations are project-dependent and specified in the **Project Overview** |
| Requirements Directory | Directory containing the requirement files in subdirectories for each category |
| Index File | File listing each requirement and each architectural decision on one line with its ID, title, and status; requirements also include their priority |
| Change Log | File with one heading per change, followed by body text, newest first (target sizes: `### Measurement Rules`) |
| Decision Repository | Directory of justified architecture and technology decisions |
| Architecture File | File containing the currently applicable technical architecture |
| Requirements Rules | File containing the rules and formats for requirements |
| Commit Message Rule | File defining the format of commit messages |
| Build Tools | Location of tools and scripts that build the project or generate files |
| Working Directory | Unversioned directory for logs, measurements, and intermediate results |
| Staging Directory | Unversioned directory for requests and drafts of **future** changes |
| Document Language | Language in which the project documents are written; the value is project-dependent and specified in the **Project Overview** |

## Purpose of the Requirements Rules

Requirements should:

- describe the application's expected behavior in a traceable way
- be understandable independently of individual implementation details
- contain clear acceptance criteria
- be versioned in Git and historically traceable
- serve as the basis for implementation, tests, and reviews
- provide the AI assistant with a reliable functional foundation

The repository is the lasting source of truth for requirements. Decisions made only in a chat are not binding until documented in the relevant project files.

## Requirement Types

Each requirement is placed in the subdirectory for its category within the **Requirements Directory** (`## Terms`). A directory is created only when the first requirement in that category is added; an empty category is not created.

| Prefix | Category | Directory | Meaning |
|---|---|---|---|
| `FR` | Functional Requirement | `functional/` | Functional behavior of the application |
| `NFR` | Non-functional Requirement | `non-functional/` | Quality, operational, or technical characteristic |
| `SEC` | Security Requirement | `security/` | Security and protection requirement |
| `UX` | Usability Requirement | `usability/` | Usability, presentation, and comprehensibility |
| `CON` | Constraint | `constraints/` | Binding constraint or restriction |

### Functional Requirements: `FR`

Functional requirements describe what the application should do.

Examples:

- report a state in the output
- obtain data from an external source
- start a program on the target platform
- restore the connection after an outage

### Non-functional Requirements: `NFR`

Non-functional requirements describe how the application should work.

Examples:

- long-running operations must not block interaction
- the refresh interval must be configurable
- errors must be traceable through the log output

### Security Requirements: `SEC`

Security requirements describe how credentials, private data, and permissions are handled.

Examples:

- credentials must not be stored in source code
- tokens must not be logged
- sensitive configuration values must be stored externally

### Usability Requirements: `UX`

Usability requirements describe comprehensibility and behavior from the user's perspective.

Examples:

- a faulty state must be recognizable in the output
- error messages must identify the affected location
- the output must be understandable without prior knowledge of the application

### Constraints: `CON`

Constraints describe requirements that every solution must satisfy.

They specify **project-wide, cross-requirement** decisions: structure and interfaces, platform and toolchain, as well as **representation and transport**—for example, media type, encoding, and the form in which time or error information is transmitted. They do **not** determine meaning: the responsible requirement specifies *which* values apply, *which* selection is made, and *which* unit applies.

A decision belongs here if a **second, different** requirement would also need to comply with it; if it applies to only one requirement, it belongs in that requirement.

Examples:

- the application runs on the specified target platform
- the language and framework are specified in a requirement
- a database must not be used

## Status Values

| Status | Meaning |
|---|---|
| `draft` | Initial idea or incomplete draft |
| `proposed` | Fully drafted but not yet approved from a functional perspective |
| `approved` | Approved from a functional perspective and ready for implementation |
| `in-progress` | Implementation has begun |
| `implemented` | Implemented, but not yet fully verified |
| `verified` | Implemented and confirmed by appropriate checks or tests |
| `deprecated` | No longer valid; the requirement is retained for traceability |

The values are **identifiers** and are **not** translated.

A new requirement normally starts with the status `draft` or `proposed`; if it records **existing implementation**, it receives the status of that implementation (`### Recording Existing Implementation`). The AI assistant may draft a requirement proposal, but approval from a functional perspective is the responsibility of the responsible person.

### Recording Existing Implementation

Behavior that has **already been implemented** before a requirement documents it is recorded **retroactively**. Recording it follows the same process as a new requirement; the only difference is the order: the implementation already exists, and the requirement documents it.

- The status is the one carried by the **existing** implementation: `implemented` if verification is missing, `verified` if verification exists. The values `draft`, `proposed`, and `approved` belong to the process that starts with a draft.
- Recording it requires approval from the responsible person, as with any other requirement; the AI assistant prepares it, and the status follows **only** their instruction (`### Approval Review and Finding Classes`).
- The **Evidence** section names the existing location and its result; the `open:` notation is omitted because the implementation predates the recording (`## Implementation and Evidence`).
- Changes to a requirement that records existing implementation follow `## Changes to Requirements` without alteration.

A requirement that records existing implementation describes only the **current applicable state**: the former state is available through Git, and any statement about the act of recording it is omitted (`### History and Current State`, `### No Meta-text`).

## Priorities

| Priority | Meaning |
|---|---|
| `must` | Essential for the current version |
| `should` | Important, but not essential for the MVP |
| `could` | Desirable if effort and time allow |
| `wont` | Deliberately excluded from the current version |

The values are **identifiers** and are **not** translated.

Priority describes importance for the current product version. It does not indicate technical effort.

## File Names

Requirement files use this format:
```text
<ID>-<short-kebab-case-name>.md