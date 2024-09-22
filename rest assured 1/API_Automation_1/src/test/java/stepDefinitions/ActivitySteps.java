package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;

public class ActivitySteps {
    private Response response;

    @When("I call the Bored API")
    public void i_call_the_bored_api() {
        RestAssured.baseURI = "https://www.boredapi.com/api/activity";
        RequestSpecification request = RestAssured.given();
        response = request.get();
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Then("the response should contain required fields")
    public void the_response_should_contain_required_fields() {
        List<String> requiredFields = List.of("activity", "type", "participants", "price", "key", "accessibility");

        for (String field : requiredFields) {
            Assert.assertNotNull("Field " + field + " is missing", response.jsonPath().get(field));
        }
    }
}
