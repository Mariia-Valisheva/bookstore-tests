package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import specs.BaseSpec;
import specs.ApiTestBase;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.commonRequestSpec;

public class DeleteUserApi extends ApiTestBase {

    @Step("Удаляем пользователя")
    public Response deleteUserResponse(String token, String UUID) {

        ResponseSpecification responseSpecification = new BaseSpec().commonResponseSpec(204);

        return (Response) given(commonRequestSpec)
                .header("Authorization", "Bearer " + token)
                .delete(ApiTestBase.getUserPath + UUID)
                .then()
                .spec(responseSpecification)
                .extract().body();
    }
}
