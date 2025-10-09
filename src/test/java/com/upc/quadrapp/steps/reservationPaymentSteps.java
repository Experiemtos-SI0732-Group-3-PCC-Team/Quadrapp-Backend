package com.upc.quadrapp.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class reservationPaymentSteps {

    Response response;
    final String BASE_URL = "http://localhost:8080/api/v1/reservation-payments";

    @Given("the reservation payment service is available")
    public void the_reservation_payment_service_is_available() {
        assert BASE_URL != null;
    }

    @When("the driver sends a request to create a reservation payment with reservationId {long} and amount {double}")
    public void the_client_sends_a_request_to_create_a_reservation_payment(Long reservationId, Double amount) {
        String body = String.format("""
            {
              "reservationId": %d,
              "amount": %.2f
            }
        """, reservationId, amount);

        response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(BASE_URL);
    }

    @Then("the reservation payment response should have status code {int}")
    public void the_reservation_payment_response_should_have_status_code(int expectedStatus) {
        assertThat(response.getStatusCode(), equalTo(expectedStatus));
    }

    @When("the driver updates the reservation payment with reservationId {long} to status {string}")
    public void the_client_updates_the_reservation_payment_status(Long reservationId, String status) {
        String body = String.format("""
            {
              "status": "%s"
            }
        """, status);

        response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(BASE_URL + "/" + reservationId + "/status");
    }

    @Then("the reservation payment update response should have status code {int}")
    public void the_reservation_payment_update_response_should_have_status_code(int expectedStatus) {
        assertThat(response.getStatusCode(), equalTo(expectedStatus));
    }
}
