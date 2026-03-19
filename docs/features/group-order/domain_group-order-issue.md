# Group Order : Domain Module impact
**Context**
Groups of festival goers want to pool tokens to place a group order. The domain must enforce pooling rules and token sufficiency.

**Acceptance Criteria**
Feature: Group order
    In order to place a group order
    As a group of festival goers
    We want to pool our tokens and place a single order

1. Scenario: Sufficient pooled tokens
    Given a group of festival goers with enough pooled tokens
    When placing a group order
    Then the system accepts the order and deducts tokens from each contributor

2. Scenario: Insufficient pooled tokens
    Given a group of festival goers with insufficient pooled tokens
    When placing a group order
    Then the system rejects the order

3. Scenario: Negative balance not allowed
    Given a festival goer
    When their token balance would become negative after pooling
    Then the system rejects the pooling