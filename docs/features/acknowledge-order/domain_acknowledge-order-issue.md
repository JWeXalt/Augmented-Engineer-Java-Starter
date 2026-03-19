# Acknowledge Order : Domain Module impact
**Context**
Bartenders want to acknowledge orders and provide estimated time of readiness. The domain must enforce acknowledgment rules and time calculation.

**Acceptance Criteria**
Feature: Acknowledge order
    In order to acknowledge orders and inform festival goers
    As a bartender
    I want to acknowledge an order and provide estimated time of readiness

1. Scenario: Acknowledge order
    Given a bartender receives an order
    When acknowledging the order
    Then the system notifies the festival goer and provides estimated time

2. Scenario: Time calculation for drinks and food
    Given an order with various items
    When acknowledging the order
    Then the system calculates estimated time as per rules

3. Scenario: Invalid order
    Given an invalid order
    When acknowledging
    Then the system rejects the acknowledgment