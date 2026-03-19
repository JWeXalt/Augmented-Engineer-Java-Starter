# Transfer Tokens : Application Module impact
**Context**
Festival goers need an API endpoint to transfer tokens to another festival goer. The application must validate input, enforce transfer limits, and handle confirmation.

**Acceptance Criteria**
Feature: Transfer tokens
    In order to transfer tokens
    As a festival goer
    I want to transfer up to three tokens of each type to another festival goer, with confirmation

1. Scenario: Successful transfer within limits
    Given valid festival goer IDs and sufficient tokens
    When calling POST /festivalgoer/{id}/transfer-tokens
    Then the response confirms the transfer and awaits confirmation

2. Scenario: Transfer exceeds limits
    Given valid festival goer IDs and transfer exceeds limits
    When calling POST /festivalgoer/{id}/transfer-tokens
    Then the response is 400 Bad Request

3. Scenario: Invalid festival goer ID
    Given an invalid festival goer ID
    When calling POST /festivalgoer/{id}/transfer-tokens
    Then the response is 404 Not Found

4. Scenario: Confirmation required
    Given a festival goer receives a transfer
    When calling POST /festivalgoer/{id}/confirm-transfer
    Then the response updates token balances