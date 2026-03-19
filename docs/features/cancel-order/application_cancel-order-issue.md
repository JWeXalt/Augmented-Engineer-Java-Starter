# Cancel Order : Application Module impact
**Context**
Festival goers need an API endpoint to cancel their order before acknowledgment. The application must validate input, enforce cancellation rules, and refund tokens.

**Acceptance Criteria**
Feature: Cancel order
    In order to cancel my order
    As a festival goer
    I want to cancel my order before it is acknowledged and receive a token refund

1. Scenario: Successful cancellation before acknowledgment
    Given a valid festival goer ID with an unacknowledged order
    When calling DELETE /festivalgoer/{id}/order
    Then the response confirms the cancellation and refunds tokens

2. Scenario: Cancel after acknowledgment
    Given a valid festival goer ID with an acknowledged order
    When calling DELETE /festivalgoer/{id}/order
    Then the response is 400 Bad Request

3. Scenario: Invalid festival goer ID
    Given an invalid festival goer ID
    When calling DELETE /festivalgoer/{id}/order
    Then the response is 404 Not Found