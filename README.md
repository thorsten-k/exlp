
# EXLP

EXLP (Extensible Logfile Processor) is an extensible Java framework that reads input from different sources (files, directories, console, compressed files), processes it into events, and forwards those events to different destinations (files, XMPP, JMS, EJB, ...).

## Initial Situation

EXLP is an established, historically grown multi-module Maven project (`net.sf.exlp:exlp`). The code base is organised into the following modules:

- `bom` – central dependency and version management
- `interfaces` – core contracts (`LogListener`, `LogParser`, `LogEventHandler`, `LogEvent`) and exceptions
- `core` – pipeline building blocks: listeners, parsers, handlers, events
- `util` – utilities: I/O, files and directories, XML, network/JMS, process, compression
- `xml` – XML/JSON helpers (CDATA, models)
- `shell` – shell, OS, and command wrappers as well as process spawning
- `ejb` – EJB integration (facades) for the add-ons
- `maven` – Maven plugin (goals `mvnVersionIgnore`, `reachabilityMetadata`, and a test goal)
- `test` – test-support classes
- `client` – desktop client (config-pointer tray)
- `addon` – optional extensions (standalone module, not part of the root aggregator)
- `doc` – project documentation

## Relevant Values

- **Document language:** English.
- **Version (source of truth):** the root `pom.xml` (`0.1.18-SNAPSHOT`); all modules inherit it.
- **Version-ignore rules:** `maven/src/main/resources/exlp/maven/exlp-versions.xml`.
- **Logging configuration:** `util/src/main/resources/config/log4j.xml`.
- **JAXB model package base:** `org.exlp.model.xml`.

## Technical Assumptions

- **Platform:** Java 8 (compiler `source`/`target` `8`).
- **Build:** Maven multi-module build; artifacts are published under the group `net.sf.exlp`.
- **Libraries:** SLF4J with Log4j2 (logging), JUnit 4 (testing), JAXB and JDOM (XML), Jackson (JSON), Apache Commons (CLI, IO, Configuration).
- **License:** GNU General Public License v3 (GPL-3.0).

## Open Fundamental Decisions

_To be defined._

## Decided Fundamental Decisions

_To be defined._ Details are kept in the **Decision Repository** (`doc/decisions/`).

## Planned Milestones

_To be defined._

## Reachability Metadata

The Maven plugin generates Native Image reachability metadata from annotated production classes
(goal `reachabilityMetadata`). The goal runs on explicit invocation or bound to a Maven lifecycle
phase:

```bash
mvn net.sf.exlp:exlp-maven:0.1.18-SNAPSHOT:reachabilityMetadata
```

The configuration selects the annotation types; per annotation, `registerClass`,
`registerConstructors`, `registerFields`, and `registerMethods` select the registration elements
(each defaults to `true`):

```xml
<plugin>
    <groupId>net.sf.exlp</groupId>
    <artifactId>exlp-maven</artifactId>
    <version>0.1.18-SNAPSHOT</version>
    <executions>
        <execution>
            <id>reachability-metadata</id>
            <phase>process-classes</phase>
            <goals><goal>reachabilityMetadata</goal></goals>
        </execution>
    </executions>
    <configuration>
        <annotations>
            <annotation>
                <type>com.example.Entity</type>
                <registerConstructors>false</registerConstructors>
                <registerFields>true</registerFields>
            </annotation>
        </annotations>
    </configuration>
</plugin>
```

The annotation types come from the application or its dependencies and are named by their fully
qualified names; the goal resolves the compile dependencies of the module for that. The scanned
artifact therefore does not depend on EXLP.

The goal writes the metadata to `META-INF/native-image/<groupId>/<artifactId>/reachability-metadata.json`
in the build output directory of the current module; the packaging places it in the artifact.

## Build and Start

Prerequisites: JDK 8 or newer and Maven 3.

```bash
mvn clean install          # build all modules and run the tests
mvn -pl core test          # run the tests of a single module
```

## Development Principles

- Follow the project instructions for the AI assistant in `doc/llm.md` and the **Requirements Rules** in `doc/requirements/requirements.md`.
- Write documents, source code, and new file names in **English**; use ASCII kebab-case for new file names.
- Cover new functionality with tests and run them (together with the Git diff) after each change.
- Never create a commit automatically; see the **Commit Message Rule**.

## Rules and Roles

- Project instructions for the AI assistant: `doc/llm.md`; rule files of the AI tool: `.clinerules/`.
- Commit Message Rule: `.clinerules/10-git-commit.md`.
- Requirements Rules: `doc/requirements/requirements.md`.
- Index File of requirements and architecture decisions including status: `doc/status.md`.
- Change Log (chronicle): `doc/changelog.md`.
- Architecture File: `doc/requirements/architecture.md`; Decision Repository: `doc/decisions/`.