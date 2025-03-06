package tests.ui;

import api.AddBooksToUserApi;
import api.DeleteUserApi;
import api.GetUserInfoApi;
import api.LoginApi;
import models.GetUserInfoResponse;
import models.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.Response;
import pages.ProfilePage;

@Tag("WEB")
@DisplayName("Тест на удаление книг из профиля пользователя")
public class DeleteBookFromProfileTest extends TestBase {

    ProfilePage profilePage = new ProfilePage();


    @DisplayName("Тест на удаление книг из профиля пользователя")
    @Test
    void deleteBooksFromProfileTest() {
        LoginResponse loginData = new LoginApi().loginResponse();

        step("Добавляем книги пользователю", () ->
        {
            AddBooksToUserApi addBooksToUserApi = new AddBooksToUserApi();
            addBooksToUserApi.addBooksToUserResponse(loginData.getUserId(), loginData.getToken());
        });

        step("Добавляем куки", () ->
        {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", loginData.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("token", loginData.getToken()));
            getWebDriver().manage().addCookie(new Cookie("expires", loginData.getExpires()));
        });

        profilePage
                .openProfilePage("/profile")
                .checkSuccessLogin(loginData.getUsername());

        step("Проверяем, что у пользователя есть книги", () ->
        {
            GetUserInfoResponse responseWithBooks = new GetUserInfoApi().getUserInfoResponse(loginData.getToken(), loginData.getUserId());
            assertThat(responseWithBooks.getBooks()).isNotEmpty();
        });

        profilePage
                .deleteBook()
                .checkDeletedBook("No rows found");


        step("Проверяем, что книги удалились (api)", () ->
        {
            GetUserInfoResponse responseWithoutBooks = new GetUserInfoApi().getUserInfoResponse(loginData.getToken(), loginData.getUserId());
            assertThat(responseWithoutBooks.getBooks()).isEmpty();

        });

        step("Удаляем пользователя", () -> {
            DeleteUserApi deleteUserApi = new DeleteUserApi();
            Response response = deleteUserApi.deleteUserResponse(loginData.getToken(), loginData.getUserId());

            step("Проверяем, что пользователь удален", () ->
                    {
                        assertThat(response.asString()).isEmpty();
                        assertThat(response.statusCode()).isEqualTo(204);
                    }
            );
        });
    }
}
