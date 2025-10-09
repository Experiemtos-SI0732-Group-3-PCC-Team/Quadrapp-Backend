Feature: Reservation Payment Management

  Scenario: Successfully create a reservation payment
    Given the reservation payment service is available
    When the driver sends a request to create a reservation payment with reservationId 1001 and amount 50.0
    Then the reservation payment response should have status code 200

  Scenario: Successfully update reservation payment status to COMPLETED
    Given the reservation payment service is available
    When the driver updates the reservation payment with reservationId 1001 to status "COMPLETED"
    Then the reservation payment update response should have status code 200