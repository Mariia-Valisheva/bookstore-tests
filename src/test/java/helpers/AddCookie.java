package helpers;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import tests.ui.TestBase;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AddCookie extends TestBase {

    @Step("Добавляем куки авторизации")
    public AddCookie addCookie(String userId, String token, String expires) {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("token", token));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        return this;
    }
}
