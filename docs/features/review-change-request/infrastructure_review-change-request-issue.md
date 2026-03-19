# Review Change Request : Infrastructure Module impact
**Context**
The infrastructure module must handle persistence of change requests, enforce review rules, and notify festival goers.

**Acceptance Criteria**
Feature: Review change request
    In order to reliably store change requests and notify festival goers
    As a system
    I want to persist change requests, enforce review rules, and notify festival goers

1. Scenario: Persist change request
    Given a bartender reviews a change request for an acknowledged order
    When the review is processed
    Then the system persists the review and notifies the festival goer

2. Scenario: Accept change request with transferable items
    Given a change request with transferable items
    When the review is processed
    Then the system accepts the change and notifies the festival goer

3. Scenario: Reject change request with no transferable items
    Given a change request with no transferable items
    When the review is processed
    Then the system rejects the change and notifies the festival goer

4. Scenario: Handle invalid change request
    Given an invalid change request
    When reviewing
    Then the system rejects the request and does not persist it