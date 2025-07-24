package test;

import org.testng.annotations.Test;

import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import static org.hamcrest.Matchers.*;

public class ProductTests {

    @Test
    public void getAllProducts() {
        RestAssured.get("https://fakestoreapi.com/products")
                .then()
                .statusCode(200)
                .time(lessThan(2000L)) // Response time validation
                .body("size()", greaterThan(0));
    }

    @Test
    public void validateProductSchema() {
        RestAssured.get("https://fakestoreapi.com/products/1")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/productSchema.json"));
    }

    @Test
    public void validatePriceFormat() {
        RestAssured.get("https://fakestoreapi.com/products/1")
                .then()
                .statusCode(200)
                .body("price", instanceOf(Float.class));
    }
}