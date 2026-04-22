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

The **"as if you meant it"** technique consists of making a failing test pass as quickly as possible by writing the simplest possible implementation **inside the test scope** — without touching any production file.

Because the Red step writes Mockito-based tests (`@Mock`, `@InjectMocks`, `@ExtendWith(MockitoExtension.class)`), the missing pieces are **not** fakes or fixtures but the **types themselves**: domain entities, value objects, DTOs, port interfaces, and the class under test. These must be created as minimal stubs in `src/test/`, just complete enough to compile and satisfy the Mockito annotations.

The goal is **not** to build the real implementation. The goal is to validate the test design, confirm the test is correctly written, and turn it green using the stubs while Mockito handles the mocking of dependencies.

## Instructions

1. Identify all currently failing tests.
   - Run the test suite to get the list of red tests.
   - Focus only on the tests that were introduced in the Red step.
2. Read and understand the failing tests carefully.
   - Identify what types, interfaces, collaborators, and behaviors the tests depend on.
   - Identify what is missing (types that don't compile, method signatures not found, missing fixtures, etc.).
3. Make the tests compile and pass using **test-side code only**:
   - Identify every missing type referenced in the test: domain entities, value objects, DTOs, command/query objects, port interfaces, and the class under test (`@InjectMocks`).
   - Create a **minimal stub** for each missing type inside `src/test/`, in the same package the test expects it to be.
   - For the class under test, implement just enough: declare the Mockito-injectable fields (matching the `@Mock` types), a constructor accepting them, and an `execute()` / command method with the simplest logic that delegates to the injected mocks and returns the expected type.
   - For port interfaces, declare only the method signatures used by the test.
   - Use the **simplest logic possible**: single-method delegations to mocked ports are the norm.
4. Run the tests again to confirm all previously failing tests are now **green**.
5. If any test is still failing, iterate on the test-side code only until all tests pass.

## Requirements
- You **MUST** follow the testing guidelines in `docs\agents\testing-guidelines.md`.
- You **MUST NOT** write, modify, or delete any file under `src/main/`.
- Stub types **MUST** live under `src/test/`, in the package the test already imports them from.
- Stub classes **MUST** be minimal: no more logic than what the test exercises.
- The test suite **MUST** be fully green at the end of this step.

## Examples

### Domain test example: making a Mockito-based failing use case test pass

Failing test (from Red step):

```java
@ExtendWith(MockitoExtension.class)
class ContactExportUseCaseTest {

    @Mock
    private ContactRepositoryPort contactRepositoryPort;

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private ContactExportService contactExportUseCase;

    @Test
    void shouldProduceExportDtoWhenContactsExist() {
        // Given a user with 20 contacts
        User user = new User("user-1");
        List<Contact> contacts = IntStream.range(0, 20)
                .mapToObj(i -> new Contact("Contact " + i, "contact" + i + "@example.com"))
                .collect(Collectors.toList());
        when(userRepositoryPort.findById("user-1")).thenReturn(Optional.of(user));
        when(contactRepositoryPort.findAllByUserId("user-1")).thenReturn(contacts);

        // When executing a query to fetch contacts
        ExportContactQuery query = new ExportContactQuery("user-1");
        ContactExportDto exportDto = contactExportUseCase.execute(query);

        // Then the system retrieves all 20 contacts and generates an export DTO
        assertNotNull(exportDto);
        assertEquals(20, exportDto.getContacts().size());
    }
}
```

Expected Output (test-side code only) — one stub per missing type:

- `domain/src/test/java/com/example/domain/contact/port/ContactRepositoryPort.java` — stub port interface
- `domain/src/test/java/com/example/domain/user/UserRepositoryPort.java` — stub port interface
- `domain/src/test/java/com/example/domain/user/User.java` — stub entity
- `domain/src/test/java/com/example/domain/contact/Contact.java` — stub entity
- `domain/src/test/java/com/example/domain/contact/ExportContactQuery.java` — stub query object
- `domain/src/test/java/com/example/domain/contact/ContactExportDto.java` — stub DTO
- `domain/src/test/java/com/example/domain/contact/ContactExportService.java` — stub service (class under test)

```java
// ContactRepositoryPort.java
package com.example.domain.contact.port;

import com.example.domain.contact.Contact;
import java.util.List;

public interface ContactRepositoryPort {
    List<Contact> findAllByUserId(String userId);
}
```

```java
// UserRepositoryPort.java
package com.example.domain.user;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findById(String userId);
}
```

```java
// User.java
package com.example.domain.user;

public class User {
    private final String id;
    public User(String id) { this.id = id; }
    public String getId() { return id; }
}
```

```java
// Contact.java
package com.example.domain.contact;

public class Contact {
    private final String name;
    private final String email;
    public Contact(String name, String email) { this.name = name; this.email = email; }
}
```

```java
// ExportContactQuery.java
package com.example.domain.contact;

public class ExportContactQuery {
    private final String userId;
    public ExportContactQuery(String userId) { this.userId = userId; }
    public String getUserId() { return userId; }
}
```

```java
// ContactExportDto.java
package com.example.domain.contact;

import java.util.List;

public class ContactExportDto {
    private final List<Contact> contacts;
    public ContactExportDto(List<Contact> contacts) { this.contacts = contacts; }
    public List<Contact> getContacts() { return contacts; }
}
```

```java
// ContactExportService.java — stub service, class under test
package com.example.domain.contact;

import com.example.domain.contact.port.ContactRepositoryPort;
import com.example.domain.user.UserRepositoryPort;
import java.util.List;

public class ContactExportService {

    private final ContactRepositoryPort contactRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    public ContactExportService(ContactRepositoryPort contactRepositoryPort,
                                UserRepositoryPort userRepositoryPort) {
        this.contactRepositoryPort = contactRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    public ContactExportDto execute(ExportContactQuery query) {
        List<Contact> contacts = contactRepositoryPort.findAllByUserId(query.getUserId());
        return new ContactExportDto(contacts);
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
      "role": "<test | stub>"
    }
  ],
  "production_candidates": [
    {
      "stub_path": "<module>/src/test/java/<package>/<StubClass>.java",
      "target_path": "<module>/src/main/java/<package>/<RealClass>.java",
      "class": "<fully.qualified.RealClass>",
      "implements": ["<fully.qualified.InterfaceName>"],
      "type": "<use_case | entity | value_object | domain_service | port | port_implementation>"
    }
  ],
  "test_doubles_to_remove": [
    {
      "path": "<module>/src/test/java/<package>/<StubClass>.java",
      "reason": "<e.g., replaced by production implementation in src/main/>"
    }
  ]
}
```

**Rules for filling the JSON:**
- `produced_files`: list **every** file created or modified during this step, with its role (`test` for test classes, `stub` for all other stubs created in `src/test/`).
- `production_candidates`: include every stub that contains **business logic, domain types, or port definitions** that belong in `src/main/`. This includes the service/use-case class, domain entities, value objects, DTOs, and port interfaces. List each as a separate entry.
- `test_doubles_to_remove`: list the stub files that will become **obsolete** once their real counterparts exist in `src/main/`. This is typically all `stub` role files.

### Example JSON output

```json
{
  "scenario": "Successfully export contacts",
  "module": "domain",
  "produced_files": [
    { "path": "domain/src/test/java/com/example/domain/contact/ContactExportUseCaseTest.java", "class": "com.example.domain.contact.ContactExportUseCaseTest", "role": "test" },
    { "path": "domain/src/test/java/com/example/domain/contact/port/ContactRepositoryPort.java", "class": "com.example.domain.contact.port.ContactRepositoryPort", "role": "stub" },
    { "path": "domain/src/test/java/com/example/domain/user/UserRepositoryPort.java", "class": "com.example.domain.user.UserRepositoryPort", "role": "stub" },
    { "path": "domain/src/test/java/com/example/domain/user/User.java", "class": "com.example.domain.user.User", "role": "stub" },
    { "path": "domain/src/test/java/com/example/domain/contact/Contact.java", "class": "com.example.domain.contact.Contact", "role": "stub" },
    { "path": "domain/src/test/java/com/example/domain/contact/ExportContactQuery.java", "class": "com.example.domain.contact.ExportContactQuery", "role": "stub" },
    { "path": "domain/src/test/java/com/example/domain/contact/ContactExportDto.java", "class": "com.example.domain.contact.ContactExportDto", "role": "stub" },
    { "path": "domain/src/test/java/com/example/domain/contact/ContactExportService.java", "class": "com.example.domain.contact.ContactExportService", "role": "stub" }
  ],
  "production_candidates": [
    { "stub_path": "domain/src/test/java/com/example/domain/contact/ContactExportService.java", "target_path": "domain/src/main/java/com/example/domain/contact/ContactExportService.java", "class": "com.example.domain.contact.ContactExportService", "implements": [], "type": "use_case" },
    { "stub_path": "domain/src/test/java/com/example/domain/contact/port/ContactRepositoryPort.java", "target_path": "domain/src/main/java/com/example/domain/contact/port/ContactRepositoryPort.java", "class": "com.example.domain.contact.port.ContactRepositoryPort", "implements": [], "type": "port" },
    { "stub_path": "domain/src/test/java/com/example/domain/user/UserRepositoryPort.java", "target_path": "domain/src/main/java/com/example/domain/user/UserRepositoryPort.java", "class": "com.example.domain.user.UserRepositoryPort", "implements": [], "type": "port" },
    { "stub_path": "domain/src/test/java/com/example/domain/user/User.java", "target_path": "domain/src/main/java/com/example/domain/user/User.java", "class": "com.example.domain.user.User", "implements": [], "type": "entity" },
    { "stub_path": "domain/src/test/java/com/example/domain/contact/Contact.java", "target_path": "domain/src/main/java/com/example/domain/contact/Contact.java", "class": "com.example.domain.contact.Contact", "implements": [], "type": "entity" },
    { "stub_path": "domain/src/test/java/com/example/domain/contact/ExportContactQuery.java", "target_path": "domain/src/main/java/com/example/domain/contact/ExportContactQuery.java", "class": "com.example.domain.contact.ExportContactQuery", "implements": [], "type": "value_object" },
    { "stub_path": "domain/src/test/java/com/example/domain/contact/ContactExportDto.java", "target_path": "domain/src/main/java/com/example/domain/contact/ContactExportDto.java", "class": "com.example.domain.contact.ContactExportDto", "implements": [], "type": "value_object" }
  ],
  "test_doubles_to_remove": [
    { "path": "domain/src/test/java/com/example/domain/contact/ContactExportService.java", "reason": "Replaced by production implementation in domain/src/main/" },
    { "path": "domain/src/test/java/com/example/domain/contact/port/ContactRepositoryPort.java", "reason": "Replaced by production interface in domain/src/main/" },
    { "path": "domain/src/test/java/com/example/domain/user/UserRepositoryPort.java", "reason": "Replaced by production interface in domain/src/main/" },
    { "path": "domain/src/test/java/com/example/domain/user/User.java", "reason": "Replaced by production entity in domain/src/main/" },
    { "path": "domain/src/test/java/com/example/domain/contact/Contact.java", "reason": "Replaced by production entity in domain/src/main/" },
    { "path": "domain/src/test/java/com/example/domain/contact/ExportContactQuery.java", "reason": "Replaced by production value object in domain/src/main/" },
    { "path": "domain/src/test/java/com/example/domain/contact/ContactExportDto.java", "reason": "Replaced by production value object in domain/src/main/" }
  ]
}
```
