package api;

import io.qameta.allure.Step;
import io.restassured.specification.ResponseSpecification;
import models.GetUserInfoResponse;
import specs.BaseSpec;
import specs.ApiTestBase;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.commonRequestSpec;

public class GetUserInfoApi extends ApiTestBase {

    @Step("Получаем информацию о пользователе")
    public GetUserInfoResponse getUserInfoResponse(String token, String UUID) {

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(200);

        return given(commonRequestSpec)
                .header("Authorization", "Bearer " + token)
                .get(ApiTestBase.getUserPath + UUID)
                .then()
                .spec(responseSpecification)
                .extract().as(GetUserInfoResponse.class);
    }
}
