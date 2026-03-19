# Order Multiple Items : Infrastructure Module impact
**Context**
The infrastructure module must handle persistence of multi-item orders and enforce token balance rules.

**Acceptance Criteria**
Feature: Order multiple items
    In order to reliably store multi-item orders and update token balances
    As a system
    I want to persist multi-item orders and enforce token balance rules

1. Scenario: Persist multi-item order
    Given a festival goer places a multi-item order
    When the order is processed
    Then the system persists the order and updates token balances

2. Scenario: Prevent negative balances
    Given a festival goer places a multi-item order that would result in negative balance
    When the order is processed
    Then the system rejects the order and does not persist it

3. Scenario: Handle invalid festival goer ID
    Given an invalid festival goer ID
    When placing a multi-item order
    Then the system rejects the order and does not persist it