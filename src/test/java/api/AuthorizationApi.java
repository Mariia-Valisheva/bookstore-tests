package api;

import models.CreateUserAndTokenRequest;
import models.GenerateTokenResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class AuthorizationApi {

    public GenerateTokenResponse generateTokenResponse(CreateUserAndTokenRequest userRequest) {
        return given()
                .log().all()
                .body(userRequest)
                .contentType(JSON)

                .when()
                .post("https://demoqa.com/Account/v1/GenerateToken")

                .then()
                .log().all()
                .extract().as(GenerateTokenResponse.class);
    }
}
