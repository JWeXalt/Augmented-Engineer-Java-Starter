# Mark Order Ready : Infrastructure Module impact
**Context**
The infrastructure module must handle persistence of order readiness, enforce readiness rules, and notify festival goers.

**Acceptance Criteria**
Feature: Mark order ready
    In order to reliably store order readiness and notify festival goers
    As a system
    I want to persist order readiness, enforce rules, and notify festival goers

1. Scenario: Persist order readiness
    Given a bartender marks an order as ready
    When the readiness is processed
    Then the system persists the readiness and notifies the festival goer

2. Scenario: Prevent marking as ready with insufficient items
    Given an order with insufficient items
    When the readiness is processed
    Then the system rejects the action and does not persist it

3. Scenario: Handle invalid order ID
    Given an invalid order ID
    When marking an order as ready
    Then the system rejects the action and does not persist it