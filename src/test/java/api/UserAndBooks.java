package api;

import utils.model.CreateUserRequest;
import utils.model.CreateUserResponse;
import utils.model.GetBooksResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class UserAndBooks {

    public CreateUserResponse createUserResponse(CreateUserRequest userRequest) {

        return given()
                .body(userRequest)
                .contentType(JSON)

                .when()
                .post("https://demoqa.com/Account/v1/User")

                .then()
                .extract()
                .response().as(CreateUserResponse.class);
    }

    public GetBooksResponse getBooksResponse() {

        return given()
                .contentType(JSON)
                .log().all()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().all()
                .extract().as(GetBooksResponse.class);
    }
}
