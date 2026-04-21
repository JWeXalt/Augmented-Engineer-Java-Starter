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
  - CRITICAL : Use Mockito to mock external systems and inject it to your classes.  
  - Use `@ExtendWith(MockitoExtension.class)` at the class level to enable Mockito in JUnit 5 unit tests.
  - Use `@Mock` to create a mock of a dependency, and `@InjectMocks` to instantiate the class under test with those mocks injected.
  - Use `when(...).thenReturn(...)` to define mock behavior for specific method calls.

  **Example:**
  ```java
  @ExtendWith(MockitoExtension.class)
  class PlaceFoodOrderUseCaseTest {

      @Mock
      private OrderRepository orderRepository;

      @InjectMocks
      private PlaceFoodOrderUseCase placeFoodOrderUseCase;

      @Test
      void givenValidOrder_whenPlaceOrder_thenOrderIsSaved() {
          // Given
          FoodOrder order = FoodOrder.builder().id(OrderId.of("order-1")).build();
          when(orderRepository.save(order)).thenReturn(order);

          // When
          FoodOrder result = placeFoodOrderUseCase.execute(order);

          // Then
          assertThat(result).isEqualTo(order);
          verify(orderRepository).save(order);
      }
  }
  ```
- **Assertions**
  - Use expressive assertions; avoid bare `assertTrue` or `assertFalse` unless necessary.

- **Test Data**
  - Use builders or factory methods for creating test data.
  - Avoid hardcoding values directly in test methods.

- **Running Tests**
  - Use the following command to run all tests:
    ```
    ./gradlew test
    ```
  - When announcing that tests are being run, always prefix the message with the 🧪 emoji.

- **CI Integration**
  - All tests must pass before merging code.
  - Failing or ignored tests are not allowed in the main branch.

---

End of testing guidelines.
