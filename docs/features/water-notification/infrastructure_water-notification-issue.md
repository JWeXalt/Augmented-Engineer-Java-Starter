# Water Notification : Infrastructure Module impact
**Context**
The infrastructure module must handle persistence and scheduling of water notifications, enforce rules, and handle frequency.

**Acceptance Criteria**
Feature: Water notification
    In order to reliably schedule and send water notifications
    As a system
    I want to persist notification schedules, enforce rules, and handle frequency

1. Scenario: Schedule hourly notification
    Given a festival goer
    When the time is between 11:00 AM and 7:00 PM
    Then the system schedules and sends a notification every hour

2. Scenario: Schedule increased frequency for heavy drinkers
    Given a festival goer has drank more than 3 alcoholic drinks in the past hour
    When the time is between 11:00 AM and 7:00 PM
    Then the system schedules and sends a notification every 30 minutes

3. Scenario: Prevent notification outside allowed hours
    Given the time is outside 11:00 AM to 7:00 PM
    When scheduling notifications
    Then the system does not schedule notifications