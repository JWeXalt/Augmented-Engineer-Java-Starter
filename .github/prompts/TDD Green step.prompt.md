---
agent: agent
name: TDD Green step
description: This prompt is used to make failing tests pass using the "as if you meant it" technique in a TDD workflow, writing only test-side code.
argument-hint: Make the failing tests pass for the following scenario: {scenario_description}
tools: ['execute/getTerminalOutput', 'execute/runInTerminal', 'read/problems', 'read/readFile', 'read/terminalSelection', 'read/terminalLastCommand', 'edit/createDirectory', 'edit/createFile', 'edit/editFiles', 'search', 'upstash/context7/*', 'todo']
model: GPT-5 mini (copilot)
---

# Green TDD step prompt

## CRITICAL
- DO NOT create, modify, or generate any production code in this step.
- This step is strictly test-side: make failing tests pass by writing code **only** inside the test source directories (`src/test/`).
- Production source directories (`src/main/`) are **read-only** in this step.

## Concept: "As If You Meant It"

The **"as if you meant it"** technique consists of making a failing test pass as quickly as possible by writing the simplest possible implementation **inside the test scope** — using fakes, stubs, in-memory implementations, or anonymous classes — without touching any production file.

The goal is **not** to build the real implementation. The goal is to validate the test design, confirm the test is correctly written, and turn it green by faking what the production code would eventually do.

## Instructions

1. Identify all currently failing tests.
   - Run the test suite to get the list of red tests.
   - Focus only on the tests that were introduced in the Red step.
2. Read and understand the failing tests carefully.
   - Identify what types, interfaces, collaborators, and behaviors the tests depend on.
   - Identify what is missing (types that don't compile, method signatures not found, missing fixtures, etc.).
3. Make the tests compile and pass using **test-side code only**:
   - Create or update **fixture classes**, **test state helpers**, **in-memory fakes**, or **test doubles** inside the test source tree.
   - If a production interface/type needs to be instantiated, create a **fake or stub implementation** in the test directory, not in `src/main/`.
   - Use the **simplest logic possible**: hardcoded returns, in-memory collections, or minimal conditional logic are acceptable here.
4. Run the tests again to confirm all previously failing tests are now **green**.
5. If any test is still failing, iterate on the test-side code only until all tests pass.

## Requirements
- You **MUST** follow the testing guidelines in `docs\agents\testing-guidelines.md`.
- You **MUST NOT** write, modify, or delete any file under `src/main/`.
- Fake implementations **MUST** live under `src/test/`, in a package that mirrors the production package (e.g., `com.example.domain.test.fake`).
- Fixture and test state classes introduced here **MUST** be reusable in subsequent TDD steps.
- The test suite **MUST** be fully green at the end of this step.

## Examples

### Domain test example: making a failing use case test pass

Failing test (from Red step):

```java
@Test
void shouldProduceExportDtoWhenContactsExist() {
    TestState<User> userTestState = fixture.getUserTestState();
    TestState<Contact> contactTestState = fixture.getContactTestState();
    UseCaseHandler<ExportContactQuery, ContactExportDto> handler = fixture.getUseCaseHandler();

    User user = new User("user1");
    userTestState.add(user);
    List<Contact> contacts = IntStream.range(0, 20)
            .mapToObj(i -> new Contact("Contact " + i, "contact" + i + "@example.com"))
            .collect(Collectors.toList());
    contacts.forEach(contactTestState::add);

    ExportContactQuery query = new ExportContactQuery(user.getId());
    ContactExportDto exportDto = handler.execute(query);

    assertThat(exportDto).isNotNull();
    assertThat(exportDto.getContacts()).hasSize(20);
}
```

Expected Output (test-side code only):

- `domain/src/test/java/com/example/domain/test/state/TestState.java` — generic in-memory store
- `domain/src/test/java/com/example/domain/test/fake/InMemoryContactRepository.java` — fake implementing the `ContactRepository` port
- `domain/src/test/java/com/example/domain/contact/ContactExportFixture.java` — wires the use case with fake collaborators

```java
// ContactExportFixture.java
package com.example.domain.contact;

import com.example.domain.test.fake.InMemoryContactRepository;
import com.example.domain.test.state.TestState;

public class ContactExportFixture {

    private final TestState<User> userTestState = new TestState<>();
    private final TestState<Contact> contactTestState = new TestState<>();
    private final InMemoryContactRepository contactRepository;
    private final UseCaseHandler<ExportContactQuery, ContactExportDto> handler;

    public ContactExportFixture() {
        this.contactRepository = new InMemoryContactRepository(contactTestState);
        this.handler = new ContactExportUseCaseHandler(contactRepository);
    }

    public TestState<User> getUserTestState() { return userTestState; }
    public TestState<Contact> getContactTestState() { return contactTestState; }
    public UseCaseHandler<ExportContactQuery, ContactExportDto> getUseCaseHandler() { return handler; }
}
```

```java
// InMemoryContactRepository.java
package com.example.domain.test.fake;

import com.example.domain.contact.Contact;
import com.example.domain.contact.ContactRepository;
import com.example.domain.test.state.TestState;

import java.util.List;

public class InMemoryContactRepository implements ContactRepository {

    private final TestState<Contact> state;

    public InMemoryContactRepository(TestState<Contact> state) {
        this.state = state;
    }

    @Override
    public List<Contact> findAll() {
        return state.getAll();
    }
}
```

## Output

Once all tests are green, **you MUST produce the following JSON artifact** as the final output of this step.
It will be used as input for the **TDD Refactor step**.

```json
{
  "scenario": "<brief description of the scenario>",
  "module": "<domain | application | infrastructure>",
  "produced_files": [
    {
      "path": "<module>/src/test/java/<package path>/<ClassName>.java",
      "class": "<fully.qualified.ClassName>",
      "role": "<test | fixture | fake | state_helper>"
    }
  ],
  "production_candidates": [
    {
      "fake_path": "<module>/src/test/java/<package>/<FakeClass>.java",
      "target_path": "<module>/src/main/java/<package>/<RealClass>.java",
      "class": "<fully.qualified.RealClass>",
      "implements": ["<fully.qualified.InterfaceName>"],
      "type": "<use_case | entity | value_object | domain_service | port_implementation>"
    }
  ],
  "fixtures_to_update": [
    {
      "path": "<module>/src/test/java/<package>/<Fixture>.java",
      "class": "<fully.qualified.Fixture>",
      "update": "<description of what must change to wire the real implementation instead of the fake>"
    }
  ],
  "test_doubles_to_remove": [
    {
      "path": "<module>/src/test/java/<package>/<FakeClass>.java",
      "reason": "<e.g., replaced by production implementation in src/main/>"
    }
  ]
}
```

**Rules for filling the JSON:**
- `produced_files`: list **every** file created or modified during this step, with its role.
- `production_candidates`: include only classes that contain **business logic or implement domain ports** that belong in `src/main/` (e.g., use case handlers, domain services). Do **not** include in-memory repository fakes here — those are permanent test infrastructure.
- `fixtures_to_update`: list fixture classes that will need to swap the fake for the real implementation.
- `test_doubles_to_remove`: list only the fakes that will become **obsolete** once their real counterpart exists in `src/main/`. `fixture` and `state_helper` roles must **never** appear here.

### Example JSON output

```json
{
  "scenario": "Successfully export contacts",
  "module": "domain",
  "produced_files": [
    { "path": "domain/src/test/java/com/example/domain/contact/ContactExportUseCaseTest.java", "class": "com.example.domain.contact.ContactExportUseCaseTest", "role": "test" },
    { "path": "domain/src/test/java/com/example/domain/contact/ContactExportFixture.java", "class": "com.example.domain.contact.ContactExportFixture", "role": "fixture" },
    { "path": "domain/src/test/java/com/example/domain/test/state/TestState.java", "class": "com.example.domain.test.state.TestState", "role": "state_helper" },
    { "path": "domain/src/test/java/com/example/domain/test/fake/InMemoryContactRepository.java", "class": "com.example.domain.test.fake.InMemoryContactRepository", "role": "fake" },
    { "path": "domain/src/test/java/com/example/domain/contact/ContactExportUseCaseHandler.java", "class": "com.example.domain.contact.ContactExportUseCaseHandler", "role": "fake" }
  ],
  "production_candidates": [
    {
      "fake_path": "domain/src/test/java/com/example/domain/contact/ContactExportUseCaseHandler.java",
      "target_path": "domain/src/main/java/com/example/domain/contact/ContactExportUseCaseHandler.java",
      "class": "com.example.domain.contact.ContactExportUseCaseHandler",
      "implements": ["com.example.domain.contact.UseCaseHandler"],
      "type": "use_case"
    }
  ],
  "fixtures_to_update": [
    {
      "path": "domain/src/test/java/com/example/domain/contact/ContactExportFixture.java",
      "class": "com.example.domain.contact.ContactExportFixture",
      "update": "Replace import and instantiation of the test-local ContactExportUseCaseHandler with the real class from src/main/"
    }
  ],
  "test_doubles_to_remove": [
    {
      "path": "domain/src/test/java/com/example/domain/contact/ContactExportUseCaseHandler.java",
      "reason": "Replaced by production implementation in domain/src/main/"
    }
  ]
}
```

> Note: `InMemoryContactRepository` is **not** in `test_doubles_to_remove` — it is permanent test infrastructure used to keep domain unit tests isolated from the database.
