package api;

import io.qameta.allure.Step;
import io.restassured.specification.ResponseSpecification;
import models.CreateUserAndTokenRequest;
import models.LoginResponse;
import specs.BaseSpec;
import specs.ApiTestBase;
import utils.BookStoreTestData;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.commonRequestSpec;

public class LoginApi extends ApiTestBase {

    @Step("Логинимся с созданным пользователем")
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

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(200);

        return given(commonRequestSpec)
                .body(loginRequest)

                .when()
                .post(ApiTestBase.loginPath)

                .then()
                .spec(responseSpecification)
                .extract().as(LoginResponse.class);
    }
}
