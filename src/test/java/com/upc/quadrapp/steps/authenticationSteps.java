package com.upc.quadrapp.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

public class authenticationSteps {

    Response response;
    final String BASE_URL = "http://localhost:8080/api/v1/authentication/sign-in";

    @Given("the authentication service is available")
    public void the_authentication_service_is_available() {
        assert BASE_URL != null;
    }

    @When("the client sends a sign-up request with username {string} and password {string} and roles {string}")
    public void the_client_sends_a_sign_up_request(String username, String password, String rolesCsv) {
        List<String> roles = List.of(rolesCsv.split(","));
        String rolesJson = roles.stream().map(role -> "\"" + role.trim() + "\"").toList().toString();

        String body = String.format("""
        {
          "username": "%s",
          "password": "%s",
          "roles": %s
        }
    """, username, password, rolesJson);

        response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("http://localhost:8080/api/v1/authentication/sign-up");
    }

    @When("the client sends a sign-in request with username {string} and password {string}")
    public void the_client_sends_a_sign_in_request(String username, String password) {
        String body = String.format("""
            {
              "username": "%s",
              "password": "%s"
            }
        """, username, password);

        response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(BASE_URL);
    }

    @Then("the sign-up response should have status code {int}")
    public void the_sign_up_response_should_have_status_code(int expectedStatus) {
        assertThat(response.getStatusCode(), equalTo(expectedStatus));
    }

    @Then("the authentication response should have status code {int}")
    public void the_authentication_response_should_have_status_code(int expectedStatus) {
        assertThat(response.getStatusCode(), equalTo(expectedStatus));
    }

    @Then("the response should contain a token")
    public void the_response_should_contain_a_token() {
        String token = response.jsonPath().getString("token");
        assertThat(token, notNullValue());
        assertThat(token.length(), greaterThan(10));
    }

    @And("the response should contain username {string} and roles {string}")
    public void the_response_should_contain_username_and_roles(String expectedUsername, String expectedRolesCsv) {
        String actualUsername = response.jsonPath().getString("username");
        List<String> actualRoles = response.jsonPath().getList("roles");

        List<String> expectedRoles = List.of(expectedRolesCsv.split(","));

        assertThat(actualUsername, equalTo(expectedUsername));
        assertThat(actualRoles, containsInAnyOrder(expectedRoles.toArray()));
    }
}
