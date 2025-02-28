package api;

import models.GetBooksResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class GetBooksApi {

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
