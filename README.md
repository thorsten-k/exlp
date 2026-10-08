
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

- **Platform:** Java 8 for the `javax` artifact and for the modules of the layered chain, which compile against the Java 8 API (`--release 8`, ADR-0003); the `xml` module builds with JDK 11 or newer and produces the `jakarta` artifact for Java 11 (ADR-0002, `doc/decisions/`).
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

The configuration lists the annotation types by their fully qualified names; every production class
of the module that bears one of them is registered with all its declared constructors, fields, and
methods:

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
            <annotation>com.example.Entity</annotation>
        </annotations>
    </configuration>
</plugin>
```

The annotation types come from the application or its dependencies; the goal resolves the compile
dependencies of the module for that. The scanned artifact therefore does not depend on EXLP.

The goal writes the metadata to `META-INF/native-image/<groupId>/<artifactId>/reachability-metadata.json`
in the output directory configured for the run (default: the build output directory of the current
module); the packaging places it in the artifact.

### Binding in the EXLP modules

The `xml` module binds the goal twice at `process-classes`, once per variant output directory (ADR-0002),
so `mvn clean install` writes the metadata into each variant directory and the packaging places it in the
matching classifier artifact. The plugin depends on no module of the layered chain, which the binding
requires.

## JAXB Variants

The `xml` module compiles both JAXB variants from the same XSD in one Maven run and publishes exactly two
artifacts: `net.sf.exlp:exlp-xml:<version>:javax` and `net.sf.exlp:exlp-xml:<version>:jakarta`. There is
no artifact without a classifier, so a consumer names the classifier that fits its platform (ADR-0002).

```xml
<dependency>
    <groupId>net.sf.exlp</groupId>
    <artifactId>exlp-xml</artifactId>
    <version>0.1.18-SNAPSHOT</version>
    <classifier>javax</classifier>
</dependency>
```

The generated sources are versioned; the profiles regenerate them from `xml/src/main/xsd/`:

```bash
mvn -pl xml -Pjavax generate-sources      # regenerate the javax sources
mvn -pl xml -Pjakarta generate-sources    # regenerate the jakarta sources
```

## Build and Start

Prerequisites: JDK 11 or newer and Maven 3. All modules compile against the Java 8 API (`--release 8`,
ADR-0003); the `xml` module compiles the `javax` variant with `--release 8` and the `jakarta` variant
with `--release 11` in one run (ADR-0002).

```bash
mvn clean install
mvn -pl core test
```

```bash
mvn -Pram -DskipTests -Djava.awt.headless=true clean install    # the full build as used locally
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