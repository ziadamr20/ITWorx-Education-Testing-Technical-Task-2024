package org.example;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Main {

    @Test
    public void testAddAndDeleteObject() {
        // Setting the HTTP client configurations for timeout
        RestAssured.given()
                .config(RestAssured.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.socket.timeout", 100000) // 10 seconds
                                .setParam("http.connection.timeout", 100000)))
                .when()
                .get("https://api.restful-api.dev/objects")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getResponseSchema.json"));  // Add schema validation here

        // Setting base URI
        RestAssured.baseURI = "https://api.restful-api.dev/objects";

        // Creating the request body for the POST request
        String requestBody = "{\n" +
                "\"name\": \"Apple MacBook Pro 16\",\n" +
                "\"data\": {\n" +
                "\"year\": 2019,\n" +
                "\"price\": 1849.99,\n" +
                "\"CPU model\": \"Intel Core i9\",\n" +
                "\"Hard disk size\": \"1 TB\"\n" +
                " }\n" +
                "}";

        // Making a POST request to create a new object and validating the response
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("name", equalTo("Apple MacBook Pro 16"))
                .body("data.year", equalTo(2019))
                .body("data.price", equalTo(1849.99f))
                .extract().response();

        // Extracting the object ID from the POST response
        String objectId = response.jsonPath().getString("id");

        // Forming the DELETE URL using the extracted object ID
        String deleteUrl = "https://api.restful-api.dev/objects/" + objectId;

        // Sending the DELETE request and verifying the response
        given()
                .when()
                .delete(deleteUrl)
                .then()
                .statusCode(200)
                .body("message", equalTo("Object with id = " + objectId + ", has been deleted."));
    }
}
