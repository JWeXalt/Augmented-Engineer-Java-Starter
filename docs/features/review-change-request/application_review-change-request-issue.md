# Review Change Request : Application Module impact
**Context**
Bartenders need an API endpoint to review and approve/reject change requests for acknowledged orders. The application must validate input, enforce review rules, and notify festival goers.

**Acceptance Criteria**
Feature: Review change request
    In order to review and approve/reject change requests
    As a bartender
    I want to review change requests for acknowledged orders

1. Scenario: Successful review and acceptance
    Given a valid change request with transferable items
    When calling POST /order/{id}/change-request/review
    Then the response confirms acceptance and notifies the festival goer

2. Scenario: Successful review and rejection
    Given a valid change request with no transferable items
    When calling POST /order/{id}/change-request/review
    Then the response confirms rejection and notifies the festival goer

3. Scenario: Invalid change request
    Given an invalid change request
    When calling POST /order/{id}/change-request/review
    Then the response is 404 Not Found