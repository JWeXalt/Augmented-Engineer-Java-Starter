# Transfer Tokens : Domain Module impact
**Context**
Festival goers want to transfer tokens to another festival goer. The domain must enforce transfer limits, confirmation, and prevent negative balances.

**Acceptance Criteria**
Feature: Transfer tokens
    In order to transfer tokens
    As a festival goer
    I want to transfer up to three tokens of each type to another festival goer, with confirmation

1. Scenario: Successful transfer within limits
    Given a festival goer with at least three tokens of each type
    When transferring up to three tokens to another festival goer
    Then the system deducts tokens and awaits confirmation

2. Scenario: Transfer exceeds limits
    Given a festival goer attempts to transfer more than three tokens
    When transferring tokens
    Then the system rejects the transfer

3. Scenario: Negative balance not allowed
    Given a festival goer
    When a transfer would result in negative balance
    Then the system rejects the transfer

4. Scenario: Confirmation required
    Given a festival goer receives a transfer
    When confirming the transfer
    Then the system updates token balances