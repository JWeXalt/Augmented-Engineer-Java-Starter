# Place Food Order : Domain Module impact
**Context**
Festival goers want to place orders for food items (snacks and meals). The domain must enforce food type rules, token costs, and prevent negative balances.

**Acceptance Criteria**
Feature: Place food order
    In order to buy food
    As a festival goer
    I want to place an order for snacks or meals

1. Scenario: Order snack
    Given a festival goer with at least 1 food token
    When ordering a snack
    Then the snack costs 1 token and balance decreases by 1

2. Scenario: Order meal
    Given a festival goer with at least 3 food tokens
    When ordering a meal
    Then the meal costs 3 tokens and balance decreases by 3

3. Scenario: Insufficient tokens
    Given a festival goer with zero food tokens
    When ordering a snack or meal
    Then the system rejects the order

4. Scenario: Negative balance not allowed
    Given a festival goer
    When an order would result in negative balance
    Then the system rejects the order