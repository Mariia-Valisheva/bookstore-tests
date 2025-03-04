package tests.api;

import models.GetUserInfoResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class GetUserInfoTest {

    @Test
    void getUserInfoTest() {
        String UUID = "test";
        String token = "test";

        GetUserInfoResponse getUserInfoResponse = given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .get("https://demoqa.com/Account/v1/User/" + UUID)
                .then()
                .log().all()
                .extract().as(GetUserInfoResponse.class);
    }
}
