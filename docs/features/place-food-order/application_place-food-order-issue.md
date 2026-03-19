# Place Food Order : Application Module impact
**Context**
Festival goers need an API endpoint to place food orders. The application must expose a REST endpoint, validate input, and handle token deduction.

**Acceptance Criteria**
Feature: Place food order
    In order to buy food
    As a festival goer
    I want to place an order for snacks or meals

1. Scenario: Successful order for snack
    Given a valid festival goer ID with at least 1 food token
    When calling POST /festivalgoer/{id}/order with a snack
    Then the response confirms the order and balance is reduced by 1

2. Scenario: Successful order for meal
    Given a valid festival goer ID with at least 3 food tokens
    When calling POST /festivalgoer/{id}/order with a meal
    Then the response confirms the order and balance is reduced by 3

3. Scenario: Insufficient tokens
    Given a valid festival goer ID with zero food tokens
    When calling POST /festivalgoer/{id}/order with a snack or meal
    Then the response is 400 Bad Request

4. Scenario: Invalid festival goer ID
    Given an invalid festival goer ID
    When calling POST /festivalgoer/{id}/order
    Then the response is 404 Not Found

5. Scenario: Negative balance not allowed
    Given a festival goer
    When an order would result in negative balance
    Then the response is 400 Bad Request