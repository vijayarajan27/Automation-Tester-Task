package test;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.*;

public class CartTests {

    @Test
    public void addItemsToCartAndVerify() {
        String payload = "{\"userId\": 1,\"date\": \"2024-07-24\", \"products\": [{ \"productId\": 1, \"quantity\": 2 },{ \"productId\": 2, \"quantity\": 3 }]}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("https://fakestoreapi.com/carts");

        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("products.size()", is(2));
    }

    @Test
    public void deleteCartItem() {
        RestAssured.delete("https://fakestoreapi.com/carts/7")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    public void getCartDetails() {
        RestAssured.get("https://fakestoreapi.com/carts/5")
                .then()
                .statusCode(200)
                .body("id", equalTo(5));
    }
}
