# Mark Order Ready : Domain Module impact
**Context**
Bartenders want to mark orders as ready for pickup. The domain must enforce readiness rules and item sufficiency.

**Acceptance Criteria**
Feature: Mark order ready
    In order to mark orders as ready for pickup
    As a bartender
    I want to mark an order as ready when enough items are prepared

1. Scenario: Mark order ready with sufficient items
    Given a bartender has prepared enough items for an order
    When marking the order as ready
    Then the system notifies the festival goer

2. Scenario: Mark order ready with insufficient items
    Given a bartender has not prepared enough items
    When marking the order as ready
    Then the system rejects the action

3. Scenario: Invalid order
    Given an invalid order
    When marking as ready
    Then the system rejects the action