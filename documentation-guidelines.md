# Documentation Guidelines

Strict rules for documenting code:

- **Class and Method Documentation**
  - Every public class and method must have a Javadoc comment describing its purpose, parameters, and return value.
  - Private methods should be commented if their purpose is not obvious.

  **Example:**
  ```java
  /**
   * Processes an order and updates its status.
   * @param order the order to process
   * @return the updated order
   */
  public Order processOrder(Order order) { ... }
  ```

- **Inline Comments**
  - Use inline comments sparingly and only to explain complex or non-obvious logic.
  - Do not restate what the code does; explain why it is done that way.

- **TODOs and FIXMEs**
  - Use `TODO` for planned improvements and `FIXME` for known issues.
  - All TODOs and FIXMEs must include the author’s initials and a date.

  **Example:**
  ```java
  // TODO [JW-2026-03-16]: Refactor to use async processing
  ```

- **API Documentation**
  - All REST endpoints must be documented using OpenAPI annotations in the Application module.

---

End of documentation guidelines.
