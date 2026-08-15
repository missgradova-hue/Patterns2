package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Api {

    private Api() {
    }

    private static final RequestSpecification requestSpec =
            new RequestSpecBuilder()
                    .setBaseUri("http://localhost")
                    .setPort(9999)
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .build();

    public static void registerUser(RegistrationUser user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}
