# Change Order : Domain Module impact
**Context**
Festival goers want to change their order before it is acknowledged. The domain must enforce change rules and token balance constraints.

**Acceptance Criteria**
Feature: Change order
    In order to modify my order
    As a festival goer
    I want to change my order before it is acknowledged

1. Scenario: Change order before acknowledgment
    Given a festival goer with an unacknowledged order
    When changing the order
    Then the system allows adding/removing items and enforces token balance

2. Scenario: Change order after acknowledgment
    Given a festival goer with an acknowledged order
    When requesting a change
    Then the system notifies the bartender

3. Scenario: Negative balance not allowed
    Given a festival goer
    When a change would result in negative balance
    Then the system rejects the change