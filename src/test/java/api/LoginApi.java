package api;

import models.CreateUserAndTokenRequest;
import models.CreateUserResponse;
import models.LoginResponse;
import utils.BookStoreTestData;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class LoginApi {

    CreateUserApi createUserApi = new CreateUserApi();
    BookStoreTestData bookStoreTestData = new BookStoreTestData();


    public LoginResponse loginResponse() {
        String userName = bookStoreTestData.userName;
        String password = bookStoreTestData.password;

        CreateUserResponse createUserResponse = createUserApi.createUserResponse(new CreateUserAndTokenRequest(userName, password));

        CreateUserAndTokenRequest loginRequest = new CreateUserAndTokenRequest(userName, password);

        return given()
                .log().all()
                .body(loginRequest)
                .contentType(JSON)

                .when()
                .post("https://demoqa.com/Account/v1/Login")

                .then()
                .log().all()
                .extract().as(LoginResponse.class);

    }
}
