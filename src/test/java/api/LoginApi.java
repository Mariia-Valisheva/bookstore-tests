package api;

import models.CreateUserAndTokenRequest;
import models.LoginResponse;
import utils.BookStoreTestData;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class LoginApi {


    public LoginResponse loginResponse() {
        BookStoreTestData bookStoreTestData = new BookStoreTestData();
        String userName = bookStoreTestData.userName;
        String password = bookStoreTestData.password;

        CreateUserApi createUserApi = new CreateUserApi();
        CreateUserAndTokenRequest createUserRequest = new CreateUserAndTokenRequest(userName, password);
        createUserApi.createUserResponse(createUserRequest);

        AuthorizationApi authorizationApi = new AuthorizationApi();
        CreateUserAndTokenRequest generateTokenRequest = new CreateUserAndTokenRequest(userName, password);
        authorizationApi.generateTokenResponse(generateTokenRequest);

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
