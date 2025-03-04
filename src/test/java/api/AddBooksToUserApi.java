package api;

import models.AddBooksToUserRequest;
import models.AddBooksToUserResponse;
import models.CollectionOfIsbnsModel;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class AddBooksToUserApi {
    public AddBooksToUserResponse addBooksToUserResponse(String userId, String token) {

        AddBooksToUserRequest addBooksToUserRequest = new AddBooksToUserRequest();
        addBooksToUserRequest.setUserId(userId);

        GetBooksApi getBooksApi = new GetBooksApi();
        String isbn = getBooksApi.getBooksResponse().getBooks().get(1).getIsbn();
        CollectionOfIsbnsModel collectionOfIsbnsModel = new CollectionOfIsbnsModel();
        collectionOfIsbnsModel.setIsbn(isbn);
        addBooksToUserRequest.setCollectionOfIsbns(Collections.singletonList(collectionOfIsbnsModel));

        return given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .body(addBooksToUserRequest)
                .contentType(JSON)

                .when()
                .post("https://demoqa.com/BookStore/v1/Books")

                .then()
                .log().all()
                .extract().as(AddBooksToUserResponse.class);
    }
}
