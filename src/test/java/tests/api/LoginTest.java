package tests.api;

import api.AuthorizationApi;
import api.CreateUserApi;
import io.restassured.specification.ResponseSpecification;
import models.CreateUserAndTokenRequest;
import models.CreateUserResponse;
import models.GenerateTokenResponse;
import models.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.ApiTestBase;
import specs.BaseSpec;
import utils.BookStoreTestData;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BaseSpec.commonRequestSpec;

@DisplayName("Тесты на генерацию токена и логин")
public class LoginTest extends ApiTestBase {


    @DisplayName("Тест на генерацию токена")
    @Test
    void generateTokenTest() {

        BookStoreTestData bookStoreTestData = new BookStoreTestData();
        String userName = bookStoreTestData.userName;
        String password = bookStoreTestData.password;

        CreateUserResponse createUserResponse = new CreateUserApi().createUserResponse(new CreateUserAndTokenRequest(userName, password));

        CreateUserAndTokenRequest createTokenRequest = new CreateUserAndTokenRequest(createUserResponse.getUsername(), password);

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(200);


        GenerateTokenResponse generateTokenResponse = step("Генерим токен на созданного пользователя", () ->

                given(commonRequestSpec)
                        .body(createTokenRequest)

                        .when()
                        .post(ApiTestBase.generateTokenPath)

                        .then()
                        .spec(responseSpecification)
                        .extract().as(GenerateTokenResponse.class)
        );


        step("Проверяем, что токен не пустой", () ->

                {
                    assertThat(generateTokenResponse.getToken()).isNotEmpty();
                }
        );
    }


    @DisplayName("Тест на успешный логин")
    @Test
    void loginWithNewUserTest() {
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

        LoginResponse loginResponse = step("Логинимся с созданным пользователем", () ->

                given(commonRequestSpec)
                        .body(loginRequest)

                        .when()
                        .post(ApiTestBase.loginPath)

                        .then()
                        .spec(responseSpecification)
                        .extract().as(LoginResponse.class)
        );
        step("Проверяем, что получили токен и дату протухания", () ->
                {
                    assertThat(loginResponse.getToken()).isNotEmpty();
                    assertThat(loginResponse.getExpires()).isNotEmpty();
                }
        );
    }
}

