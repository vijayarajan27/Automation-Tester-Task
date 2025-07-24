package com.example.tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserApiContractTest {

  @BeforeClass
  public void setup() {
    RestAssured.baseURI = "https://api.example.com";
    RestAssured.basePath = "/";
  }

  @Test
  public void validateUserRegistrationResponseSchema() {
    RestAssured
      .given()
        .queryParam("id", 123)
      .when()
        .get("/user")
      .then()
        .assertThat()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/user-registration.schema.json"));
  }
}
