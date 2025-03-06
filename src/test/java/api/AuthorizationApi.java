package api;

import io.qameta.allure.Step;
import io.restassured.specification.ResponseSpecification;
import models.CreateUserAndTokenRequest;
import models.GenerateTokenResponse;
import specs.BaseSpec;
import specs.ApiTestBase;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.commonRequestSpec;

public class AuthorizationApi extends ApiTestBase {

    @Step("Генерируем токен")
    public GenerateTokenResponse generateTokenResponse(CreateUserAndTokenRequest userRequest) {

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(200);
        return given(commonRequestSpec)
                .body(userRequest)

                .when()
                .post(ApiTestBase.generateTokenPath)

                .then()
                .spec(responseSpecification)
                .extract().as(GenerateTokenResponse.class);
    }
}
