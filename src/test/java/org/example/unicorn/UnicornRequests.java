package org.example.unicorn;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class UnicornRequests {
    public static String createUnicorn(String body) {
        return  given()
                .body(body)
                .contentType(ContentType.JSON)
                .post("/unicorn")
                .then()
                .assertThat()
                .statusCode(201)
                .body("$", hasKey("_id"))
                .extract().path("_id");
    }

    public static void deleteUnicorn(String id){
        given().delete("/unicorn/" + id)
                .then()
                .assertThat()
                .statusCode(200);
    }

    public static void changeColor(String id, String color){
        String requestBody = String.format("\n" + "{\n" + "  \"name\": \"Helene\",\n" + "  \"color\": \"%s\"\n" + "}", color);

        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .put("/unicorn/" + id)
                .then()
                .assertThat()
                .statusCode(200);


        }
}
