# Review Change Request : Domain Module impact
**Context**
Bartenders want to review and approve or reject change requests for acknowledged orders. The domain must enforce review rules and item transfer constraints.

**Acceptance Criteria**
Feature: Review change request
    In order to review and approve/reject change requests
    As a bartender
    I want to review change requests for acknowledged orders

1. Scenario: Accept change request with transferable items
    Given a change request for an acknowledged order with transferable items
    When reviewing the request
    Then the system accepts the change and notifies the festival goer

2. Scenario: Reject change request with no transferable items
    Given a change request for an acknowledged order with no transferable items
    When reviewing the request
    Then the system rejects the change and notifies the festival goer

3. Scenario: Invalid change request
    Given an invalid change request
    When reviewing
    Then the system rejects the request