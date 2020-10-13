package test.task.ACompany;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    final String wa_key = "coding-puzzle-client-449cc9d";
    final String endpointManufacturer = "/v1/car-types/manufacturer";
    final String endpointMainTypes = "/v1/car-types/main-types";
    final String endpointBuiltDates = "/v1/car-types/built-dates";

    @BeforeTest
    public void configureRestAssured() {
        RestAssured.baseURI = "http://api-aws-eu-qa-1.auto1-test.com";
    }
}
