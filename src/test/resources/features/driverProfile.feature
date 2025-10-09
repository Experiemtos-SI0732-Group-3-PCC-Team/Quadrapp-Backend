Feature: Driver Profile Creation

  Scenario: Successfully create a driver profile
    Given the driver profile service is available
    When the user sends a request to create a driver profile with name "Luis Ramos", city "Lima", country "Peru", phone "987654321", dni "12345678" and userId 1
    Then the driver profile response should have status code 200

  Scenario: Fail to create a driver profile with invalid data
    Given the driver profile service is available
    When the user sends a request to create a driver profile with name "", city "Lima", country "Peru", phone "", dni "12345678" and userId 1
    Then the driver profile response should have status code 500
