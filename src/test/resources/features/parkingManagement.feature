Feature: Parking spot management

  Scenario: Successfully add a parking spot
    Given the parking service is available
    When the owner sends a request to add a parking spot with row 1, column 2, and label "A2" to parking 2
    Then the response should have status code 200
    And the body should contain "A2"

  Scenario: Fail to add a parking spot that already exists
    Given the parking service is available
    When the owner sends a request to add a parking spot with row 1, column 2, and label "A2" to parking 2
    Then the response should have status code 400
    And the body should contain "already exists"
