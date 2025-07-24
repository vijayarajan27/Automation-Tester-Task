package test;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class NegativeTests {

    @Test
    public void callAPIWithInvalidProductId() {
        RestAssured.get("https://fakestoreapi.com/products/99999")
                .then()
                .statusCode(404);
    }

    @Test
    public void testRateLimiting() {
        for (int i = 0; i < 20; i++) {
            RestAssured.get("https://fakestoreapi.com/products")
                    .then()
                    .statusCode(200);
        }
        // Note: FakeStoreAPI may not enforce real rate limiting.
    }
}