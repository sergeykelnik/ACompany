package test.task.ACompany;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NegativeTests extends BaseTest {

    @DataProvider()
    private Object[] Endpoints() {
        return new Object[]{

                "/v1/car-types/manufacturer",
                "/v1/car-types/main-types",
                "/v1/car-types/built-dates",
        };
    }

    @Test(dataProvider = "Endpoints", description = "Check call returns 401 Unauthorized without wa_key")
    public void testUnauthorizedRequests(String endpoint) {
        given()
                .when()
                .get(baseURI + endpoint)
                .then().log().all()
                .assertThat()
                .statusCode(401);
    }

    @Test(dataProvider = "Endpoints", description = "Check call returns 403 Forbidden with wrong wa_key")
    public void testForbiddenRequests(String endpoint) {
        given().queryParam("wa_key", "wrong_key")
                .when()
                .get(baseURI + endpoint)
                .then().log().all()
                .assertThat()
                .statusCode(403);
    }

    @Test(description = "Check call returns 400 Bad Request without required parameter")
    public void testBadRequestForMainTypes() {
        given().queryParam("wa_key", wa_key)
                .when()
                .get(baseURI + endpointMainTypes)
                .then().log().all()
                .assertThat().body("message", equalTo("Required String parameter 'manufacturer' is not present"))
                .and()
                .statusCode(400);
    }

    @Test(description = "Check call returns 400 Bad Request without required parameter")
    public void testBadRequestForBuiltDatesWithoutManufacturer() {
        given().queryParam("wa_key", wa_key)
                .when()
                .get(baseURI + endpointBuiltDates)
                .then().log().all()
                .assertThat().body("message", equalTo("Required String parameter 'manufacturer' is not present"))
                .and()
                .statusCode(400);
    }

    @Test(description = "Check call returns 400 Bad Request without required parameter")
    public void testBadRequestForBuiltDatesWithoutBuiltDates() {
        given().queryParam("wa_key", wa_key)
                .queryParam("manufacturer")
                .when()
                .get(baseURI + endpointBuiltDates)
                .then().log().all()
                .assertThat().body("message", equalTo("Required String parameter 'main-type' is not present"))
                .and()
                .statusCode(400);
    }
}
