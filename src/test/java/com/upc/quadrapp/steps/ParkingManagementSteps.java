package com.upc.quadrapp.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class ParkingManagementSteps {

    private Response response;

    @Given("the parking service is available")
    public void the_parking_service_is_available() {
        baseURI = "http://localhost:8080/api/parkings";
    }

    @When("the owner sends a request to add a parking spot with row {int}, column {int}, and label {string} to parking {long}")
    public void the_client_sends_a_request_to_add_a_parking_spot(int row, int column, String label, long parkingId) {
        String jsonBody = String.format("""
            {
              "row": %d,
              "column": %d,
              "label": "%s"
            }
        """, row, column, label);

        response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("/" + parkingId + "/spots");

        response.then().log().all();
    }

    @Then("the response should have status code {int}")
    public void the_response_should_have_status_code(int status) {
        response.then().statusCode(status);
    }

    @And("the body should contain {string}")
    public void the_body_should_contain(String bodyText) {
        response.then().body(containsString(bodyText));
    }
}
