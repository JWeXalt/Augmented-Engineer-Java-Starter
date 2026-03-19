# Cancel Order : Domain Module impact
**Context**
Festival goers want to cancel their order before it is acknowledged. The domain must enforce cancellation rules and token refund.

**Acceptance Criteria**
Feature: Cancel order
    In order to cancel my order
    As a festival goer
    I want to cancel my order before it is acknowledged and receive a token refund

1. Scenario: Cancel order before acknowledgment
    Given a festival goer with an unacknowledged order
    When canceling the order
    Then the system cancels the order and refunds tokens

2. Scenario: Cancel order after acknowledgment
    Given a festival goer with an acknowledged order
    When requesting cancellation
    Then the system rejects the cancellation

3. Scenario: Negative balance not allowed
    Given a festival goer
    When a cancellation would result in negative balance
    Then the system rejects the cancellation