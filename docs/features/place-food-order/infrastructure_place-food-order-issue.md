# Place Food Order : Infrastructure Module impact
**Context**
The infrastructure module must handle persistence of food orders, token deduction, and enforce food type rules and token costs.

**Acceptance Criteria**
Feature: Place food order
    In order to reliably store food orders and update token balances
    As a system
    I want to persist food orders, deduct tokens, and enforce food type rules

1. Scenario: Persist food order
    Given a festival goer places a food order
    When the order is processed
    Then the system persists the order details and updates token balance

2. Scenario: Deduct tokens for snacks and meals
    Given a festival goer places an order for a snack or meal
    When the order is processed
    Then the system deducts the correct number of tokens from the balance

3. Scenario: Prevent negative balances
    Given a festival goer places an order that would result in negative balance
    When the order is processed
    Then the system rejects the order and does not persist it

4. Scenario: Handle invalid festival goer ID
    Given an invalid festival goer ID
    When placing a food order
    Then the system rejects the order and does not persist it