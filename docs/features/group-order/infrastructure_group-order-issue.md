# Group Order : Infrastructure Module impact
**Context**
The infrastructure module must handle persistence of group orders, token pooling, and enforce token sufficiency.

**Acceptance Criteria**
Feature: Group order
    In order to reliably store group orders and update token balances
    As a system
    I want to persist group orders, handle token pooling, and enforce token sufficiency

1. Scenario: Persist group order
    Given a group of festival goers places a group order
    When the order is processed
    Then the system persists the order and updates token balances for each contributor

2. Scenario: Prevent negative balances
    Given a group order would result in negative balance for any contributor
    When the order is processed
    Then the system rejects the order and does not persist it

3. Scenario: Handle invalid festival goer ID
    Given an invalid festival goer ID
    When placing a group order
    Then the system rejects the order and does not persist it