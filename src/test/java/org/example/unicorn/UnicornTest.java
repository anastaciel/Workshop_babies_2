package org.example.unicorn;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class UnicornTest {

    @BeforeAll
    public static void setupTests(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI  = "https://crudcrud.com/api/b79a3555f19e456a8d40b172508a5492";

    }
    @Test
    public void userShouldBeAbleCreateUnicorn() {
        UnicornRequests.createUnicorn("{\n" +
                "  \"name\": \"Helene\",\n" +
                "  \"color\": \"pink\"\n" +
                "}");
    }
    @Test
    public void userShouldBeAbleDeleteExistingUnicorn(){

        String id = UnicornRequests.createUnicorn("{\n" +
                "  \"name\": \"Helene\",\n" +
                "  \"color\": \"pink\"\n" +
                "}");

      UnicornRequests.deleteUnicorn(id);

        given()
                .get("/unicorn/" + id)
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void userShouldBeAbleChangeColor(){
        String id = UnicornRequests.createUnicorn("\n" + "{\n" + "  \"name\": \"Helene\",\n" + "  \"color\": \"pink\"\n" + "}");

        UnicornRequests.changeColor( id, "violet");

        String color = given().get("/unicorn/" + id).then().extract().path("color");

        Assertions.assertEquals("violet", color);

    }

}
