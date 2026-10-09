---
id: FR-002
title: Apply custom namespace prefixes when marshalling XML
type: functional
status: proposed
priority: should
depends_on: []
related: []
---

# FR-002: Apply custom namespace prefixes when marshalling XML

## Requirement

The JAXB utility of the `util` module applies namespace prefixes of a mapper configured for it
(AC-FR-002-01).

- The configured mapper determines the prefix of each namespace of the written XML (AC-FR-002-01).
- A mapper that the JAXB RI of the variant rejects does not prevent marshalling; the XML is written
  without custom prefixes (AC-FR-002-02).
- A rejected mapper is reported as a warning that names the class of the mapper (AC-FR-002-02).
- Without a configured mapper the utility marshals with the defaults of the JAXB RI (AC-FR-002-03).
- The mapper is configured once for the utility and applies to every marshalling method (AC-FR-002-04).
- Each JAXB variant sets the marshaller property of its own JAXB RI (Assumption 1, Assumption 2).

## Rationale

JAXB assigns namespace prefixes on its own, and consumers that compare or read the XML expect the
prefixes of the domain; a mapper that the JAXB RI rejects must not cost the written output.

## Scope

In scope:

- Applying configured prefixes during marshalling in the JAXB utility of the `util` module.
- The behavior when the JAXB RI rejects the configured mapper.

Out of scope:

- The prefixes that a concrete mapper returns.
- Correcting the prefixes of an existing JDOM document.
- Namespace prefixes while unmarshalling.

## Assumptions

1. The `javax` variant sets the marshaller property `com.sun.xml.bind.namespacePrefixMapper`.
2. The `jakarta` variant sets the marshaller property `org.glassfish.jaxb.namespacePrefixMapper`.
3. The mapper is configured through the static `setNsPrefixMapper` method of the utility.

## Open Questions

None.

## Decided Questions

None.

## Acceptance Criteria

### AC-FR-002-01: Apply the prefixes of the configured mapper

Given:

- A mapper is configured and a JAXB object is marshalled.

When:

- The utility writes the object.

Then:

- Each namespace of the output carries the prefix that the mapper returns.

### AC-FR-002-02: Marshal a rejected mapper without custom prefixes

Given:

- The configured mapper is not accepted by the JAXB RI of the variant.

When:

- The utility writes a JAXB object.

Then:

- The output is written without custom prefixes.
- The rejection is logged as a warning that names the class of the mapper.

### AC-FR-002-03: Marshal with the RI defaults without a mapper

Given:

- No mapper is configured.

When:

- The utility writes a JAXB object.

Then:

- The output carries the prefixes of the JAXB RI.

### AC-FR-002-04: Apply one configuration to every marshalling method

Given:

- A mapper is configured once.

When:

- Each marshalling method of the utility writes a JAXB object.

Then:

- Every output carries the prefixes of the mapper.

### AC-FR-002-05: Apply prefixes in both JAXB variants

Given:

- A mapper of the variant is configured and the variant writes a JAXB object.

When:

- The `javax` variant and the `jakarta` variant each write the object.

Then:

- Each output carries the prefixes of its mapper.

## Dependencies

None.

## Evidence

### Implementation

- open: `util/src/main/java/org/exlp/util/jx/JaxbUtil.java` – applies the configured mapper (AC-FR-002-01)
- open: `util/src/main/java/org/exlp/util/jk/JaxbUtil.java` – applies the configured mapper (AC-FR-002-01)

### Tests

- `util/src/test/java/org/exlp/util/jx` – open: a test asserts that a mapper accepted by the `javax`
  RI prefixes the output (AC-FR-002-01)
- `util/src/test/java/org/exlp/util/jx` – open: a test asserts that a mapper rejected by the `javax`
  RI leaves the output without custom prefixes and logs a warning (AC-FR-002-02)
- `util/src/test/java/org/exlp/util/jk` – open: a test asserts that a mapper accepted by the `jakarta`
  RI prefixes the output (AC-FR-002-05)
- `util/src/test/java/org/exlp/util/jk` – open: a test asserts that a mapper rejected by the `jakarta`
  RI leaves the output without custom prefixes and logs a warning (AC-FR-002-05)

### Documentation

- open: `README.md` – configuring a namespace prefix mapper is documented (AC-FR-002-01)
