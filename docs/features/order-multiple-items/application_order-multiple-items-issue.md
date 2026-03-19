# Order Multiple Items : Application Module impact
**Context**
Festival goers need an API endpoint to place orders for multiple items. The application must validate input and enforce token balance rules.

**Acceptance Criteria**
Feature: Order multiple items
    In order to buy several items at once
    As a festival goer
    I want to place an order for multiple items

1. Scenario: Successful order within balance
    Given a valid festival goer ID with sufficient tokens
    When calling POST /festivalgoer/{id}/order with multiple items
    Then the response confirms the order and deducts tokens

2. Scenario: Order exceeds balance
    Given a valid festival goer ID with insufficient tokens
    When calling POST /festivalgoer/{id}/order with multiple items
    Then the response is 400 Bad Request

3. Scenario: Invalid festival goer ID
    Given an invalid festival goer ID
    When calling POST /festivalgoer/{id}/order
    Then the response is 404 Not Found