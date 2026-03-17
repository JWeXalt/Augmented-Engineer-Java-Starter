# Code Review Guidelines

All code must pass the following checklist before merging:

- Adheres to module boundaries and Hexagonal Architecture rules
- Follows all Java coding standards and naming conventions
- Classes and methods have clear, single responsibilities
- Exception handling is specific, meaningful, and documented
- Dependency management follows project rules (constructor injection, no unnecessary libraries)
- No business logic in Application or Infrastructure modules
- No technical implementation details in Domain module
- All business logic and module boundaries are covered by unit and integration tests
- Test methods use the Given-When-Then pattern and are descriptive
- All code is properly documented with Javadoc and meaningful inline comments
- No TODOs or FIXMEs without author and date
- No unused code, dead code, or commented-out blocks
- All tests pass in CI; no ignored or failing tests in main branch

---

End of code review guidelines.
