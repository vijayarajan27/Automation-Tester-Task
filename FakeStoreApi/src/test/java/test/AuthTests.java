package test;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.notNullValue;

public class AuthTests {
    public static String authToken;

    @Test(priority = 1)
    public void loginWithValidCredentials() {
        String payload = "{ \"username\": \"mor_2314\", \"password\": \"83r5^_\" }";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("https://fakestoreapi.com/auth/login");

        response.then().statusCode(200)
                .body("token", notNullValue());

        authToken = response.jsonPath().getString("token");
        System.out.println("Token: " + authToken);
    }

    @Test(priority = 2)
    public void loginWithInvalidCredentials() {
        String payload = "{ \"username\": \"invalid_user\", \"password\": \"wrong\" }";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("https://fakestoreapi.com/auth/login")
                .then()
                .statusCode(401); // Unauthorized
    }
}
