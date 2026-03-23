---
agent: agent
name: TDD Green step
description: This prompt is used to implement production code to make failing tests pass in a TDD workflow for an AI agent
argument-hint: Implement production code to make the following failing test(s) pass in a TDD workflow: {test_reference}
tools: ['execute/getTerminalOutput', 'execute/runInTerminal', 'read/problems', 'read/readFile', 'read/terminalSelection', 'read/terminalLastCommand', 'edit/createDirectory', 'edit/createFile', 'edit/editFiles', 'search', 'upstash/context7/*', 'todo']
model: GPT-5 mini (copilot)
---

# Green TDD step prompt

## CRITICAL
- DO NOT create, modify, or generate any test code in this step.
- This step is strictly production code only: implement code to make failing tests pass.
- Keep implementation minimal and focused on making the tests pass (no over-engineering).

## Instructions
1. Identify the failing test(s) that need to pass.
    - If a test reference is provided, locate the test file and understand the test expectations.
    - Analyze the test assertions to determine what production code is required.
2. Analyze the existing codebase to understand the architecture, patterns, and conventions used.
    - Review the module structure (Domain, Application, or Infrastructure).
    - Check for existing implementations that follow similar patterns.
3. Implement the minimum production code required to make all tests pass.
    - Create new domain entities, value objects, services, or use cases as needed.
    - Implement repository interfaces or adapters in the Infrastructure module if required.
    - Add API controllers or DTOs in the Application module if required.
4. Ensure all implementations follow the coding guidelines in `docs\agents\java-coding-guidelines.md` and architectural patterns.
5. Run the tests to confirm they all pass.
6. Return an output in JSON format of the newly created or modified code to help with the next steps in the TDD workflow such as refactoring. 

## Requirements
- You **MUST** follow the guidelines and architectural patterns defined for the module you are working on.
- **NEVER** modify any test code in this step. Your ONLY goal is to implement production code.
- **MUST** ensure all failing tests pass when executed.
- Implement only what is necessary to make the tests pass (YAGNI principle).
- Follow the project's coding style, naming conventions, and architecture patterns.
- All new code **MUST** be well-documented with Javadoc comments.

## Examples

### Domain implementation example : Production code created from failing test

Input : 
Test file: `domain/src/test/java/com/example/domain/contact/ContactExportUseCaseTest.java`
Failing test: `shouldProduceExportDtoWhenContactsExist()`

Expected Output : 

- Create `domain/src/main/java/com/example/domain/contact/usecase/ExportContactUseCase.java`:

```java
package com.example.domain.contact.usecase;

import com.example.domain.contact.Contact;
import com.example.domain.contact.ContactRepository;
import com.example.domain.contact.dto.ContactExportDto;
import com.example.domain.contact.query.ExportContactQuery;

/**
 * Use case for exporting contacts.
 * Retrieves all contacts for a user and generates an export DTO.
 */
public class ExportContactUseCase {

    private final ContactRepository contactRepository;

    public ExportContactUseCase(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Executes the export contacts query.
     *
     * @param query the export contact query containing user ID
     * @return the export DTO containing all contacts for the user
     */
    public ContactExportDto execute(ExportContactQuery query) {
        var contacts = contactRepository.findByUserId(query.getUserId());
        return new ContactExportDto(contacts);
    }
}
```

- Create `domain/src/main/java/com/example/domain/contact/query/ExportContactQuery.java`:

```java
package com.example.domain.contact.query;

/**
 * Query to export contacts for a specific user.
 */
public record ExportContactQuery(String userId) {
    // Query record implementation
}
```

- Create `domain/src/main/java/com/example/domain/contact/dto/ContactExportDto.java`:

```java
package com.example.domain.contact.dto;

import java.util.List;
import com.example.domain.contact.Contact;

/**
 * Data transfer object for exporting contacts.
 */
public class ContactExportDto {

    private final List<Contact> contacts;

    public ContactExportDto(List<Contact> contacts) {
        this.contacts = contacts;
    }

    /**
     * Gets the exported contacts.
     *
     * @return list of contacts
     */
    public List<Contact> getContacts() {
        return contacts;
    }
}
```

- Create/Update `domain/src/main/java/com/example/domain/contact/ContactRepository.java` (if it doesn't exist):

```java
package com.example.domain.contact;

import java.util.List;

/**
 * Port for contact persistence operations.
 */
public interface ContactRepository {

    /**
     * Finds all contacts for a specific user.
     *
     * @param userId the user ID
     * @return list of contacts for the user
     */
    List<Contact> findByUserId(String userId);
}
```
