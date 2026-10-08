# Requirements Rulebook

This rulebook contains the rules for an application's functional, technical, and quality requirements.

Each requirement is maintained in its own Markdown file. This rulebook is exclusively the working guide; the **index file** (`## Terms`) maintains the index. The complete description of a requirement is in its respective file.

This rulebook describes **rules and forms**, not the inventory: specific requirements and values are **not** listed here; filenames appear here **only** where they are the same in **every** project; **these lists are closed** (`## Requirement Types`, `## Related Project Documentation`). Which requirements exist is stated in the **index file**; what a requirement specifies is stated in its file.

This rulebook and its **visible** wording—the section names, labels such as `None.`, `Question:`, and `Applies in:`, as well as the patterns for automated checks—are in the **document language** (`## Terms`, `README.md`); in a project with a different document language, the translated forms replace them.

This rulebook is **project-independent**: when adopting it in another project, the names in `## Requirement Types` and `## Related Project Documentation` must be carried over unchanged, and the visible wording follows the **document language** of the adopting project.

It includes **no** project, product, device, or tool names, **no** technology or version information, **no** dates, and **no** paths outside the two closed lists (`## Requirement Types`, `## Related Project Documentation`); its examples use **fictional** names and numbers and do not refer to an item in the inventory.

**Identifiers** are **outside** the document language and remain **unchanged**: the YAML keys `id`, `title`, `type`, `status`, `priority`, `depends_on`, and `related`; the values of `status`, `priority`, and `type`; the prefixes `FR`, `NFR`, `SEC`, `UX`, and `CON` together with the IDs (`AC-<ID>-NN`); and the keywords `Given`, `When`, and `Then`.

## Terms

The rules refer to repositories and files by their role. Each project has exactly one repository or file for each role; their names are given in the **project overview**; the **project instructions** refer to them and do **not** repeat their names—with the exception of names that are the same in **every** project and appear in `## Requirement Types` and `## Related Project Documentation`; **these lists are closed**.

| Role | Meaning |
|---|---|
| Project overview | File containing the project objective, current status, and entry point for people |
| Project instructions | Files containing the rules and working practices for the AI assistant; the only fixed name is `doc/llm.md` (`## Related Project Documentation`) |
| AI tool rule files | Files containing the rules for the AI tool; their name and location are project-specific and given in the **project overview** |
| Requirements repository | Directory in which requirement files are stored in subdirectories by category |
| Index file | File listing each requirement and each architecture decision on one line with its ID, title, and status; for requirements, also the priority |
| Activity log file | File with a heading for each activity, including its date, followed by body text, newest first (targets: `### Measurement Rules`) |
| Decision repository | Directory for reasoned architecture and technology decisions |
| Architecture file | File containing the currently applicable technical architecture |
| Rulebook | File containing the rules and forms for requirements |
| Commit rule | File specifying the format of commit messages |
| Build tools | Repository of tools and scripts that perform builds or generate files |
| Working repository | Unversioned directory for logs, measurements, and intermediate results |
| Staging repository | Unversioned directory for requests and drafts of **future** changes |
| Document language | Language in which the project documents are written; the value is project-specific and is set in the **project overview** |

## Purpose of the Rulebook

Requirements should:

- describe the application's expected behavior in a traceable way
- be understandable independently of individual implementation details
- contain unambiguous acceptance criteria
- be versioned in Git and historically traceable
- serve as the basis for implementation, testing, and reviews
- provide the AI assistant with a reliable functional foundation

The repository is the permanent source of requirements. Decisions made only in a chat become binding only once documented in the appropriate project files.

## Requirement Types

Each requirement is stored in the subdirectory for its category within the **requirements repository** (`## Terms`). A directory is created only when the first requirement of that category is added; an empty category is not created.

| Prefix | Category | Repository | Meaning |
|---|---|---|---|
| `FR` | Functional Requirement | `functional/` | Functional behavior of the application |
| `NFR` | Non-functional Requirement | `non-functional/` | Quality, operational, or technical property |
| `SEC` | Security Requirement | `security/` | Security and protection requirement |
| `UX` | Usability Requirement | `usability/` | Usability, presentation, and clarity |
| `CON` | Constraint | `constraints/` | Binding boundary condition or restriction |

### Functional Requirements: `FR`

Functional requirements describe what the application should do.

Examples:

- report a state in the output
- obtain data from an external source
- start a program on the target platform
- restore the connection after an outage

### Non-functional Requirements: `NFR`

Non-functional requirements describe how the application should operate.

Examples:

- long-running accesses must not block operation
- the refresh interval must be configurable
- errors must be traceable through the log output

### Security Requirements: `SEC`

Security requirements describe the handling of credentials, private data, and permissions.

Examples:

- credentials must not be included in source code
- tokens must not be logged
- sensitive configuration values must be stored externally

### Usability Requirements: `UX`

Usability requirements describe clarity and behavior from the user's perspective.

Examples:

- a fault state must be recognizable in the output
- error messages must identify the affected location
- the output must be understandable without prior knowledge of the application

### Constraints: `CON`

Constraints describe requirements that must be observed by all solutions.

They record **project-wide, cross-requirement** provisions: structure and interface, platform and toolchain, as well as **presentation and transport**—for example, media type, encoding, and the form in which time or error information is transmitted. They do **not** define meaning: *which* values apply, *which* choice is made, and *which* unit applies are specified by the responsible requirement.

A provision belongs here if a **second, different** requirement would also have to comply with it; if it applies to only one requirement, it belongs in that requirement.

Examples:

- the application runs on the specified target platform
- language and framework are specified in a requirement
- a database must not be used

## Status Values

| Status | Meaning |
|---|---|
| `draft` | Initial idea or incomplete draft |
| `proposed` | Fully formulated, but not yet approved from a functional perspective |
| `approved` | Approved from a functional perspective and ready for implementation |
| `in-progress` | Implementation has started |
| `implemented` | Implemented, but not yet fully checked |
| `verified` | Implemented and confirmed by an appropriate check or test |
| `deprecated` | No longer valid; the requirement is retained for traceability |

The values are **identifiers** and are **not** translated.

A new requirement normally starts with the status `draft` or `proposed`; if it records an **existing implementation**, it carries the status of that implementation (`### Recording Existing Implementation`). The AI assistant may draft a requirement proposal, but functional approval is given by the responsible person.

### Recording Existing Implementation

Behavior that is **already implemented** before a requirement documents it is **recorded retroactively**. Recording it follows the same process as a new requirement; the **only** difference is the sequence: the implementation already exists, and the requirement records it.

- The status is that of the **existing** implementation: `implemented` if verification is missing, `verified` if verification exists. The values `draft`, `proposed`, and `approved` belong to the process that begins with a draft.
- Recording requires **approval** by the responsible person, like any other requirement; the AI assistant prepares it, and the status follows **only** their instruction (`### Approval Review and Finding Classes`).
- The **evidence** names the existing location and its result; the entry `open:` is omitted because the implementation predates the record (`## Implementation and Evidence`).
- Changes to a recorded requirement follow `## Changes to Requirements` without modification.

A recorded requirement describes only the **currently applicable state**: the previous state is accessible through Git, and any statement about the act of recording is omitted (`### History and Current State`, `### No Meta-Text`).

## Priorities

| Priority | Meaning |
|---|---|
| `must` | Essential for the current version |
| `should` | Important, but not essential for the MVP |
| `could` | Desirable if effort and time allow |
| `wont` | Deliberately not part of the current version |

The values are **identifiers** and are **not** translated.

Priority describes importance for the current product version. It is not a statement about technical effort.

## Filenames

Requirement files use this format:

```text
<ID>-<short-kebab-case-name>.md
```

The name is formed **only in English**: kebab case, ASCII, without umlauts or `ß`, and without **words from a language other than English**. The same rule applies to decisions (**decision repository**, `### Filenames and IDs`), and for documents and source code it is stated in the **project instructions**.

The rule does **not** apply only to requirement files: it covers **every** newly created project file, including **tools and build tools** in their repository (`## Terms`) and the files that create them. If a requirement or a decision specifies a filename for a **future** file, that name must be checked against the rule—it is checked before the file is created and changed if necessary; the text of a requirement or decision does not replace the check; the activity is recorded in the **activity log file**.

Examples of the naming form (fictional names, not an inventory):

```text
FR-042-output-values.md
NFR-042-update-interval.md
```

The filename should be short and unambiguous. The ID in the filename must match the ID in the file's metadata block.

If an existing file has a name in a language other than English, it remains **unchanged**; it is renamed only on explicit request. For such a request, the rule applies to the **new** name, and all references to the old name are updated in the same step.

## IDs

- Each requirement receives a unique ID.
- IDs that have already been used are never reused.
- The ID remains unchanged if the title changes.
- A requirement does not receive a new ID even if it is refined functionally.
- If a requirement is removed, it receives the status `deprecated`.
- A new category starts with number `001`.
- Gaps in numbering are allowed and are not filled retroactively.

Examples of the form (fictional numbers, not an inventory):

```text
FR-042
NFR-042
SEC-042
UX-042
CON-042
```

The ID is the stable reference for Git commits, tests, architecture decisions, issues, and pull requests.

## Contents of a Requirement File

Each requirement file contains **exactly** these sections in this order:

1. YAML header (`### Metadata`)
2. `# <ID>: <Title>`
3. `## Requirement`
4. `## Rationale`
5. `## Scope` (optional)—in the form “In scope: …” and “Out of scope: …” (target: `### Measurement Rules`)
6. `## Assumptions` (optional)—technical details without product significance (`### Assumptions`)
7. `## Open Questions`
8. `## Decided Questions`
9. `## Acceptance Criteria`
10. `## Dependencies`
11. `## Evidence`

Additional sections may be added **only** by changing this rule, not in an individual file. **Subsections** (`###`) within one of the listed sections are permitted and count as part of that section.

An impact on another requirement belongs in its `## Evidence`; a contradiction is an **Open Question**.

A section with no content is **deleted**; `None.` appears alone only under `## Open Questions` and `## Decided Questions`, and in that case **only** that word appears there. An unresolved placeholder must not remain in an approved requirement.

### Metadata

Each requirement file begins with a YAML block:

```yaml
---
id: FR-XXX
title: Short, unambiguous title
type: functional
status: proposed
priority: must
depends_on: []
related: []
---
```

The field names are **identifiers** and are **not** translated; the same applies to the values of `status`, `priority`, and `type` (`## Status Values`, `## Priorities`).

Fields used:

| Field | Required | Meaning |
|---|---|---|
| `id` | Yes | Unique requirement ID |
| `title` | Yes | Short, unambiguous title |
| `type` | Yes | Requirement type without a prefix, for example `functional` |
| `status` | Yes | Current processing status |
| `priority` | Yes | Priority for the current version |
| `depends_on` | No | Requirements that must be met first |
| `related` | No | Requirements related in subject matter |

The requirement's **attributes**—requirement type, status, and priority—are **meta-information** about the requirement and are **not** product provisions: they belong in the **YAML header** and in the **index file** table (`## Terms`).

- **No attribute in the requirement text:** Neither `## Requirement`, `## Scope`, nor an acceptance criterion states the status, priority, or requirement type; a bullet that merely repeats an attribute is **not** a factual statement and is omitted (`### Requirement and Scope`, `### No Meta-Text`).
- **Decision about an attribute:** If a Decided Question concerns an attribute, the **index file** is the location of the applicability line (`### Applicability Line`).

### History and Current State

A requirement file describes **only the currently applicable state**. It contains **no** change history. The following are not permitted:

- a block quote at the beginning of the file containing dates (“Recorded on …”, “Approved on …”, “Implemented on …”),
- sections or sentences that record an activity (“Note on …”, “The previous state was …”, “The only thing that remains open is …”, “→ Implemented on …”),
- sentences about a **previous** implementation; the previous state is accessible through Git.

The same boundary applies to the **architecture file**; details are set out under `### Architecture File`.

History is maintained by **Git**, including commit messages. Activities are recorded in the **activity log file** (`## Terms`): a heading `## <Date> – <Short Description>` followed by body text, **newest first**; targets are set out under `### Measurement Rules`.

**Open change:** The **topmost** entry in the **activity log file** is the **open** change—the activity since the last commit. It summarizes **all** actions and AI interactions up to the next commit; another interaction **updates** this entry and does **not** create a new one. This creates **one** entry per change, and the commit closes it.

**Body:** Body lines name, in order, `What:`, `Result:`, `Evidence:`, and `Files:`; a line with no statement is **omitted**. A body line makes **one** statement and may wrap across additional lines; an activity remains **concise**, and an activity that exceeds the targets in `### Measurement Rules` is **shortened**.

An entry records **only** the activity and its result; a **currently applicable provision** is recorded in the responsible requirement, the **decision repository**, or the **architecture file**, and an existing anchor in an entry is retained.

The entry does **not** retell the **work** or explain it:

- **No rationale:** Intent, cause, and process are omitted; “because,” “so that,” “for,” “therefore,” and “cause” do **not** appear in the entry.
- **No measurement series:** `Evidence:` states **only** the result of the check—“`pio run` `[SUCCESS]`,” “test run 5/5,” “no matches found in sample”—not duration or pixel or memory values; measurements and logs belong in the **working repository**.
- **No repeated value:** A value from a requirement is **not** repeated here; it belongs at its authoritative location.
- **No narrated sentence:** The individual changed sentences, proposal numbers, and wording of a rule belong in the Git diff and commit message, **not** here.

References to the history name **only** the **activity log file**, not an individual entry.

The **index file** is a **pure index**: an introduction of no more than ten lines and tables with ID, title, and status—and, for requirements, also priority. It contains **no** prose about individual requirements or decisions and **no** chronology. Status appears in the YAML header of each file and in the index table—**nowhere else**.

The **status** of a requirement changes only with approval and is **not** narrated: it appears in the YAML header and the index table.

### No Meta-Text

A requirement file describes **the product**. Statements about the file itself or the work on it are omitted:

- the **processing status**—“all questions have been decided,” “the section says `None.`,“ “a split has been proposed”—,
- counts relating to **processing status**—“eight questions listed,” “eleven questions decided”—,
- the requirement's **attributes**—requirement type, status, and priority stated in the text (`### Metadata`)—,
- dates, respondents, and activities—“with the answer on …”, “in the meantime”, “the changes were …”—,
- parenthetical additions to a question's topic that narrate its **history**—“(formerly Open Question 3)”, “(wording and number unchanged)”, “(fully decided)”, “(decided)”—; the **only** permitted addition is `(partially decided)` (`### Entry Form for a Question`).
- references to this rulebook; its rules apply in any case,
- references to another requirement that serve **only** as an example of a process—“follow the process in …”.

What **applies** is stated in the file; what **happened** is stated in the **activity log file**. A statement that merely repeats these form rules is deleted. **One** exception remains: the single line for a target under `## Evidence`.

A statement about **another** project file—a header file, an output artifact, a build tool, or a repository—is a statement about the **product** and is **not** meta-text; meta-text consists only of statements about **this** file and the **work on it**. The **processing status** of a requirement—even another requirement—is not stated in the text: it is replaced by the **existing state** itself (“the requirement specifies the value”), never by the status. If the classification is disputed, the finding is a **note** (`### Approval Review and Finding Classes`).

The same applies to the **architecture file**: it too describes the product's currently applicable **structure**, not the work on its file (`### Architecture File`).

### Writing Style

- One statement is **one** sentence; targets for paragraphs and bullet points are set out under `### Measurement Rules`.
- Boldface is permitted for names, IDs, paths, and commands, **not** for emphasis in running text (“**fourteen**”, “**no**”).
- Use **one** reference per statement; resolve chains of references in parentheses.
- A statement about a file or the work on it is not a statement about the product (`### No Meta-Text`).
- A reference helps locate a provision; it is **not** an example of a process.
- References to other documents name the file and section (a decision in the **decision repository**, another requirement), but no dates.

### Requirement and Scope

`## Requirement` contains the **provisions**; `## Scope` delimits them.

- **One point, one fact:** Each bullet under `## Requirement` contains **one** fact and **one** location (`AC-…`, `## Scope`, `Decided Question <N>`, `Assumption <N>`, or another requirement).
- **Evidence line:** The location appears in parentheses at the end of the statement; listing several locations is omitted, and the effectiveness of a provision is checked, not listed.
- **Opening paragraph:** The paragraph under `## Requirement` states the requirement in **one** sentence together with **one** location; it does **not** repeat the bullet points.
- **No attributes:** A point contains no requirement type, status, or priority; these **attributes** belong in the YAML header and the **index file** (`### Metadata`).
- **Scope without a test statement:** `## Scope` states what is included and what is not; it contains **no** statement about how something is tested (`### One Fact, One Location`).
- **A provision without a location is a note**; report it to the responsible person with **file and line**.

### Assumptions

`## Assumptions` contains **technical detail provisions without product significance** that the AI assistant derives from the request, existing requirements, and constraints—for example, a name, value, path, or approach. They are **not** functional decisions and are not submitted to the responsible person as questions: they are stated as provisions and checked only for contradictions.

- **Distinction:** A **functional** decision—behavior, data selection, access, meaning, scope, or choice of values—is **not** an assumption; it is an **Open Question** (`## Open Questions`).
- **Form:** a numbered list; **one** fact per item on **one** line, with no introduction, subheading, or rationale.
- **Location:** A provision in `## Requirement` that relies on an assumption names it as its location (`Assumption <N>`, `### Requirement and Scope`).
- **Contradiction:** If the responsible person disagrees with an assumption, it is moved to **Open Questions** and removed from `## Assumptions`; the activity is recorded in the **activity log file**.
- **Do not invent:** An assumption follows from the request, a requirement, or a constraint; it does **not** invent an address, message format, protocol, configuration, credentials, or authentication setting (`doc/llm.md`, Working Practices section).
- **Approval:** Assumptions are **not** open questions and do not prevent approval; the review presents them to the responsible person as a list.

### Complete Template

```markdown
---
id: FR-XXX
title: Short, unambiguous title
type: functional
status: proposed
priority: must
depends_on: []
related: []
---

# FR-XXX: Short, unambiguous title

## Requirement

Describe precisely here what behavior is expected.

## Rationale

Describe why this requirement is needed.

## Scope

In scope:

- …

Out of scope:

- …

## Assumptions

1. …

## Open Questions

None.

## Decided Questions

None.

## Acceptance Criteria

### AC-XXX-01: Normal Case

Given:

- Initial situation

When:

- Action or event

Then:

- Expected result

### AC-XXX-02: Error or Boundary Case

Given:

- Initial situation

When:

- Action or event

Then:

- Expected result

## Dependencies

None.

## Evidence

### Implementation

- Path or command – result (AC-XXX-01)

### Tests

- Path or command – result (AC-XXX-01)

### Documentation

- Path or command – result (AC-XXX-01)
```

The template is for guidance. `## Scope` and `## Assumptions` are the only sections that may be omitted; the section list under “Contents of a Requirement File” is closed. A section with no content is deleted; an unresolved placeholder must not remain in an approved requirement.

## Acceptance Criteria

Acceptance criteria must be observable and testable. They should describe when a requirement is fulfilled.

### Good Acceptance Criteria

```markdown
### AC-XXX-01: The run produces the specified result

Given:

- a fresh clone of the repository
- no other environment has been set up

When:

- the documented command line is executed

Then:

- the run ends without error
- the specified result is produced
```

### Unsuitable Acceptance Criteria

```text
The application should handle missing values robustly.
```

This wording is too vague. It does not describe an unambiguous, testable result.

### Requirements for Acceptance Criteria

- An acceptance criterion tests **observable product behavior**. Its **subject** is identified: the **device** (output or effect), a **command** to be executed (result of the run), or a **file in the inventory** (its form or content). A statement about one of these three subjects is testable and permitted, even if determined by inspection or by running it.
- Statements about the **work process** and about **people** are **not** permitted: instructions, responsibilities, sequences (“only on the instruction of the responsible person”), “no additional dependency”, “no second path to the same result”, “the rest of the inventory remains unchanged”, “an unobserved result does not count as success”. They belong under `## Scope` or in the requirement that governs them.
- The normal case must be described; relevant error and boundary cases must be considered.
- A missing value must be distinguished from a valid value.
- Expected outputs, states, and error messages are stated **verbatim** when specified.
- A criterion describing a **probe** using a different value names the **authoritative location** where the probe value is set; this preserves equality between the header file and the authoritative location.
- Each acceptance criterion receives a unique ID (`AC-<ID>-NN`); the ID remains **stable**. A removed criterion is covered by the remaining cases, and all references to its ID are updated in the same step. The exception for **reordering** is set out in `### Reordering Acceptance Criteria`.
- A criterion consists of the **identifiers** `Given`, `When`, and `Then`, which are **not** translated; explanations, rationales, and prose within a criterion are omitted.
- **Target:** the number of acceptance criteria per requirement is specified under `### Measurement Rules`; it is a **recommendation**. If exceeded, the requirement should be split or consolidated; exceeding the target does **not** prevent approval.
- **Measurement rule:** see `### Measurement Rules`. An exceedance is reported as a **note**; no line in `## Evidence` is required for it.

### Reordering Acceptance Criteria

An acceptance criterion retains its **ID**; a removed ID is not assigned again. An **exception** applies only after a **fundamental restructuring** of the requirement and **only** on the **explicit request** of the responsible person:

- **Restructuring:** The number of criteria has changed because of the restructuring, and the remaining criteria still describe observable behavior.
- **Request:** The responsible person explicitly requests reordering and names the restructuring.
- **Reordering:** The remaining criteria are **renumbered consecutively in a single sequence** (`01` to `NN`); the former IDs are removed, and all references to a changed ID are updated in the **same** step.
- **Activity:** What was reordered and in response to which request is recorded in the **activity log file**; the requirement file does not narrate it (`### No Meta-Text`).

## Separate Functional and Technical Content

Requirements primarily describe expected behavior and its benefit to the user.

Example of a functional requirement:

```text
The application clearly identifies a missing result
and does not confuse it with a valid result.
```

The specific technical implementation belongs in the architecture or implementation documentation:

```text
The internal representation uses a dedicated sentinel for a missing result,
not a substitute value.
```

Technical proposals from the AI assistant are initially proposals. They become binding only when documented and approved as an architecture decision.

### One Fact, One Location

Each fact is maintained **preferably** in **one** location: the **single source** is a **strong recommendation**, **not** a requirement. If it cannot otherwise be accessed, an **exception** is permitted; the second location is identified as an **exception** and names the location that maintains the fact, together with its **rationale**.

- Tables are the **sole** source for the values, names, and units they contain; text refers to the table and does **not** repeat the value, **unless** an **exception** under the preceding paragraph is identified.
- If a value is needed in another requirement, refer to it (the value from the requirement that maintains it), rather than explaining it again.
- If another requirement is affected, record this in **its** `## Evidence`; do not create a separate section for it.
- A statement that already appears elsewhere is **deleted**, not reworded and repeated; an **exception** under the preceding paragraph remains permitted.
- The pair of **provision** (`## Requirement`, `## Scope`) and **test** (acceptance criterion) is **not** a duplicate statement and requires **no** exception: the criterion may repeat the fact in its own words so it can be tested without jumping to another section.
- The pair of **provision** (`## Requirement`) and **assumption** (`## Assumptions`) is **not** a duplicate statement: the provision names the fact and identifies the assumption as its location (`Assumption <N>`); the assumption contains its value (`### Assumptions`).
- **Display locations**—the **index file**, the **architecture file**, the **project overview**, and `## Evidence`—may state a fact without naming an exception; they do not maintain their own value and name the location that maintains it.
- `## Rationale` contains **only** rationales; a **provision** is not repeated there.
- A criterion tests **provisions**; if it itself contains a provision, that provision belongs in `## Requirement`, `## Scope`, or a Decided Question in the same file.
- **Duplicate statements are a note:** A repetition is identified with **file and line**; it does not prevent approval, but the responsible person decides it once (`### Approval Review and Finding Classes`). Evidence of the repetition is the **verbatim identical line** appearing twice in the same file after `### Measurement Rules`; it is found using the fourth pattern under `### Notes on the Check Run`.
- **Maintenance and testing are separate:** “Out of scope” in `## Scope` delimits the **maintenance** of a fact (repository, section, location) and does not rule out **testing** the same fact; this does **not** create a contradiction.
- A requirement file contains no reference to this rulebook.

## Open Questions

Open **functional** decisions are documented explicitly. They must not be silently replaced by technical assumptions; technical details without product significance are instead recorded in `## Assumptions` (`### Assumptions`).

This section has **exactly two** forms:

1. **`None.`**—when no question is open; nothing except this word appears in the section.
2. A numbered list of **open** questions in the entry form in `### Entry Form for a Question`; no introduction, narrative, mention of decided questions, **heading**, or **grouping** (such as `**Provisions**` or `**Questions**`).

The section shows the **currently applicable** state: the questions that are open **today**. Who answered and when is recorded in the **activity log file**.

### Entry Form for a Question

Each entry—open or decided—contains the **same** question and has **this** form:

```markdown
N. **Topic**
   Question: <the question, in one or two lines>
```

- **Topic line:** `N.` and the **topic** in bold, with no period at the end; the topic is the heading for the subject, not the question itself.
- **Question line:** `Question:` and the question in **one or two** lines, as concise as possible; the original wording remains accessible through Git.
- The **body** supplements the entry according to the section: `Proposal:` and `Open:` under `## Open Questions`; `Decision:` and `Applies in:` under `## Decided Questions`.
- An entry retains its **number** even if it later appears under `## Decided Questions`; the number is the stable reference for Git commits, acceptance criteria, and the **decision repository** (`## Terms`). The exception for **deletion** is set out in `### Deleting Questions`.
- A question entry and the two question sections together remain within the targets in `### Measurement Rules`; `### Writing Style` governs only paragraphs and bullet points outside the question sections. Both are **recommendations**; exceeding them is a **note**.
- If part of a question remains open, the question appears **twice** in the file: under `## Open Questions` with the body `Open:`, and under `## Decided Questions` with the body `Decision:`. Both entries have the **same number**, **same topic line**, and **same question line**—word for word—and both have the suffix `(partially decided)` at the end of the topic.
- A **directive**—a provision made by the responsible person—is **not** a question and therefore is **not** an entry in these sections: it appears as a statement in `## Requirement`, `## Scope`, or an acceptance criterion (`### One Fact, One Location`).

Examples of open questions (fictional; demonstrate form only):

- Through which method, and with what protection, are the inputs obtained?
- Which source is evaluated?
- In what form do the inputs arrive?
- What triggers an update, and at what interval does it occur?
- What happens on the first start when no valid inputs are available?
- How is a fault state reported?
- How should the output behave if only some of the inputs are available?
- Which components are provided by the environment, and how are they connected?

A requirement with open questions that are critical to implementation normally remains in status `draft` or `proposed` and is not yet implemented.

A contradiction with another requirement is recorded as an Open Question; no separate section is created for it.

When a question is decided, it is not deleted; it is moved to the “Decided Questions” section of the same file.

### Deleting Questions

A question is **not** deleted; it is decided and moved to `## Decided Questions`. An **exception** applies only after a **fundamental restructuring** of the requirement and **only** on the **explicit request** of the responsible person:

- **Restructuring:** Afterward, `## Requirement`, `## Scope`, or the acceptance criteria themselves contain the facts; the affected questions no longer state a distinct fact.
- **Request:** The responsible person explicitly requests deletion and names the restructuring.
- **Carryover:** What the deleted questions specified is then stated in `## Requirement`, `## Scope`, or an acceptance criterion (`### One Fact, One Location`).
- **Renumbering:** The remaining entries in **both** sections are renumbered **consecutively in a single sequence**; the former numbers are removed, and all references to a changed number are updated in the **same step**.
- **Activity:** What was deleted and in response to which request is recorded in the **activity log file**; the requirement file does not narrate it (`### No Meta-Text`).

## Decided Questions

A decided question is retained: it is moved here from `## Open Questions`; its number, topic line, and question line remain unchanged (`### Entry Form for a Question`).

An entry therefore presupposes a **question** that previously appeared under `## Open Questions` and was answered by the responsible person; no entry is created without that question (`### Rules`).

The section has **exactly two** forms—as does `## Open Questions`:

1. **`None.`**—when nothing has been decided; nothing except this word appears in the section.
2. A numbered list of **decided** questions in the entry form in `### Entry Form for a Question`, supplemented with `Decision:` and `Applies in:`; no heading, grouping, or introduction.

### Structure of an Entry under “Decided Questions”

The entry follows the question entry form (`### Entry Form for a Question`) and adds:

```markdown
N. **Topic**
   Question: <the question, in one or two lines>
   Decision: <the decision, in one to three lines>
   Applies in: AC-XXX-NN
```

- **Decision line:** `Decision:` followed by the decision in **one to three** sentences; explanations, rationales, examples, and emphasis in bold are omitted. The line states **only** the decision: references to a proposal and its acceptance or rejection are omitted; the `Proposal:` body appears only under `## Open Questions` (`### Entry Form for a Question`), and the activity is recorded in the **activity log file** (`## Terms`).
- **Applicability line:** `Applies in:` with **one** location—`AC-XXX-NN`, `## Requirement` or `## Scope` of this requirement, another requirement with its section (a Decided Question in the other requirement), a decision in the **decision repository**, or the **index file** for a decision about an **attribute** (`### Metadata`); listing several locations is omitted, and the effectiveness of a decision is checked, not listed.
- An entry **without** a decision is omitted; any open part belongs under `## Open Questions` (`Open:`).
- A technical decision—a library, protocol, transport, or storage format—belongs in the **decision repository** (`## Terms`); the entry names it by its number.
- The numbers in the two sections come from **one** sequence; do **not** create two number sequences, one for directives and one for questions. The same number is used only for the two entries of a **partially decided** question (`### Entry Form for a Question`).

### Example (Fictional; Form Only)

```markdown
1. **Location of the output**
   Question: Is the value displayed or only output?
   Decision: The value is displayed.
   Applies in: AC-XXX-01

2. **Source of the library**
   Question: Is a vendor library used, or a custom one?
   Decision: The vendor library is used.
   Applies in: a decision in the decision repository
```

If part of a question remains open, it appears in **both** sections with the **same** topic and **same** question line. Under `## Open Questions`, the entry then reads:

```markdown
3. **Output format (partially decided)**
   Question: In what form is the output produced, and how is it read?
   Open: The file header.
```

Under `## Decided Questions`, the same entry reads:

```markdown
3. **Output format (partially decided)**
   Question: In what form is the output produced, and how is it read?
   Decision: The output consists of one text line per value.
   Applies in: AC-XXX-02
```

### Rules

- A decided question retains its **number**; the number is not reused, and the topic line and question line are not rewritten (`### Entry Form for a Question`); the exception for **deletion** is set out in `### Deleting Questions`.
- The question is not deleted, even if the requirement later becomes `implemented`, `verified`, or `deprecated`; deletion under `### Deleting Questions` remains possible.
- The decision must take effect: the requirement text, `## Scope`, and acceptance criteria are reviewed and adjusted; an impact on another requirement is recorded in **its** `## Evidence`. A decision that does not appear in any requirement or acceptance criterion is not yet a decision.
- If the decision concerns behavior that warrants its own requirement, a new requirement file is created. The entry then refers to that requirement.
- A technical decision—a library, protocol, transport, or storage format—does not belong here, but in the **decision repository**; the entry names the decision by its number.
- Functional decisions are made by the responsible person. The AI assistant may prepare a decision as a `Proposal:`; it becomes binding only upon approval. An open question must not be silently treated as decided.
- An entry is created **only** from a question that previously appeared under `## Open Questions` and was answered; the AI assistant does **not** create one on its own initiative.
- Information derived by the AI assistant or incorporated from the request is **not** a question: it appears as a statement in `## Requirement`, `## Scope`, or an acceptance criterion (`### One Fact, One Location`).
- If all questions critical to implementation have been decided and no finding weighs against the recommendation, approval is **recommended** (`### Approval Review and Finding Classes`); granting approval remains the responsibility of the responsible person.

## Dependencies

Dependencies are entered in the metadata block; the `## Dependencies` section explains the entries in `depends_on` and `related` and names no additional requirement:

```yaml
depends_on:
  - FR-XXX
  - NFR-XXX
```

A dependency should be added only if the other requirement is genuinely a prerequisite for implementation or verification.

Example:

```text
A constraint that specifies the toolchain and versions
```

may depend on previously specified state management, but does not necessarily have to depend on a particular library.

## Changes to Requirements

When a requirement changes:

1. The change is made in the same file; it **replaces** the old wording and is not added on top of it.
2. The ID remains unchanged.
3. The acceptance criteria are adjusted.
4. Dependencies and related requirements are reviewed.
5. The Git diff is checked for unintended changes.
6. The status is adjusted if approval is no longer valid.
7. A meaningful commit is created through **manual interaction** by the responsible person (`## Git Rules`).

Examples in the format of the **commit rule** (`## Terms`):

```text
docs(FR-042): add acceptance criteria for the output
docs(CON-042): pin the version of the build environment
docs: deprecate FR-007 automatic startup
```

If a change completely alters the functional objective, consider whether a new requirement would be more appropriate. The old requirement is then set to `deprecated`.

No chronology is added to the requirement file; the previous state is accessible through Git. The activity is recorded in the **activity log file** in no more than four body lines—what happened, result, newest first.

## Implementation and Evidence

A requirement is considered `verified` only when:

- the implementation is present
- the acceptance criteria have been checked
- appropriate automated tests are available, where possible
- relevant error and boundary cases have been checked
- the documentation is current
- the Git diff has been reviewed
- there are no unintended side changes

`## Evidence` contains the three subsections `### Implementation`, `### Tests`, and `### Documentation`, each with **one line per item of evidence** in the form `<Path or command> – <Result> (<AC-ID>)`.

- **Before implementation**, the result is replaced with `open:` followed by the evidence to be provided: `<Path or command> – open: <What must be evidenced> (<AC-ID>)`; the wording `This requirement` or `This constraint` is also permitted.
- For a requirement that records **existing implementation**, use the evidence form in `### Recording Existing Implementation`.
- Command output, measurements (such as runtime or storage requirements), and intermediate results belong in the commit message or the **working repository** (unversioned) and are **not** retold.
- A separate “Testing outside the tests” section is omitted; a manual check is **one** line.
- Sentences about previous states or work on the file (“With the answers on …”, “the changes were …”) are omitted (`### No Meta-Text`).
- An exceeded target **may** be recorded in **one** line, with **no** path and **no** proposed wording: `Target exceeded: <N> lines excluding table rows.` or `Target exceeded: <N> acceptance criteria.` The line is **optional**; exceeding the target does **not** prevent approval.
- Content under `## Evidence` is **not** repeated under `## Requirement` or `## Scope`.

Example:

```markdown
## Evidence

### Implementation

- `<Source file>` – output generated (AC-XXX-01)

### Tests

- `<Test command>` – all test programs built (AC-XXX-02)

### Documentation

- `<Architecture file>` – structure and process described (AC-XXX-01)
```

## Working with the AI Assistant

For a new requirement, the AI assistant should initially analyze and ask questions only.

Approval follows **one** sequence: review (`### Review a Requirement`), present the **complete** list of findings in **one** pass, wait for acceptance, address the accepted findings, perform acceptance (`### Acceptance after Implementation`), and only then grant approval when instructed. A finding raised after acceptance against **unchanged** text is a **note** (`### Approval Review and Finding Classes`). The sequence results in a **recommendation**, not a decision: form-related findings do not prevent approval, and the responsible person alone decides on approval.

### New Requirement or Follow-up Change

Not every change requires its own requirement file. Scope is determined by the **decision** it needs:

- **New requirement**—the change requires a **functional** decision not covered by an existing requirement: new behavior, new data selection, new access, new technology, or a new promise to the user. A **separate file** is created (`### Define a New Requirement`).
- **Follow-up change**—the change implements **only** what an **existing, approved** requirement or a **constraint** already specifies, and introduces **no** new functional decision. No new file is created: the activity is recorded in the **activity log file**, and the evidence is recorded in `## Evidence` of the governing requirement.

The AI assistant classifies the change and **states** the classification together with the governing requirement; if the responsible person disagrees, their classification applies. A follow-up change is **not** a way to bypass an unanswered functional question: an open question remains open even if the implementation is small.

### Define a New Requirement

The AI assistant should:

1. read the **project overview** and the **project instructions** (`## Terms`)
2. review the requirements in this directory
3. use an available ID
4. identify functional ambiguities
5. invent no address, data format, configuration, or credentials
6. create a proposal for exactly one requirement file
7. formulate testable acceptance criteria
8. initially set the status to `proposed`
9. change no code until the requirement has been approved

### Review a Requirement

Before approval, the AI assistant should review the requirement. The review ends with a **recommendation**; the responsible person decides whether to approve (`### Approval Review and Finding Classes`).

**Content—grounds for not recommending approval:**

- Is the requirement clear and understandable—do the subject, behavior, and boundary specify it so that two readers interpret it in the same way?
- Is it small enough for a single file?
- Is the expected behavior testable?
- Are normal, error, and boundary cases covered?
- Are there contradictions with other requirements?
- Are technical details incorrectly stated as functional requirements?
- Have all material open questions been answered?
- Does every entry under `## Decided Questions` originate from a question that previously appeared under `## Open Questions` and was answered (`## Decided Questions`, `### Rules`)?
- Does every acceptance criterion test **observable product behavior** (`### Requirements for Acceptance Criteria`)?

A **contradiction** is considered only if **both** locations are named **verbatim** (file and line). The following weigh against the recommendation: an **unanswered** question—an entry under `## Open Questions` other than `None.`—an `Open:` item for a partially decided question, and an entry under `## Decided Questions` that does not originate from an answered question (`## Decided Questions`, `### Rules`).

**Form—notes:** The following review points are **notes** (`### Approval Review and Finding Classes`); a violation does **not** prevent approval:

- Does each provision have a testing criterion, does each criterion have a provision, and does each provision name a location (`### Requirement and Scope`)?
- Does the file contain statements about itself or the work on it (`### No Meta-Text`)?
- Does `## Open Questions` contain anything other than `None.` or a list of open questions?
- Does each entry under `## Open Questions` and `## Decided Questions` have a topic line and a question line, and does it meet the targets in `### Measurement Rules`?
- Does each entry under `## Decided Questions` have **one** decision and **one** applicability line?
- Does each decision line state the decision itself—**without** reference to a proposal and **without** explanation, rationale, example, or emphasis?
- Do the two entries for a partially decided question have the same number, topic line, and question line—and both include the suffix `(partially decided)`?
- Does an entry contain a parenthetical addition other than `(partially decided)`?
- Do `## Open Questions` or `## Decided Questions` contain anything other than `None.` or the entries—a heading or grouping such as `**Provisions**` or `**Questions**`?
- Does a file have **two** number sequences in the question sections—that is, the same number for **two different** entries?
- Do the question sections of **one** requirement form a **consecutive** sequence, and is a **deletion** under `### Deleting Questions` supported by a **request**?
- Do the acceptance criteria of **one** requirement form a **consecutive** sequence, and is a **reordering** under `### Reordering Acceptance Criteria` supported by a **request**?
- Does the file refer to this rulebook? Such a reference is **removed** (`### No Meta-Text`).
- Does any line in the file appear **verbatim twice** after `### Measurement Rules`?
- Do the acceptance criteria remain within the target in `### Measurement Rules`?
- Does the file remain within the target in `### Measurement Rules`?
- Do `## Open Questions` and `## Decided Questions` together remain within the target in `### Measurement Rules`?
- Does the **index file** contain **one** line per requirement, with no prose?
- Does each entry in the **activity log file** remain within the targets in `### Measurement Rules`, and does it avoid retelling the work?
- Is there **one** entry per change between two commits, and does the **topmost** entry summarize the change since the last commit?

### Notes on the Check Run

The check should be automated where possible; the patterns below should be applied in a text-search tool in an equivalent way—the execution tool and paths are project-specific. A check **runs** the patterns and reports the result for each (`0 matches` or file and line); the patterns do not replace the findings list. A match is **initially** only a **note** and does not prevent approval; it is reported to the responsible person. If the **content review** confirms the violation, the match becomes a **finding weighing against the recommendation** (`### Approval Review and Finding Classes`); the case of a **provision** referred to at a **display location** is covered by the run against the **decision repository** below. This rulebook itself is excluded because its rules contain the patterns and it is located in the same repository; substitute `<Requirements repository>`, and `<Name of this rulebook>` means `doc/requirements/requirements.md` (`## Related Project Documentation`).

```text
grep -rn -E --exclude='requirements.md' 'on 20-|in the meantime|All questions|all questions|no questions remain|process as in|<Name of this rulebook>' <Requirements repository>
grep -rn -E --exclude='requirements.md' '\*\*(Provisions|Questions)\*\*$' <Requirements repository>
grep -rn -E --exclude='requirements.md' '^ *Decision:.*[Pp]ropos' <Requirements repository>
awk 'BEGIN{sec=0} /^## /{sec = ($0 ~ /^## (Requirement|Scope)$/)} sec && length($0)>=40 && $0 !~ /^\|/ && seen[$0]++ {print FILENAME":"FNR": line appears twice"}' <Requirements repository>/*/*.md
awk 'BEGIN{sec=0} /^## /{sec = ($0 ~ /^## (Requirement|Scope)$/)} sec && /^ *- .*(Priority|requirement type|Status [`"])/ {print FILENAME":"FNR": attribute in text"}' <Requirements repository>/*/*.md
```

It is recommended that a match be removed or—if it carries a **currently applicable provision**—moved into `## Requirement`, `## Scope`, or an acceptance criterion. A reference to a **proposal** carries no provision and is removed; a currently applicable provision remains as a statement of the decision. A match on an **attribute** is **removed**; the attribute belongs in the YAML header and the **index file** (`### Metadata`).

Another run checks the **decision repository** for a provision that is referred to at a **display location** (`### Contents of a Decision`); here too, a match is **initially** only a **note**:

```text
grep -rn -E '(in|with) the (technical )?architecture[^a-z]|architecture and build environment|specified in the index file' <Decision repository>
```

The pattern finds only a **wording**, not the **fact**. If the **content review** (`### Review a Decision`) confirms that a **provision** is in fact referred to or announced at a **display location**, the match becomes a **finding weighing against the recommendation** (`### Approval Review and Finding Classes`); without that confirmation it remains a **note**.

The following four runs check the **activity log file** for line width, amount of body text, heading format, and narration—the **values come from `### Measurement Rules`**; here too, a match is a **note**:

```text
awk 'length($0)>120 {print FILENAME":"FNR": "length($0)" characters"}' <Activity log file>
awk '/^## /{if(h){if(s>480) print FILENAME":"h" -> "s" characters"} h=FNR; s=0; next} NF{s+=length($0)} END{if(s>480) print FILENAME":"h" -> "s" characters"}' <Activity log file>
grep -n -E '^## ' <Activity log file> | grep -v -E ':## {4}-{2}-{2} – '
grep -n -E '^- (What|Result|Evidence):.*(because|so that|for|therefore|cause)' <Activity log file>
```

A run checks the **architecture file** to ensure that it contains **no** history and **no** dates (`### Architecture File`); here too, a match is a **note**:

```text
grep -n -E 'on 20-|in 20-|since|in the meantime|withdrawn|Status [`"]' <Architecture file>
```

A match is **removed**; a **currently applicable provision** is moved to the responsible requirement, the **decision repository**, or the **index file**, and the activity is recorded in the **activity log file**.

### Measurement Rules

The targets and thresholds in this rulebook, together with their counting methods. The targets are **recommendations**: exceeding them is reported as a **note** and does **not** prevent approval; the same applies to a target **without** a measurement rule.

| Target | Size | Counting method |
|---|---|---|
| Requirement file | no more than 800 lines | excluding blank lines and table rows |
| `## Open Questions` and `## Decided Questions` | no more than 80 lines | excluding blank lines and table rows |
| One question entry | no more than 7 lines | excluding blank lines |
| Acceptance criteria | no more than 15 | `### AC-…` headings |
| **Activity log file** line | no more than 120 characters | line width in characters |
| Activity body text | no more than 480 characters | body lines combined, excluding heading and blank lines |
| Activity heading | no more than 100 characters | heading width in characters |
| One paragraph (`### Writing Style`) | no more than 5 lines | excluding blank lines |
| One bullet point | no more than 2 lines | continuation lines count |
| `## Scope` | no more than 15 lines | excluding blank lines |
| `## Assumptions` | no more than 10 lines | excluding blank lines |
| Requirement file line | no more than 120 characters | line width in characters |
| Evidence of a duplicate line | at least 40 characters | verbatim identical line, excluding surrounding spaces |

### Approval Review and Finding Classes

An approval review performs **all** check items in `### Review a Requirement` and—in the case of a decision—`### Review a Decision`, and returns **two** kinds of findings.

- **Finding weighing against the recommendation**—a finding in the **Content** group from `### Review a Requirement` and, for a decision, from `### Review a Decision`: an **incomprehensible** or **ambiguous** statement, behavior that is **not testable**, an **unanswered functional** question, or a **contradiction** for which **both** locations are named **verbatim** (file and line) and whose statements are mutually exclusive. A match from a pattern in `### Notes on the Check Run` also weighs against the recommendation if the **content review** confirms it as a finding in the **Content** group—in particular, a **provision** referred to or announced at a **display location**. While such a finding remains, approval is **not recommended**.
- **Note**—**everything else**, especially **form**, a **missing reference**, and a **provision without a testing criterion**: the targets in `### Measurement Rules`, including character width and length; the sections and their order; a number sequence; **meta-text** under `### No Meta-Text`; a duplicate line; a match from patterns in `### Notes on the Check Run` that the content review does **not** confirm; the requirements for `## Evidence`, including the form of its evidence lines; concision, readability, wording, a missing location, and a target without a measurement rule. **When in doubt, a finding is a note.** A note does not prevent approval; it is reported to the responsible person.

A finding names the rule it follows (section and line) and the **wording** of the affected location; without this location, it is **not** a finding. A review is **complete**: it lists **all** findings in **one** list and does **not** stop at the first finding. A requirement file is changed only because of a **finding weighing against the recommendation** or on the responsible person's instruction.

The review ends with **exactly one** statement: **“Approval recommended”** or **“Approval not recommended: …”**; **notes** are listed separately. A report without this statement is not a review.

**Once only:** An approval review is performed **once** for each **text version** of a requirement. Its result appears as **one** `Result:` line in the **activity log file**, together with the recommendation, number of findings weighing against the recommendation, and number of notes. It is repeated only after a **change to this rulebook**, on the responsible person's **explicit** request, or if the requirement has changed **beyond the findings already addressed**.

**Binding effect of earlier reviews:** A later review is bound by the earlier one.

- The responsible person decides on each note **once**; the decision appears as **one** line in the **activity log file** and is **not** raised again.
- An item the responsible person had removed **upon request**—a question, criterion, or section—is **not** a finding; the request is recorded in the **activity log file**.
- No new finding weighing against the recommendation is raised against **unchanged** text; the only permitted result is a **note** that identifies the location **and** explains why the earlier review did not report it.
- A finding caused **only** by implementing proposals recorded in the **activity log file** is **not** raised; it is considered a consequence of the previous review and reported to the responsible person as a **note**.
- If a finding reverses a **previous** finding, it names **both** reviews by their activity-log lines; it is a **note**.

**Acceptance follows implementation** (`### Acceptance after Implementation`); it is **not** a new review. If all findings weighing against the recommendation have been addressed and acceptance identifies no open item, approval is **recommended**; any further finding may then arise **only** from a **change to this rulebook** or from a **changed requirement landscape outside the list**.

Whoever changes this rulebook checks the affected requirement files in the **same** step and records the activity in the **activity log file**; a rule change **without** this follow-up does **not** prevent approval of a file reviewed earlier.

The **responsible person** grants **approval**; the AI assistant sets `approved` **only** on their instruction and **only** after a result of “all findings addressed” or at their explicit request. The review's **recommendation** is not binding on them: they may approve a requirement with open notes or contrary to the recommendation.

- **Notes** are **not** incorporated into the requirement file; it is changed because of a **finding weighing against the recommendation** or on the responsible person's instruction.

### Acceptance after Implementation

Acceptance is the **counter-check against the list**, not a new review.

1. **Reconcile:** Review each **finding weighing against the recommendation** at its location; record each finding as `addressed` or `open`. **Notes** do not prevent acceptance.
2. **Review the diff:** Check the Git diff for side changes; revert any change that was not a finding on the list.
3. **Run patterns:** Run the five patterns in `### Notes on the Check Run`; report the result of each.
4. **Result:** Acceptance ends with one of the two statements “all findings addressed” or “open: …” and appears as **one** line in the **activity log file**.
5. **No new finding:** A finding against **unchanged** text is a **note**; it is reported to the responsible person **once**.

### Implement a Requirement

After functional approval, the AI assistant should:

1. read the relevant requirement file in full
2. review dependent and related requirements
3. examine the existing code and tests
4. prepare an implementation plan
5. implement only the approved requirement
6. add or update appropriate tests
7. run the relevant checks
8. review the Git diff
9. update the evidence in the requirement file
10. avoid refactoring unrelated code

A commit is created only after review and explicit approval and **never** automatically, but through **manual interaction** by the responsible person (`## Git Rules`).

## Git Rules

- A commit is **never** created automatically, but through **manual interaction** by the responsible person—not by the AI assistant, a tool, a script, a workflow, or a timer.
- Approval to implement, review, or deploy is **not** an instruction to commit.
- Requirements are versioned together with the project.
- Changes to requirements are not documented outside Git.
- A functional change receives its own traceable commit.
- Multiple actions and AI interactions between two commits result in **one** entry in the **activity log file**; each further interaction **updates** the open entry (`### History and Current State`).
- Implementation and requirement changes are not mixed if they are independent of each other.
- The requirement ID is used in commit messages where relevant; the message format is set out in the **commit rule** (`## Terms`).
- Large bulk changes to many independent requirements should be avoided.

Examples in the format of the **commit rule** (`## Terms`):

```text
docs(FR-042): clarify output after reset

feat(FR-042): write the greeting to the console

test(CON-042): cover pinned toolchain version
```

## Architecture Decisions

An **architecture decision** is a reasoned technical or architectural choice—for example, a library, protocol, transport, or storage format. It is stored in its own Markdown file in the **decision repository** (`## Terms`); the **index file** (`## Terms`) maintains the index.

- A technical decision belongs here and **not** in a requirement; the Decided Question names it by its number (`## Decided Questions`).
- A decision must not contradict a requirement; if it changes expected behavior, the requirement is changed first.
- A choice that would require changing a **constraint** (`## Requirement Types`) is changed there first.
- A decision becomes binding only with the **approval** of the responsible person; before that, it is a **proposal** from the AI assistant.

### Filenames and IDs

Decision files use this format:

```text
ADR-<NNNN>-<short-kebab-case-name>.md
```

The name follows the rule in `## Filenames`; the ID is `ADR-<NNNN>` and appears in the filename and the `id` field.

- Sequential numbering from `ADR-0001`, four digits; numbering is independent of the numbering of other projects.
- Numbers that have already been used are **not** reused.
- A superseded decision retains its number and receives the note `superseded by ADR-NNNN`.

### Decision Metadata

Each decision file begins with a YAML block:

```yaml
---
id: ADR-0001
title: Short, unambiguous title
status: proposed
date: YYYY-MM-DD
affects:
  - CON-042
related:
  - FR-042
---
```

| Field | Required | Meaning |
|---|---|---|
| `id` | Yes | Decision ID (`ADR-<NNNN>`), as in the filename |
| `title` | Yes | Short, unambiguous title |
| `status` | Yes | Processing status (`### Decision Status Values`) |
| `date` | Yes | Date recorded |
| `affects` | No | Requirements whose implementation is affected by the decision |
| `related` | No | Related decisions or requirements |

The field names are **identifiers** and are **not** translated.

### Decision Status Values

| Status | Meaning |
|---|---|
| `proposed` | Proposal not yet approved, functionally or technically |
| `accepted` | Binding; implementation may be based on it |
| `rejected` | Deliberately not selected; rationale is retained |
| `deprecated` | No longer valid, but retained for traceability |
| `superseded` | Replaced by another decision (with reference) |

The values are **identifiers** and are **not** translated. A proposal from the AI assistant is recorded as `proposed`; it becomes binding only with the approval of the responsible person.

### Contents of a Decision

Each decision file contains these sections:

1. YAML header (`### Decision Metadata`)
2. **Context**—initial situation and open question
3. **Decision**—the selected option, in individual points
4. **Rationale**—why this option
5. **Alternatives**—options considered and why they were not selected
6. **Impact**—affected requirements, files, and evidence
7. **Open Points**—what the decision deliberately does not regulate
8. **Evidence**—what has already been implemented and what remains

The same principles apply to the content **by analogy** as to a requirement file (`### One Fact, One Location`, `### No Meta-Text`, `### Writing Style`); in addition:

- **Name the open question:** `## Context` identifies the **answered** open question and the location where it was open—a requirement with its section, the **project overview**, or an earlier decision; it does **not** merely describe the initial situation, but explicitly formulates the choice to be made.
- **Provisions belong in the decision:** `## Decision` itself contains the provisions and any version bound by them; the proposal status appears **only** in the status (`### Decision Status Values`).
- **No provision at a display location:** A provision—especially a bound version—is **not** referred to a **display location** (`### One Fact, One Location`) such as the **architecture file** or the **index file**, nor announced there (“will be specified in the architecture”); if it has not yet been decided, it appears under `## Open Points` together with the location where it will be decided.
- **References:** A reference names the **file and section** (`### Writing Style`) and no dates.
- **Open points are not open questions:** `## Open Points` states only what the decision **deliberately** does not regulate; that is **not** an unanswered question for the purposes of `### Review a Decision`.

### Review a Decision

Before approval, the AI assistant should also review a decision. The review ends with a **recommendation**; the responsible person decides on approval (`### Approval Review and Finding Classes`).

**Content—grounds for not recommending approval:**

- Does `## Context` name the answered open question and its location (`### Contents of a Decision`)?
- Are all provisions and bound versions contained in `## Decision` itself, with none referred to a display location such as the **architecture file** or the **index file** (`### One Fact, One Location`)?
- Does `## Impact` name each affected requirement by its ID (`### Contents of a Decision`)?
- Is the decision clear and understandable—do the subject, behavior, and boundary specify it so that two readers interpret it in the same way?
- Does the decision contradict a requirement or an approved decision?
- Does the decision change the expected behavior of a requirement, and was that requirement changed first (`## Architecture Decisions`)?
- Is there no unanswered question—does each entry under `## Open Points` state only what the decision **deliberately** does not regulate (`### Contents of a Decision`)?

**Form—notes:** For form, `### Review a Requirement` applies **by analogy**; a violation does **not** prevent approval.

## Related Project Documentation

The following files and repositories belong to the overall project; their names are the same in **every** project and are not specified differently in the project overview:

| Role | File or directory | Purpose |
|---|---|---|
| Project overview | `README.md` | Project objective, current status, and entry point for people |
| Project instructions | `doc/llm.md` | Rules and working practices for the AI assistant; **AI tool rule files** are in their **project-specific** repository (`## Terms`) |
| Requirements repository | `doc/requirements/` | Functional and quality requirements |
| Rulebook | `doc/requirements/requirements.md` | Rules and forms for requirements; the same path in **every** project |
| Index file | `doc/status.md` | Pure index: requirements and architecture decisions |
| Activity log file | `doc/changelog.md` | Activities, newest first; targets under `### Measurement Rules` |
| Architecture file | `doc/requirements/architecture.md` | Currently applicable technical architecture |
| Decision repository | `doc/decisions/` | Reasoned architecture and technology decisions |

### Architecture File

The **architecture file** describes **only the currently applicable state** of the technical architecture. `### History and Current State` applies to it **by analogy**: it contains **no** change history, **no** dates, **no** statements about a **previous** state, and **no** narration of activities or processing status—not even as an addendum, footnote, or parenthetical. History is maintained by **Git**, including commit messages; activities are recorded in the **activity log file**.

- It is a **display location** and **not** an authoritative location (`### One Fact, One Location`): it names structure, components, and process **concisely** and **without its own values**; each value, status, and provision is maintained in **exactly one** other location—in the responsible requirement, a **constraint**, or the **decision repository**—and the architecture file names that location.
- **Bound versions**—platform, language, framework, and build environment—are maintained by a **constraint** or a **decision**; the architecture file names them as a **display location** and refers to the authoritative location.
- The **status** of a requirement or a decision does **not** appear in it; status belongs in the requirement's or decision's YAML header and in the **index file**.
- Meta-text is omitted as in a requirement file (`### No Meta-Text`).

All other roles from `## Terms` that name a file or repository have **project-specific** names: **AI tool rule files**, the **commit rule**, **build tools**, the **working repository**, the **staging repository**, and further **project instructions** in addition to `doc/llm.md`. Their names appear in the **project overview**; `doc/llm.md` remains **project-independent** and refers to them.

## Principle

> One file describes exactly one requirement. This rulebook describes only the rules and forms.

A requirement file describes the currently applicable state; history is maintained by Git, and activities are recorded in the **activity log file**. The same applies to the **architecture file** (`### Architecture File`).

Functional decisions are approved by the responsible person. The AI assistant may analyze requirements, ask questions, formulate proposals, and prepare their implementation. It must not silently invent missing product decisions.
