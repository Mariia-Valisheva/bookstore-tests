package tests.api;

import org.junit.jupiter.api.Test;
import models.GetBooksResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBooksTest {
    @Test
    void getBooksTest() {
        GetBooksResponse getBooksResponse = given()
                .contentType(JSON)
                .log().all()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().all()
                .extract().as(GetBooksResponse.class);

        assertThat(getBooksResponse.getBooks()).isNotEmpty();
    }
}
