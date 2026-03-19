# Order Multiple Items : Domain Module impact
**Context**
Festival goers want to order multiple items in a single order. The domain must enforce token balance rules for combined orders.

**Acceptance Criteria**
Feature: Order multiple items
    In order to buy several items at once
    As a festival goer
    I want to place an order for multiple items

1. Scenario: Order within token balance
    Given a festival goer with sufficient drink and food tokens
    When ordering multiple items
    Then the system accepts the order and deducts the correct tokens

2. Scenario: Order exceeds token balance
    Given a festival goer with insufficient tokens
    When ordering multiple items
    Then the system rejects the order

3. Scenario: Negative balance not allowed
    Given a festival goer
    When an order would result in negative balance
    Then the system rejects the order