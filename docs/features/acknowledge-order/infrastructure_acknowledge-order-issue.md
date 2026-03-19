# Acknowledge Order : Infrastructure Module impact
**Context**
The infrastructure module must handle persistence of order acknowledgments, enforce acknowledgment rules, and notify festival goers.

**Acceptance Criteria**
Feature: Acknowledge order
    In order to reliably store order acknowledgments and notify festival goers
    As a system
    I want to persist order acknowledgments, enforce rules, and notify festival goers

1. Scenario: Persist order acknowledgment
    Given a bartender acknowledges an order
    When the acknowledgment is processed
    Then the system persists the acknowledgment and notifies the festival goer

2. Scenario: Time calculation for drinks and food
    Given an order with various items
    When the acknowledgment is processed
    Then the system calculates estimated time as per rules

3. Scenario: Handle invalid order ID
    Given an invalid order ID
    When acknowledging an order
    Then the system rejects the acknowledgment and does not persist it