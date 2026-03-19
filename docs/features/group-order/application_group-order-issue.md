# Group Order : Application Module impact
**Context**
Groups of festival goers need an API endpoint to place group orders. The application must validate input, handle pooling, and enforce token sufficiency.

**Acceptance Criteria**
Feature: Group order
    In order to place a group order
    As a group of festival goers
    We want to pool our tokens and place a single order

1. Scenario: Successful group order
    Given valid festival goer IDs with sufficient pooled tokens
    When calling POST /group-order with contributors and items
    Then the response confirms the order and deducts tokens

2. Scenario: Insufficient pooled tokens
    Given valid festival goer IDs with insufficient pooled tokens
    When calling POST /group-order
    Then the response is 400 Bad Request

3. Scenario: Invalid festival goer ID
    Given an invalid festival goer ID
    When calling POST /group-order
    Then the response is 404 Not Found