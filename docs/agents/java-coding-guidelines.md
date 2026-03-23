# Java Coding Guidelines

## Introduction & Purpose

This document defines the strictly enforced Java coding guidelines for the Belair’s Buvette project. All contributors must adhere to these rules to ensure code consistency, maintainability, and alignment with our Hexagonal Architecture. These guidelines apply to all modules: Application, Domain, and Infrastructure.

**Key Principles:**
- Consistency across the codebase
- Readability and maintainability
- Clear separation of concerns (Hexagonal Architecture)
- High code quality and testability

---

## Project Structure, Module Boundaries & Hexagonal Architecture

The codebase strictly follows Hexagonal Architecture (Ports and Adapters) with clear module separation:

- **Application Module**
  - Contains REST API controllers, DTOs, and exposed endpoints.
  - Handles input validation, request mapping, and response formatting.
  - May depend on Domain and Infrastructure modules, but must not contain business logic.

- **Domain Module**
  - Contains all business logic: Entities, Value Objects, Domain Services, Ports (interfaces), and Use Cases.
  - Must not depend on Application or Infrastructure modules.
  - Defines interfaces (Ports) for all external dependencies (e.g., repositories, external services).
  - Only Use Cases and Ports are exposed to Application.

- **Infrastructure Module**
  - Contains technical implementations of Domain Ports (e.g., persistence, external APIs).
  - May depend on Domain module, but must not contain business logic.

**Strict Rules:**
- No business logic in Application or Infrastructure modules.
- No technical implementation details in Domain module.
- Cross-module dependencies must follow: Application → Domain → Infrastructure.
- Each module must have its own package structure reflecting its responsibility.
- DTOs are used in Application for API contracts; map DTOs to Domain objects at the Application boundary.
- Never expose Domain entities directly in API responses.
- Dependencies must always point inward: Infrastructure → Domain, Application → Domain.

**Example Structure:**
application/
---

  src/main/java/com/it/exalt/belair/application/controller/...

domain/

  - Use SLF4J for all logging. Do not use java.util.logging, Log4j, or other logging APIs directly.
  - Inject logger instances using the standard SLF4J pattern.

  - Declare logger as a private static final field in each class.

  **Example:**
  ```java
  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MyClass.class);
  ```

  - Log at appropriate levels: error, warn, info, debug, trace.
  - Use parameterized logging instead of string concatenation.
  - Do not log sensitive information.
  - Log exceptions with stack traces at the point of catch.

  **Example:**
  ```java
  logger.info("Processing order: {}", orderId);
  logger.error("Failed to process order", exception);
  ```



**Architecture Diagram:**
```
+-------------------+         +-------------------+         +------------------------+
|   Application     |  --->   |      Domain       |  <---   |     Infrastructure     |
| (Controllers, DTO)|         | (Entities, Ports) |         | (Repositories, Adapters)|
+-------------------+         +-------------------+         +------------------------+
```

---

## General Java Coding Standards

All Java code must strictly follow these standards:

- **Formatting**
  - Use 4 spaces for indentation. Do not use tabs.
  - Limit lines to 120 characters.
  - Place opening braces on the same line as the declaration.
  - Always use curly braces for blocks, even for single-line statements.

  **Example:**
  ```java
  if (condition) {
      doSomething();
  }
  ```

- **Imports**
  - Do not use wildcard imports. Import only what is needed.
  - Organize imports: standard Java, third-party, then project-specific, each separated by a blank line.

- **Variables and Constants**
  - Use `final` for variables that are not reassigned.
  - Constants must be `static final` and named in uppercase with underscores.

  **Example:**
  ```java
  private static final int MAX_SIZE = 100;
  ```

- **Null Handling**
  - Avoid returning or accepting `null` where possible. Use `Optional` for return values that may be absent.
  - Always check for `null` before dereferencing.

- **Annotations**
  - Place annotations on their own line above the declaration.

  **Example:**
  ```java
  @Override
  public void execute() { ... }
  ```

- **Visibility**
  - Use the most restrictive visibility possible (`private` > `protected` > package-private > `public`).
  - Fields must be `private` unless there is a justified reason.

---

## Naming Conventions

Strict naming conventions must be followed throughout the codebase:

- **Packages**
  - Use all lowercase letters, separated by dots.
  - Reflect the module and responsibility (e.g., `com.it.exalt.belair.domain.entity`).

- **Classes and Interfaces**
  - Use UpperCamelCase.
  - Interface names should be descriptive and not prefixed with “I”.
  - Abstract classes may be prefixed with “Abstract”.

  **Example:**
  ```java
  public interface OrderRepository { ... }
  public class OrderService { ... }
  public abstract class AbstractOrderProcessor { ... }
  ```

- **Methods**
  - Use lowerCamelCase.
  - Method names should be verbs or verb phrases.

  **Example:**
  ```java
  public void processOrder() { ... }
  ```

- **Variables**
  - Use lowerCamelCase.
  - Variable names must be descriptive and avoid abbreviations.

  **Example:**
  ```java
  int orderCount;
  String customerName;
  ```

- **Constants**
  - Use uppercase letters with underscores.

  **Example:**
  ```java
  private static final int MAX_RETRY_COUNT = 3;
  ```

- **Test Methods**
  - Use lowerCamelCase and describe the scenario and expected outcome.

  **Example:**
  ```java
  void shouldReturnOrderWhenIdIsValid() { ... }
  ```

---

## Class & Method Design

Strict rules for designing classes and methods:

- **Single Responsibility**
  - Each class and method must have a single, well-defined responsibility.
  - Large classes or methods must be refactored.

- **Class Design**
  - Favor composition over inheritance.
  - Keep classes small and focused.
  - Use interfaces to define contracts; implement them in concrete classes.

  **Example:**
  ```java
  public interface PaymentProcessor {
      void process(Payment payment);
  }

  public class StripePaymentProcessor implements PaymentProcessor {
      @Override
      public void process(Payment payment) { ... }
  }
  ```

- **Method Design**
  - Methods must be short (ideally < 20 lines).
  - Each method must do one thing and do it well.
  - Method parameters should be minimized (prefer objects over long parameter lists).
  - Avoid side effects; methods should be predictable.

  **Example:**
  ```java
  public Order createOrder(Customer customer, List<Item> items) { ... }
  ```

- **Immutability**
  - Favor immutable objects, especially for value objects in the Domain module.
  - Use `final` fields and avoid setters.

  **Example:**
  ```java
  public final class Money {
      private final BigDecimal amount;
      private final Currency currency;
      // constructor, getters, no setters
  }
  ```

---

## Exception Handling

Strict rules for handling exceptions:

- **Checked vs. Unchecked**
  - Use checked exceptions for recoverable conditions and unchecked (runtime) exceptions for programming errors.
  - Never use generic exceptions (e.g., `Exception`, `Throwable`, or `RuntimeException`) directly.

- **Throwing Exceptions**
  - Throw the most specific exception possible.
  - Always provide a clear, descriptive message.

  **Example:**
  ```java
  throw new IllegalArgumentException("Order ID must not be null");
  ```

- **Catching Exceptions**
  - Catch only exceptions you can handle meaningfully.
  - Never catch `Exception` or `Throwable` unless absolutely necessary (and document why).
  - Always log exceptions at the point of catch, and rethrow if the error cannot be handled locally.

  **Example:**
  ```java
  try {
      repository.save(order);
  } catch (DataAccessException ex) {
      logger.error("Failed to save order", ex);
      throw new OrderPersistenceException("Could not persist order", ex);
  }
  ```

- **Custom Exceptions**
  - Define custom exceptions for domain-specific error cases.
  - Name custom exceptions with the “Exception” suffix.

  **Example:**
  ```java
  public class OrderNotFoundException extends RuntimeException { ... }
  ```

---

## Dependency Management

Strict rules for managing dependencies:

- **Gradle Usage**
  - All dependencies must be declared in the appropriate `build.gradle.kts` file for each module.
  - Use the version catalog (e.g., `libs.versions.toml`) for dependency versions; do not hardcode versions in build files.

- **Scope**
  - Only include dependencies required for the specific module.
  - Do not add unnecessary libraries or transitive dependencies.

- **Injection**
  - Use constructor injection for all dependencies (fields must be `final`).
  - Avoid field injection and static access to dependencies.

  **Example:**
  ```java
  public class OrderService {
      private final OrderRepository orderRepository;

      public OrderService(OrderRepository orderRepository) {
          this.orderRepository = orderRepository;
      }
  }
  ```

- **Third-Party Libraries**
  - Use well-maintained, widely adopted libraries.
  - Document the reason for introducing any new library in the code review or commit message.

- **Internal Module Dependencies**
  - Application and Infrastructure modules may depend on Domain, but not vice versa.
  - Never create circular dependencies between modules.

---

## Code Review Checklist

All code must pass the following checklist before merging:



End of guidelines.
