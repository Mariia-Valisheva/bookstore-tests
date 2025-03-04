package api;

import models.GetUserInfoResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class GetUserInfoApi {

    public GetUserInfoResponse getUserInfoResponse(String token, String UUID) {
        return given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .get("https://demoqa.com/Account/v1/User/" + UUID)
                .then()
                .log().all()
                .extract().as(GetUserInfoResponse.class);
    }
}
