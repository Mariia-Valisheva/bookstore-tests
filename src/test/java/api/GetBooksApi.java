package api;

import io.qameta.allure.Step;
import io.restassured.specification.ResponseSpecification;
import models.GetBooksResponse;
import specs.BaseSpec;
import specs.ApiTestBase;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.commonRequestSpec;

public class GetBooksApi extends ApiTestBase {

    @Step("Получаем список книг")
    public GetBooksResponse getBooksResponse() {

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(200);

        return given(commonRequestSpec)
                .get(ApiTestBase.getBooksPath)
                .then()
                .spec(responseSpecification)
                .extract().as(GetBooksResponse.class);
    }
}
