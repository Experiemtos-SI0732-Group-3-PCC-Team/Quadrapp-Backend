Feature: User Authentication

  Scenario: Successfully sign up with valid credentials
    Given the authentication service is available
    When the client sends a sign-up request with username "Julio Diaz" and password "123456789" and roles "ROLE_USER"
    Then the sign-up response should have status code 201
    And the response should contain username "Julio Diaz" and roles "ROLE_USER"

  Scenario: Successfully sign in with valid credentials
    Given the authentication service is available
    When the client sends a sign-in request with username "Carlos Hernandez" and password "123456789"
    Then the authentication response should have status code 200
    And the response should contain a token

  Scenario: Fail to sign in with invalid credentials
    Given the authentication service is available
    When the client sends a sign-in request with username "Carlos Hernandez" and password "12345678"
    Then the authentication response should have status code 500