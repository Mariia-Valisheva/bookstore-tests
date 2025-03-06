package api;

import io.qameta.allure.Step;
import io.restassured.specification.ResponseSpecification;
import models.CreateUserAndTokenRequest;
import models.CreateUserResponse;
import specs.BaseSpec;
import specs.ApiTestBase;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.commonRequestSpec;

public class CreateUserApi extends ApiTestBase {

    @Step("Создаем пользователя")
    public CreateUserResponse createUserResponse(CreateUserAndTokenRequest userRequest) {

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(201);

        return given(commonRequestSpec)
                .body(userRequest)

                .when()
                .post(ApiTestBase.getUserPath)

                .then()
                .spec(responseSpecification)
                .extract().as(CreateUserResponse.class);
    }
}