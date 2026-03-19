# Water Notification : Domain Module impact
**Context**
Bartenders want to send regular notifications to festival goers to remind them to drink water. The domain must enforce notification rules and frequency.

**Acceptance Criteria**
Feature: Water notification
    In order to encourage responsible drinking
    As a bartender
    I want to send regular notifications to festival goers to drink water

1. Scenario: Hourly notification
    Given a festival goer
    When the time is between 11:00 AM and 7:00 PM
    Then the system sends a notification every hour

2. Scenario: Increased frequency for heavy drinkers
    Given a festival goer has drank more than 3 alcoholic drinks in the past hour
    When the time is between 11:00 AM and 7:00 PM
    Then the system sends a notification every 30 minutes

3. Scenario: Notification outside allowed hours
    Given the time is outside 11:00 AM to 7:00 PM
    When sending notifications
    Then the system does not send notifications