package tests.api;

import org.junit.jupiter.api.Test;
import utils.model.*;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


public class AddBooksToUserTest {

    @Test
    void addBooksToUserTest() {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        GetBooksResponse getBooksResponse = new GetBooksResponse();

        AddBooksToUserRequest addBooksToUserRequest = new AddBooksToUserRequest();
        addBooksToUserRequest.setUserId(createUserResponse.getUserID());

        String id = createUserResponse.getUserID();
        System.out.println(id);
        String isbn = getBooksResponse.getBooks().get(3).getIsbn();

        CollectionOfIsbnsModel collectionOfIsbnsModel = new CollectionOfIsbnsModel();
        collectionOfIsbnsModel.setIsbn(isbn);

        addBooksToUserRequest.getCollectionOfIsbns().add(collectionOfIsbnsModel);

        System.out.println(isbn);

        AddBooksToUserResponse addBooksToUserResponse = given()
                .log().all()
                .body(addBooksToUserRequest)
                .contentType(JSON)

                .when()
                .post("https://demoqa.com/BookStore/v1/Books")

                .then()
                .log().all()
                .extract().as(AddBooksToUserResponse.class);
    }
}
