package api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class DeleteUserApi {
    public Response deleteUserResponse(String token, String UUID) {
        return (Response) given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .delete("https://demoqa.com/Account/v1/User/" + UUID)
                .then()
                .log().all()
                .extract().body();
    }
}
