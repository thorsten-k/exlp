---
id: FR-002
title: Apply custom namespace prefixes when marshalling XML
type: functional
status: implemented
priority: should
depends_on: []
related: []
---

# FR-002: Apply custom namespace prefixes when marshalling XML

## Requirement

The JAXB utility of the `util` module applies the namespace prefixes of a mapper configured for it in
both JAXB variants (AC-FR-002-01).

- The configured mapper determines the prefix of each namespace of the written XML (AC-FR-002-01).
- The utility adapts the configured mapper to the RI type of the variant before it sets the marshaller
  property (Assumption 4).
- A mapper that the JAXB RI of the variant rejects does not prevent marshalling; the XML is written
  without custom prefixes (AC-FR-002-02).
- A rejected mapper is logged as the warning `Namespace prefix mapper <class> rejected by the JAXB RI`
  (AC-FR-002-02).
- Without a configured mapper the utility marshals with the defaults of the JAXB RI (AC-FR-002-03).
- The mapper is configured once for the utility and applies to every method that writes XML
  (AC-FR-002-04).
- Each JAXB variant sets the marshaller property of its own JAXB RI (Assumption 1, Assumption 2).

## Rationale

JAXB assigns namespace prefixes on its own, and consumers that compare or read the XML expect the
prefixes of the domain; a mapper that the JAXB RI rejects must not cause the written output to be lost.

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
2. The `jakarta` variant sets the marshaller property `org.glassfish.jaxb.namespacePrefixMapper` in
   every method that writes XML.
3. The mapper is configured through the static `setNsPrefixMapper` method of the utility; its parameter
   type is `org.exlp.interfaces.io.NsPrefixMapperInterface`.
4. The utility adapts the configured mapper to the RI type of the variant before it sets the marshaller
   property: `com.sun.xml.bind.marshaller.NamespacePrefixMapper` for `javax` and
   `org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper` for `jakarta`.

## Open Questions

None.

## Decided Questions

1. **Rejected mapper**
   Question: When the JAXB RI rejects the configured mapper, does marshalling continue without custom
   prefixes or does it fail?
   Decision: Marshalling continues without custom prefixes and the rejection is logged as a warning that
   names the class of the mapper.
   Applies in: AC-FR-002-02

## Acceptance Criteria

### AC-FR-002-01: Apply the prefixes of the configured mapper in both variants

Given:

- A mapper that the JAXB RI of the variant accepts is configured.
- A JAXB object is marshalled.
- The `javax` variant and the `jakarta` variant each write the object.

When:

- The utility writes the object.

Then:

- Each namespace of each output carries the prefix that the mapper returns.

### AC-FR-002-02: Marshal a rejected mapper without custom prefixes

Given:

- The configured mapper is rejected by the JAXB RI of the variant.

When:

- The utility writes a JAXB object.

Then:

- The output is written without custom prefixes.
- The rejection is logged as the warning `Namespace prefix mapper <class> rejected by the JAXB RI`.

### AC-FR-002-03: Marshal with the RI defaults without a mapper

Given:

- No mapper is configured.

When:

- The utility writes a JAXB object.

Then:

- The output carries the prefixes of the JAXB RI.

### AC-FR-002-04: Apply one configuration to any method that writes XML

Given:

- A mapper is configured once.

When:

- Any public method of the utility that writes XML writes a JAXB object.

Then:

- Every output carries the prefixes of the mapper.

## Evidence

### Implementation

- `util/src/main/java/org/exlp/util/jx/NsPrefixMapperAdapter.java` – adapts a configured mapper to the
  `com.sun.xml.bind.marshaller.NamespacePrefixMapper` of the `javax` RI (AC-FR-002-01)
- `util/src/main/java/org/exlp/util/jk/NsPrefixMapperAdapter.java` – adapts a configured mapper to the
  `org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper` of the `jakarta` RI (AC-FR-002-01)
- `util/src/main/java/org/exlp/util/jx/JaxbUtil.java` – applies the mapper in every method that writes
  XML, logs the warning for a rejected mapper, and uses the RI defaults without a mapper
  (AC-FR-002-01, AC-FR-002-02, AC-FR-002-03, AC-FR-002-04)
- `util/src/main/java/org/exlp/util/jk/JaxbUtil.java` – sets `org.glassfish.jaxb.namespacePrefixMapper`
  and applies the mapper in every method that writes XML (AC-FR-002-01, Assumption 2)

### Tests

- `util/src/test/java/org/exlp/util/jx/TestJaxbUtilNsPrefix.java` – `appliesPrefixesOfConfiguredMapper`
  asserts the prefixes of the `javax` RI (AC-FR-002-01)
- `util/src/test/java/org/exlp/util/jx/TestJaxbUtilNsPrefix.java` – `writesWithoutCustomPrefixesForRejectedMapper`
  asserts no custom prefixes and the warning for the `javax` RI (AC-FR-002-02)
- `util/src/test/java/org/exlp/util/jx/TestJaxbUtilNsPrefix.java` – `writesRiDefaultsWithoutMapper`
  asserts the RI defaults (AC-FR-002-03)
- `util/src/test/java/org/exlp/util/jx/TestJaxbUtilNsPrefix.java` – `appliesOneConfigurationToEveryMethod`
  asserts every method that writes XML (AC-FR-002-04)
- `util/src/test/java/org/exlp/util/jk/TestJaxbUtilNsPrefix.java` – `appliesPrefixesOfConfiguredMapper`
  asserts the prefixes of the `jakarta` RI (AC-FR-002-01)
- `util/src/test/java/org/exlp/util/jk/TestJaxbUtilNsPrefix.java` – `writesWithoutCustomPrefixesForRejectedMapper`
  asserts no custom prefixes and the warning for the `jakarta` RI (AC-FR-002-02)
- `util/src/test/java/org/exlp/util/jk/TestJaxbUtilNsPrefix.java` – `writesRiDefaultsWithoutMapper`
  asserts the RI defaults (AC-FR-002-03)
- `util/src/test/java/org/exlp/util/jk/TestJaxbUtilNsPrefix.java` – `appliesOneConfigurationToEveryMethod`
  asserts every method that writes XML (AC-FR-002-04)
- `mvn -o -pl util test` – 59 tests pass (AC-FR-002-01)

### Documentation

- `README.md` – configuring a namespace prefix mapper is documented for both JAXB variants (AC-FR-002-01)
