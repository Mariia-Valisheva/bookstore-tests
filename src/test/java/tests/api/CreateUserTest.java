package tests.api;

import org.junit.jupiter.api.Test;
import utils.BookStoreTestData;
import models.CreateUserAndTokenRequest;
import models.CreateUserResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserTest {

    BookStoreTestData bookStoreTestData = new BookStoreTestData();

    @Test
    void createUserTest() {

        CreateUserAndTokenRequest userRequest = new CreateUserAndTokenRequest(bookStoreTestData.userName, bookStoreTestData.password);

        CreateUserResponse createUserResponse = given()
                .log().all()
                .body(userRequest)
                .contentType(JSON)

                .when()
                .post("https://demoqa.com/Account/v1/User")

                .then()
                .log().all()
                .extract().as(CreateUserResponse.class);

        assertThat(createUserResponse.getUserId()).isNotEmpty();
    }
}
