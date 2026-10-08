# Project Instructions for the AI Assistant

This file is **project-independent**: it applies in the same way to **every** project. Information about an individual project — its goal and status, **document language**, technologies, values, credentials, generated files, the names and location of the AI tool's rule files, and the names of the other project-dependent roles (**Commit Message Rule**, **Build Tools**) — belongs not here, but in the **Project Overview** (`README.md`). Therefore, when transferring this file to another project, **nothing** needs to be replaced, except for the literally named section headings, role terms, and headings that follow the **document language** of the receiving project.

Literally named section headings, role terms, and headings (such as `## File Names`, `## File Names and IDs`, or **Project Overview**) are written in the project's **document language**; in a project with a different document language, their translated forms take their place.

This document is itself a project document and is therefore written in the **document language**; its value is specified in the **Project Overview** (`README.md`).

## Project Context

Read `README.md` first, before suggesting changes; it contains the project information.

The functional requirements are located under `doc/requirements/`. Each requirement is in its own file; the rules and formats for those files are defined in the **Requirements Rules** (`doc/requirements/requirements.md`).

## Workflow

- For new features, read the relevant requirement first.
- Whether a change requires a **separate requirement file** is determined by the **Requirements Rules** (`doc/requirements/requirements.md`, “Requirement or Follow-up Change”). Technical details without product significance are recorded as **assumptions** in the requirement and are **not** raised as questions.
- Behavior that has **already been implemented** is recorded as **existing implementation**: the requirement receives the status of the existing implementation, and its evidence names the existing location in the code (`doc/requirements/requirements.md`, “Recording Existing Implementation”).
- Ask when a product decision is missing.
- New files must have names **only in English**: kebab-case, ASCII, without umlauts or `ß`, and with no words in any language other than English. This applies to requirements (`## File Names` in the **Requirements Rules**), decisions (`## File Names and IDs` in the **Decision Repository**), documents and source code, as well as **tools and build tools**, including the files they create. Existing files with names in another language are **not** renamed automatically; rename them only on explicit request, and update all references to the old name in the same step.
- **Check the name before creating the file:** A file name specified by a requirement or a decision for a **future** file is **not** an exception—the text of a requirement or decision does not replace the check. When creating the file, check its name against the rule above and change it if necessary; this also applies when **implementing** a decision whose approval text specifies a file name. Report any violation to the responsible person with the **file and line**, and correct it in the same step, including references to the old name (`doc/changelog.md`).
- Do not invent an address, message format, protocol, configuration, credentials, or authentication setting.
- **Generated files:** A file created by a tool or external application may be subject to its own rule—for example, “never modify.” Such a rule is stated in the **AI tool's rule files** and applies **only** to the file named there and **only** to the AI assistant; it does **not** create a general rule for generated files. Report a need for changes to the responsible person with the **file and line**. The **Project Overview** specifies which file is affected and which rule applies.
- When changing multiple files, create a plan first.
- Make only clearly scoped changes.
- Do not perform unrelated refactoring.
- Search only within the project and documented paths; run every recursive command (`find`, `grep -r`, `ls -R`) with a depth limit and a time limit; do not run one from `/` or `~`. The details are in the AI tool's rule file for shell commands; its name is specified in the **Project Overview**.
- Cover new functionality with tests.
- An **approval review** ends with a **recommendation**—“Approval recommended” or “not recommended.” It provides **one complete list of findings in a single pass**, does not stop at the first finding, and each finding names the rule, file, line, and wording. **Form findings**—line width, section length, bullet points, number sequences, meta-text, pattern runs—are **notes** and do **not** prevent approval; a recommendation against approval is warranted only for an **incomplete or incomprehensible** specification and for **unanswered questions**. After implementation, perform **acceptance against this list**, **not** a new review; unchanged text produces **no** new finding, and if in doubt, a finding is a **note**. The details are in the **Requirements Rules** (`doc/requirements/requirements.md`, “Approval Review and Finding Classes,” “Acceptance After Implementation”); the decision about a note and its outcome are recorded in the change log and bind every later review. The recommendation is not binding on the responsible person.
- After changes, run tests and check the Git diff.
- A commit is **never** created automatically; it requires **manual interaction** by the responsible person (`doc/requirements/requirements.md`, “Git Rules”).

## Architectural Principles

- External components and libraries are allowed, but each requires an architectural decision in `doc/decisions/`; they may be used only once the **Decision Repository** gives them the status `accepted` (status values are **identifiers** and are **not** translated).
- Do not implement anything whose requirement has not been approved (`doc/requirements/requirements.md`, “Define a New Requirement,” “Implement a Requirement”).
- The platform, language, framework, build environment, pinned versions, and **source of truth** for values, credentials, and generated files are specified in the **constraints** or the **Decision Repository**; the **Architecture File** and **Project Overview** are **display locations** that identify the authoritative source. This document does not repeat them.

## Documentation

- Requirements: `doc/requirements/`
- Constraints: `doc/requirements/constraints/`
- Requirements index (index only): `doc/status.md`
- Change log: `doc/changelog.md`
- AI assistant rules: this document (**project-independent**) and the **AI tool's rule files**; their names, locations, and subjects are specified in the **Project Overview**
- Architectural decisions: `doc/decisions/` (index: `doc/status.md`)
- Technical architecture: `doc/requirements/architecture.md`
- The paths listed above are among the names that are the same in **every** project (closed list: `## Requirement Types` and `## Related Project Documentation` in the **Requirements Rules**); a project with different names cannot use this document unchanged.
- Changes to behavior must be documented.
- Paths are given relative to the repository root (`doc/...`). References within a file are relative to that file's own location; therefore, the **Index File** links to its entries without the leading `doc/`.