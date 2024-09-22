Feature: Bored API Activity

  Scenario: Get random activity
    When I call the Bored API
    Then the response status code should be 200
    And the response should contain required fields
