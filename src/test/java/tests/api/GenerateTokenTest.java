package tests.api;

import api.CreateUserApi;
import models.CreateUserAndTokenRequest;
import models.GenerateTokenResponse;
import org.junit.jupiter.api.Test;
import utils.BookStoreTestData;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class GenerateTokenTest {

    CreateUserApi userAndBooks = new CreateUserApi();
    BookStoreTestData bookStoreTestData = new BookStoreTestData();

    @Test
    void generateTokenTest() {
        String userName = bookStoreTestData.userName;
        String password = bookStoreTestData.password;

        String userId = userAndBooks.createUserResponse(new CreateUserAndTokenRequest(userName, password)).getUserId();

        CreateUserAndTokenRequest createTokenRequest = new CreateUserAndTokenRequest(userName, password);

        GenerateTokenResponse generateTokenResponse = given()
                .log().all()
                .body(createTokenRequest)
                .contentType(JSON)

                .when()
                .post("https://demoqa.com/Account/v1/GenerateToken")

                .then()
                .log().all()
                .extract().as(GenerateTokenResponse.class);

        assertThat(generateTokenResponse.getToken()).isNotEmpty();
    }
}
