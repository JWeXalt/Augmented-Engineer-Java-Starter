# Water Notification : Application Module impact
**Context**
Bartenders need an API endpoint to send regular notifications to festival goers. The application must validate input, enforce notification rules, and handle frequency.

**Acceptance Criteria**
Feature: Water notification
    In order to encourage responsible drinking
    As a bartender
    I want to send regular notifications to festival goers to drink water

1. Scenario: Successful hourly notification
    Given a valid festival goer ID
    When calling POST /festivalgoer/{id}/notify-water
    Then the response confirms notification every hour between 11:00 AM and 7:00 PM

2. Scenario: Increased frequency for heavy drinkers
    Given a valid festival goer ID with more than 3 alcoholic drinks in the past hour
    When calling POST /festivalgoer/{id}/notify-water
    Then the response confirms notification every 30 minutes

3. Scenario: Notification outside allowed hours
    Given the time is outside 11:00 AM to 7:00 PM
    When calling POST /festivalgoer/{id}/notify-water
    Then the response does not send notifications