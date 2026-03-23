# Testing Guidelines

Strict rules for writing and organizing tests:

- **Test Coverage**
  - All business logic must be covered by unit tests.
  - Integration tests are required for module boundaries and external dependencies.
  - Aim for high code coverage, but never sacrifice test quality for coverage metrics.

- **Test Structure**
  - Place unit tests in the corresponding module’s `src/test/java` directory, mirroring the main package structure.
  - Name test classes with the `*Test` suffix (e.g., `OrderServiceTest`).

- **Test Methods**
  - Method names must clearly describe the scenario and expected outcome.
  - Use the Given-When-Then pattern in all tests.
  - Separate each part by a commentary:
    //given
    ...
    //when
    ...
    //then
    ...

  **Example:**
  ```java
  @Test
  void givenValidOrderId_whenGetOrder_thenReturnsOrder() {
      // Given
      // When
      // Then
  }
  ```

- **Isolation**
  - Unit tests must not depend on external systems (e.g., databases, APIs).
  - Use mocks or stubs for dependencies.

- **Assertions**
  - Use expressive assertions; avoid bare `assertTrue` or `assertFalse` unless necessary.

- **Test Data**
  - Use builders or factory methods for creating test data.
  - Avoid hardcoding values directly in test methods.

- **CI Integration**
  - All tests must pass before merging code.
  - Failing or ignored tests are not allowed in the main branch.

---

End of testing guidelines.
