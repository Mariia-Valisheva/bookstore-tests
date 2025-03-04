package tests.ui;

import api.AddBooksToUserApi;
import api.DeleteUserApi;
import api.GetUserInfoApi;
import api.LoginApi;
import models.GetUserInfoResponse;
import models.LoginResponse;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.assertj.core.api.Assertions.assertThat;
import io.restassured.response.Response;

public class DeleteBookFromProfileTest extends TestBase {


    @Test
    void deleteBooksFromProfileTest() {
        LoginResponse loginData = new LoginApi().loginResponse();

        AddBooksToUserApi addBooksToUserApi = new AddBooksToUserApi();
        addBooksToUserApi.addBooksToUserResponse(loginData.getUserId(), loginData.getToken());

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", loginData.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", loginData.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginData.getExpires()));

        open("/profile");
        $("#userName-value").shouldHave(text(loginData.getUsername()));


        GetUserInfoResponse responseWithBooks = new GetUserInfoApi().getUserInfoResponse(loginData.getToken(), loginData.getUserId());
        assertThat(responseWithBooks.getBooks()).isNotEmpty();

        $("#delete-record-undefined").click();
        $("#closeSmallModal-ok").click();
        confirm();
        $(".rt-noData").shouldHave(text("No rows found"));


        GetUserInfoResponse responseWithoutBooks = new GetUserInfoApi().getUserInfoResponse(loginData.getToken(), loginData.getUserId());
        assertThat(responseWithoutBooks.getBooks()).isEmpty();

        DeleteUserApi deleteUserApi = new DeleteUserApi();
        Response response = deleteUserApi.deleteUserResponse(loginData.getToken(), loginData.getUserId());
        assertThat(response.asString()).isEmpty();
        assertThat(response.statusCode()).isEqualTo(204);
    }
}
