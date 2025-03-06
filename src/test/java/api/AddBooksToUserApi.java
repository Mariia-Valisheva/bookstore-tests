package api;

import io.qameta.allure.Step;
import io.restassured.specification.ResponseSpecification;
import models.AddBooksToUserRequest;
import models.AddBooksToUserResponse;
import models.CollectionOfIsbnsModel;
import specs.BaseSpec;
import specs.ApiTestBase;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.commonRequestSpec;

public class AddBooksToUserApi extends ApiTestBase {

    @Step("Добавляем книги пользователю")
    public AddBooksToUserResponse addBooksToUserResponse(String userId, String token) {

        AddBooksToUserRequest addBooksToUserRequest = new AddBooksToUserRequest();
        addBooksToUserRequest.setUserId(userId);

        GetBooksApi getBooksApi = new GetBooksApi();
        String isbn = getBooksApi.getBooksResponse().getBooks().get(1).getIsbn();
        CollectionOfIsbnsModel collectionOfIsbnsModel = new CollectionOfIsbnsModel();
        collectionOfIsbnsModel.setIsbn(isbn);
        addBooksToUserRequest.setCollectionOfIsbns(Collections.singletonList(collectionOfIsbnsModel));

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(201);

        return given(commonRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(addBooksToUserRequest)

                .when()
                .post(ApiTestBase.getBooksPath)

                .then()
                .spec(responseSpecification)
                .extract().as(AddBooksToUserResponse.class);
    }
}
