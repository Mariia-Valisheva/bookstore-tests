package tests.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUserTest {
    @Test
    void deleteUserTest() {
        String UUID = "test";
        String token = "test";

        Response response = (Response) given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .delete("https://demoqa.com/Account/v1/User/" + UUID)
                .then()
                .log().all()
                .statusCode(204)
                .extract().body();

        assertThat(response.getBody().asString()).isEmpty();

    }
}
