# Transfer Tokens : Infrastructure Module impact
**Context**
The infrastructure module must handle persistence of token transfers, enforce transfer limits, and handle confirmation.

**Acceptance Criteria**
Feature: Transfer tokens
    In order to reliably store token transfers and update balances
    As a system
    I want to persist token transfers, enforce limits, and handle confirmation

1. Scenario: Persist token transfer
    Given a festival goer transfers tokens to another
    When the transfer is processed
    Then the system persists the transfer and updates token balances

2. Scenario: Prevent negative balances
    Given a transfer would result in negative balance
    When the transfer is processed
    Then the system rejects the transfer and does not persist it

3. Scenario: Handle invalid festival goer ID
    Given an invalid festival goer ID
    When transferring tokens
    Then the system rejects the transfer and does not persist it

4. Scenario: Confirmation required
    Given a festival goer receives a transfer
    When the confirmation is processed
    Then the system updates token balances