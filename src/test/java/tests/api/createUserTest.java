package tests.api;

import org.junit.jupiter.api.Test;
import utils.testdata.BookStoreTestData;
import utils.model.CreateUserRequest;
import utils.model.CreateUserResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class createUserTest {

    @Test
    void createUserTest() {
        BookStoreTestData bookStoreTestData = new BookStoreTestData();
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUserName(bookStoreTestData.userName);
        userRequest.setPassword(bookStoreTestData.password);

        CreateUserResponse createUserResponse = given()
                .log().all()
                .body(userRequest)
                .contentType(JSON)

                .when()
                .post("https://demoqa.com/Account/v1/User")

                .then()
                .log().all()
                .extract().as(CreateUserResponse.class);

        assertThat(createUserResponse.getUserID()).isNotEmpty();
    }
}
