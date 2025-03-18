package tests.ui;

import api.AddBooksToUserApi;
import api.DeleteUserApi;
import api.GetUserInfoApi;
import api.LoginApi;
import helpers.CookieHelper;
import models.GetUserInfoResponse;
import models.LoginResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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

        AddBooksToUserApi addBooksToUserApi = new AddBooksToUserApi();
        addBooksToUserApi.addBooksToUserResponse(loginData.getUserId(), loginData.getToken());

        CookieHelper.addLoginCookie(loginData.getUserId(), loginData.getToken(), loginData.getExpires());

        profilePage
                .openProfilePage("/profile")
                .checkSuccessLogin(loginData.getUsername())
                .deleteBook()
                .checkDeletedBook("No rows found");


        step("Проверяем, что книги удалились (api)", () ->
        {
            GetUserInfoResponse responseWithoutBooks = new GetUserInfoApi().getUserInfoResponse(loginData.getToken(), loginData.getUserId());
            assertThat(responseWithoutBooks.getBooks()).isEmpty();

        });

        DeleteUserApi deleteUserApi = new DeleteUserApi();
        Response response = deleteUserApi.deleteUserResponse(loginData.getToken(), loginData.getUserId());
        SoftAssertions.assertSoftly(
                softAssertions -> {
                    assertThat(response.asString()).isEmpty();
                    assertThat(response.statusCode()).isEqualTo(204);
                });
    }
}
