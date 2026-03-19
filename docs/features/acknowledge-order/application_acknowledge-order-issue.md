# Acknowledge Order : Application Module impact
**Context**
Bartenders need an API endpoint to acknowledge orders and provide estimated time of readiness. The application must validate input, enforce acknowledgment rules, and notify festival goers.

**Acceptance Criteria**
Feature: Acknowledge order
    In order to acknowledge orders and inform festival goers
    As a bartender
    I want to acknowledge an order and provide estimated time of readiness

1. Scenario: Successful acknowledgment
    Given a valid order ID
    When calling POST /order/{id}/acknowledge
    Then the response confirms acknowledgment and provides estimated time

2. Scenario: Invalid order ID
    Given an invalid order ID
    When calling POST /order/{id}/acknowledge
    Then the response is 404 Not Found