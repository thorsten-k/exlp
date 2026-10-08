# Technical Architecture

This file describes the currently applicable state of the technical architecture. It is a **display location** and
does not hold its own value: platform, language, frameworks, build environment, and their pinned versions are held
by a **constraint** (`doc/requirements/constraints/`) or a **decision** (`doc/decisions/`); the **status** is held by
the YAML header of the respective requirement or decision.

## Structure

The application is a Maven multi-module project. The modules are layered, and the dependencies point in **only one**
direction:

`interfaces` → `xml` → `util` → `core` → `shell` → `ejb`

Each layer accesses only the layers to its left. The version management and the auxiliary modules form no layer:
`bom` only manages dependency versions, while `client` (standalone run), `test` (test support), and `addon`
(optional extensions) build on the same modules. `maven` (build tooling) is consumed as a plugin and depends on no
module of the chain, so every module can bind its goals.

## Modules

| Module | Responsibility |
|---|---|
| `bom` | Version management: central dependency versions for all modules |
| `interfaces` | Contracts: event, listener, parser, and handler interfaces, plus exceptions |
| `xml` | Data exchange: JAXB binding and transfer models (XML, JSON) |
| `util` | Reuse: helpers for I/O, files, XML, network and JMS, processes, and compression |
| `core` | Pipeline: listeners, parsers, handlers, and events |
| `shell` | Operating system: shell, OS, and command wrappers, process spawning |
| `ejb` | Integration: EJB facade beans for the add-ons |
| `client` | Standalone run: config-pointer tray and connections |
| `maven` | Build tooling: Maven plugin (goals `mvnVersionIgnore` and `reachabilityMetadata`) |
| `test` | Test support: base classes for XML tests |
| `addon` | Optional extensions (standalone module, not part of the root aggregator) |
| `doc` | Documentation |

## Components and Patterns

- **Pipeline:** a listener reads a source, a parser turns it into events, and a handler chain processes them;
  `LogEvent` is the transported unit.
- **Source adaptation:** listeners (`core.listener`) adapt different inputs—file, directory, console/string, tail,
  HTTP, and XML.
- **Event handling:** handlers (`core.handler`) form a chain—debug, list, queue, save, and void, with a result
  container.
- **Factories:** factories create XML and JSON models as well as configuration objects.
- **Configuration:** `ConfigKey`, `Configuration`, and `Profile` name the configuration; `ExlpCentralConfigPointer`
  points to the external configuration.

## Flow

- **Input:** a `LogListener` reads from a source (file, directory, console, compressed file, HTTP) and produces raw
  records.
- **Parsing:** a `LogParser` converts the records into `LogEvent`s.
- **Processing:** a `LogEventHandler` receives the events and forwards or processes them further.
- **Forwarding:** events can be passed on to destinations such as files, JMS, XMPP, or EJB.

## Technology

- **Language:** Java 8.
- **Build:** Maven multi-module build with a BOM for version management.
- **XML:** JAXB and JDOM.
- **JSON:** Jackson.
- **Logging:** SLF4J as the facade with Log4j 2 as the backend.
- **Messaging:** JMS for point-to-point and topic messaging; JeroMQ (ZeroMQ) in the client.
- **Integration:** EJB and JPA APIs in the `ejb` module.
- **Testing:** JUnit.

## Authoritative Locations

- Platform, language, frameworks, build environment, and pinned versions: **constraint**
  (`doc/requirements/constraints/`) or **decision** (`doc/decisions/`).
- Build of the project: **constraint** (`doc/requirements/constraints/`).
- Verification of the project: **constraint** (`doc/requirements/constraints/`).
- Selecting a technology, a protocol, or a storage format: **decision** (`doc/decisions/`).
- Behavior and values: the responsible **requirement** (`doc/requirements/`).
