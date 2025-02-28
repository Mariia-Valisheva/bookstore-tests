package api;

import models.CreateUserAndTokenRequest;
import models.CreateUserResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class CreateUserApi {

    public CreateUserResponse createUserResponse(CreateUserAndTokenRequest userRequest) {

        return given()
                .body(userRequest)
                .contentType(JSON)

                .when()
                .post("https://demoqa.com/Account/v1/User")

                .then()
                .extract()
                .response().as(CreateUserResponse.class);
    }
}
