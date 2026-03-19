# Change Order : Application Module impact
**Context**
Festival goers need an API endpoint to change their order before acknowledgment. The application must validate input, enforce change rules, and notify the bartender if needed.

**Acceptance Criteria**
Feature: Change order
    In order to modify my order
    As a festival goer
    I want to change my order before it is acknowledged

1. Scenario: Successful change before acknowledgment
    Given a valid festival goer ID with an unacknowledged order
    When calling PATCH /festivalgoer/{id}/order
    Then the response confirms the change and enforces token balance

2. Scenario: Change after acknowledgment
    Given a valid festival goer ID with an acknowledged order
    When calling PATCH /festivalgoer/{id}/order
    Then the bartender is notified of the requested change

3. Scenario: Invalid festival goer ID
    Given an invalid festival goer ID
    When calling PATCH /festivalgoer/{id}/order
    Then the response is 404 Not Found