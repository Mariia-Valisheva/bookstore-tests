package tests.api;

import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import models.GetUserInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import specs.ApiTestBase;
import specs.BaseSpec;
import utils.BookStoreTestData;
import models.CreateUserAndTokenRequest;
import models.CreateUserResponse;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BaseSpec.commonRequestSpec;

@Tag("API")
@DisplayName("Тесты на работу с пользователем")
public class UserTests extends ApiTestBase {

    BookStoreTestData bookStoreTestData = new BookStoreTestData();

    @DisplayName("Тест на создание пользователя")
    @Test
    void createUserTest() {

        CreateUserAndTokenRequest userRequest = new CreateUserAndTokenRequest(bookStoreTestData.userName, bookStoreTestData.password);
        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(201);


        CreateUserResponse createUserResponse = step("Создаем пользователя", () ->
                given(commonRequestSpec)
                        .body(userRequest)

                        .when()
                        .post(ApiTestBase.getUserPath)

                        .then()
                        .spec(responseSpecification)
                        .extract().as(CreateUserResponse.class)
        );

        step("Проверяем, что пользователь создан успешно", () ->
                {
                    assertThat(createUserResponse.getUserId()).isNotEmpty();
                }
        );
    }


    @DisplayName("Тест на получение информации о пользователе")
    @Test
    void getUserInfoTest() {
        String UUID = "test";
        String token = "test";

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(200);


        GetUserInfoResponse getUserInfoResponse = step("Получаем информацию по пользователю", () ->
                given(commonRequestSpec)
                        .header("Authorization", "Bearer " + token)
                        .get(ApiTestBase.getUserPath + UUID)
                        .then()
                        .spec(responseSpecification)
                        .extract().as(GetUserInfoResponse.class)
        );

        step("Проверяем полученную информацию", () ->
                {
                    assertThat(getUserInfoResponse.getUsername()).isNotEmpty();
                    assertThat(getUserInfoResponse.getUserId()).isNotEmpty();
                    assertThat(getUserInfoResponse.getBooks()).isNotEmpty();
                }
        );
    }


    @DisplayName("Тест на удаление пользователя")
    @Test
    void deleteUserTest() {
        String UUID = "test";
        String token = "test";

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(204);

        Response response = step("Удаляем созданного пользователя", () ->
                (Response) given(commonRequestSpec)
                        .header("Authorization", "Bearer " + token)
                        .delete(ApiTestBase.getUserPath + UUID)
                        .then()
                        .spec(responseSpecification)
                        .extract().body()

        );

        step("Проверяем, что пользователь удалился", () ->

                {
                    assertThat(response.getBody().asString()).isEmpty();
                }
        );
    }

}
