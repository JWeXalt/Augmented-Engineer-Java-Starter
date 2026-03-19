# Cancel Order : Infrastructure Module impact
**Context**
The infrastructure module must handle persistence of order cancellations, enforce cancellation rules, and refund tokens.

**Acceptance Criteria**
Feature: Cancel order
    In order to reliably store order cancellations and refund tokens
    As a system
    I want to persist order cancellations, enforce cancellation rules, and refund tokens

1. Scenario: Persist order cancellation
    Given a festival goer cancels their order before acknowledgment
    When the cancellation is processed
    Then the system persists the cancellation and refunds tokens

2. Scenario: Prevent negative balances
    Given a cancellation would result in negative balance
    When the cancellation is processed
    Then the system rejects the cancellation and does not persist it

3. Scenario: Handle invalid festival goer ID
    Given an invalid festival goer ID
    When canceling an order
    Then the system rejects the cancellation and does not persist it