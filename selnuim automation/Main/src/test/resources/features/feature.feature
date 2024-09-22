Feature: ITWorx

  Scenario: User can login and add a course
    Given I navigate to the login page
    When I enter valid credentials
    And I add a course with details
    Then I should see the course in the course list
