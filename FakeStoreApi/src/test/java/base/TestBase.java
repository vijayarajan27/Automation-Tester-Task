package base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class TestBase {
    protected String baseURI = "https://fakestoreapi.com";
    protected static String token;

    public RequestSpecification givenSpec() {
        return RestAssured.given().baseUri(baseURI).contentType("application/json");
    }
}
