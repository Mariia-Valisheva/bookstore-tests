package tests.api;

import api.AuthorizationApi;
import api.CreateUserApi;
import api.GetBooksApi;
import io.restassured.specification.ResponseSpecification;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.ApiTestBase;
import specs.BaseSpec;
import utils.BookStoreTestData;

import java.util.Collections;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BaseSpec.commonRequestSpec;

@DisplayName("Тесты на работу с книгами")
public class BooksTest extends ApiTestBase {

    @DisplayName("Тест на получение списка книг")
    @Test
    void getBooksTest() {

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(200);

        GetBooksResponse getBooksResponse = step("Получаем информацию по книгам", () ->
                given(commonRequestSpec)
                        .get(ApiTestBase.getBooksPath)
                        .then()
                        .spec(responseSpecification)
                        .extract().as(GetBooksResponse.class)
        );

        step("Проверяем, что коллекция не пустая", () ->
                {
                    assertThat(getBooksResponse.getBooks()).isNotEmpty();
                }
        );
    }

    @DisplayName("Тест на добавление книг пользователю")
    @Test
    void addBooksToUserTest() {

        CreateUserApi createUserApi = new CreateUserApi();
        BookStoreTestData bookStoreTestData = new BookStoreTestData();
        AuthorizationApi authorizationApi = new AuthorizationApi();
        GetBooksApi getBooksApi = new GetBooksApi();


        String userName = bookStoreTestData.userName;
        String password = bookStoreTestData.password;

        String userId = createUserApi.createUserResponse(new CreateUserAndTokenRequest(userName, password)).getUserId();
        String token = authorizationApi.generateTokenResponse(new CreateUserAndTokenRequest(userName, password)).getToken();

        String isbn = getBooksApi.getBooksResponse().getBooks().get(1).getIsbn();

        AddBooksToUserRequest addBooksToUserRequest = new AddBooksToUserRequest();
        addBooksToUserRequest.setUserId(userId);

        CollectionOfIsbnsModel collectionOfIsbnsModel = new CollectionOfIsbnsModel();
        collectionOfIsbnsModel.setIsbn(isbn);
        addBooksToUserRequest.setCollectionOfIsbns(Collections.singletonList(collectionOfIsbnsModel));

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(201);


        AddBooksToUserResponse addBooksToUserResponse = step("Добавляем книги для созданного пользователя", () ->
                given(commonRequestSpec)
                        .header("Authorization", "Bearer " + token)
                        .body(addBooksToUserRequest)

                        .when()
                        .post(ApiTestBase.getBooksPath)

                        .then()
                        .spec(responseSpecification)
                        .extract().as(AddBooksToUserResponse.class)
        );

        step("Проверяем, что книги добавились", () ->
                {
                    assertThat(addBooksToUserResponse.getBooks()).isNotEmpty();
                    assertThat(addBooksToUserResponse.getBooks()).hasSize(1);
                }
        );
    }
}
