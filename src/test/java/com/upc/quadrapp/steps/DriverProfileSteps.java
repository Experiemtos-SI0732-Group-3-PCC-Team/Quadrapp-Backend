package com.upc.quadrapp.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DriverProfileSteps {

    Response response;
    final String BASE_URL = "http://localhost:8080/api/v1/driver-profiles";

    @Given("the driver profile service is available")
    public void the_driver_profile_service_is_available() {
        assert BASE_URL != null;
    }

    @When("the user sends a request to create a driver profile with name {string}, city {string}, country {string}, phone {string}, dni {string} and userId {long}")
    public void the_user_sends_a_request_to_create_a_driver_profile(String fullName, String city, String country, String phone, String dni, Long userId) {
        String body = String.format("""
            {
              "fullName": "%s",
              "city": "%s",
              "country": "%s",
              "phone": "%s",
              "dni": "%s",
              "userId": %d
            }
        """, fullName, city, country, phone, dni, userId);

        response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(BASE_URL);
    }

    @Then("the driver profile response should have status code {int}")
    public void the_driver_profile_response_should_have_status_code(int expectedStatus) {
        assertThat(response.getStatusCode(), equalTo(expectedStatus));
    }
}