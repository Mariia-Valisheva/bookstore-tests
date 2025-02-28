package tests.ui;

import api.AuthorizationApi;
import api.LoginApi;
import models.CreateUserAndTokenRequest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DeleteBookFromProfileTest extends TestBase {

    AuthorizationApi authorizationApi = new AuthorizationApi();
    LoginApi loginApi = new LoginApi();

    @Test
    void deleteBooksFromProfileTest() {

        String userIdCookie = loginApi.loginResponse().getUserId();
        String userName = loginApi.loginResponse().getUsername();
        String password = loginApi.loginResponse().getPassword();

        CreateUserAndTokenRequest authRequest = new CreateUserAndTokenRequest(userName, password);

        String token = authorizationApi.generateTokenResponse(authRequest).getToken();
        String expires = authorizationApi.generateTokenResponse(authRequest).getExpires();

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", userIdCookie));
        getWebDriver().manage().addCookie(new Cookie("token", token));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));

        open("/profile");

    }
}
