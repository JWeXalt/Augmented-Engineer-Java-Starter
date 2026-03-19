# Change Order : Infrastructure Module impact
**Context**
The infrastructure module must handle persistence of order changes, enforce change rules, and notify the bartender if needed.

**Acceptance Criteria**
Feature: Change order
    In order to reliably store order changes and notify the bartender
    As a system
    I want to persist order changes, enforce change rules, and notify the bartender if needed

1. Scenario: Persist order change
    Given a festival goer changes their order before acknowledgment
    When the change is processed
    Then the system persists the change and updates token balances

2. Scenario: Notify bartender after acknowledgment
    Given a festival goer requests a change to an acknowledged order
    When the change is processed
    Then the system notifies the bartender

3. Scenario: Prevent negative balances
    Given a change would result in negative balance
    When the change is processed
    Then the system rejects the change and does not persist it