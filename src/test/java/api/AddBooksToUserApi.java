package api;

import models.AddBooksToUserRequest;
import models.AddBooksToUserResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class AddBooksToUserApi {
    public AddBooksToUserResponse addBooksToUserResponse(AddBooksToUserRequest addBooksToUserRequest, String token) {
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
