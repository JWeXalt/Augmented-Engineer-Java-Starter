# Mark Order Ready : Application Module impact
**Context**
Bartenders need an API endpoint to mark orders as ready for pickup. The application must validate input, enforce readiness rules, and notify festival goers.

**Acceptance Criteria**
Feature: Mark order ready
    In order to mark orders as ready for pickup
    As a bartender
    I want to mark an order as ready when enough items are prepared

1. Scenario: Successful mark as ready
    Given a valid order ID with enough prepared items
    When calling POST /order/{id}/ready
    Then the response confirms readiness and notifies the festival goer

2. Scenario: Mark as ready with insufficient items
    Given a valid order ID with insufficient items
    When calling POST /order/{id}/ready
    Then the response is 400 Bad Request

3. Scenario: Invalid order ID
    Given an invalid order ID
    When calling POST /order/{id}/ready
    Then the response is 404 Not Found