package tests.api;

import api.AuthorizationApi;
import api.CreateUserApi;
import api.GetBooksApi;
import models.AddBooksToUserRequest;
import models.AddBooksToUserResponse;
import models.CollectionOfIsbnsModel;
import models.CreateUserAndTokenRequest;
import org.junit.jupiter.api.Test;
import utils.BookStoreTestData;


import java.util.Collections;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class AddBooksToUserTest {

    CreateUserApi createUserApi = new CreateUserApi();
    BookStoreTestData bookStoreTestData = new BookStoreTestData();
    AuthorizationApi authorizationApi = new AuthorizationApi();
    GetBooksApi getBooksApi = new GetBooksApi();


    @Test
    void addBooksToUserTest() {
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

        AddBooksToUserResponse addBooksToUserResponse = given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .body(addBooksToUserRequest)
                .contentType(JSON)

                .when()
                .post("https://demoqa.com/BookStore/v1/Books")

                .then()
                .log().all()
                .extract().as(AddBooksToUserResponse.class);

        assertThat(addBooksToUserResponse.getBooks()).isNotEmpty();
        assertThat(addBooksToUserResponse.getBooks()).hasSize(1);
    }
}


