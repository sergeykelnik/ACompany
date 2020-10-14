package test.task.ACompany;

import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PositiveTests extends BaseTest {

    @DataProvider()
    private Object[][] Endpoints1() {
        return new Object[][]{

                {"/v1/car-types/manufacturer", "", ""},
                {"/v1/car-types/main-types", "manufacturer", ""},
                {"/v1/car-types/built-dates", "manufacturer", "main-type"}
        };
    }

    @Test(dataProvider = "Endpoints1", description = "Check call returns 200 OK, ContentType JSON, appropriate path items")
    public void testOKRequests(String endpoint, String param1, String param2) {
        given().queryParam("wa_key", wa_key)
                .queryParam(param1, "853")
                .queryParam(param2, "Model X")
                .when()
                .get(baseURI + endpoint)
                .then().log().all()
                .assertThat()
                .body("page", equalTo(0)) //Test fails because fields: page, pageSize, totalPageCount absent in '/v1/car-types/built-dates' response
                .body("pageSize", equalTo(Integer.MAX_VALUE))
                .body("totalPageCount", equalTo(1))
                .body("wkda", is(notNullValue()))
                .contentType(ContentType.JSON)
                .statusCode(200);
    }

    @Test(description = "Check MainTypes returns 404 Not found if wrong parameter used")
    public void testEmptyBodyMainTypes() {
        given().queryParam("wa_key", wa_key)
                .queryParam("manufacturer", "999")
                .when()
                .get(baseURI + endpointMainTypes)
                .then().log().all()
                .assertThat()
                .statusCode(404); //Test fails because we expect 404 Not found returned by server instead of 200
    }

    @Test(description = "Check BuiltDates returns 404 Not found if wrong parameter used")
    public void testEmptyBodyBuiltDates() {
        given().queryParam("wa_key", wa_key)
                .queryParam("manufacturer", "999")
                .queryParam("main-type", "999")
                .when()
                .get(baseURI + endpointBuiltDates)
                .then().log().all()
                .assertThat()
                .statusCode(404); //Test fails because we expect 404 Not found returned by server instead of 200
    }

    @Test(description = "Check call returns correct amount of manufacturers")
    public void testAmountOfManufacturers() {
        given().queryParam("wa_key", wa_key)
                .when()
                .get(baseURI + endpointManufacturer)
                .then().log().all()
                .assertThat()
                .body("wkda.size()", equalTo(79)); //Sometimes test fails due to extra key-value "20": "Abarth" which appears in '/v1/car-types/manufacturer'
    }


    @Test(description = "Check specific use case")
    public void testSpecificUseCase() {

        String code = "460";
        String manufacturer = "Lada";
        String type = "Vesta";
        String year = "2019";

        given().queryParam("wa_key", wa_key)
                .when()
                .get(baseURI + endpointManufacturer)
                .then().log().all()
                .assertThat()
                .body("wkda." + code, equalTo(manufacturer));

        given().queryParam("wa_key", wa_key)
                .queryParam("manufacturer", code)
                .when()
                .get(baseURI + endpointMainTypes)
                .then().log().all()
                .assertThat()
                .body("wkda." + type, equalTo(type));

        given().queryParam("wa_key", wa_key)
                .queryParam("manufacturer", code).when()
                .queryParam("main-type", type)
                .when()
                .get(baseURI + endpointBuiltDates)
                .then().log().all()
                .assertThat()
                .body("wkda." + year, equalTo(year));
    }
}