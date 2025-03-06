package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {
    private final SelenideElement
            userName = $("#userName-value"),
            deleteBookButton = $("#delete-record-undefined"),
            closeModalButton = $("#closeSmallModal-ok"),
            tableNoDataRow = $(".rt-noData");


    @Step("Открываем страницу профиля")
    public ProfilePage openProfilePage(String url) {
        open(url);
        return this;
    }

    @Step("Проверяем, что успешно залогинились")
    public ProfilePage checkSuccessLogin(String expectedUserName) {
        userName.shouldHave(text(expectedUserName));
        return this;
    }


    @Step("Удаляем книгу из профиля")
    public ProfilePage deleteBook() {
        deleteBookButton.click();
        closeModalButton.click();
        confirm();
        return this;
    }


    @Step("Проверяем, что книга удалилась (ui)")
    public ProfilePage checkDeletedBook(String expectedText) {
        tableNoDataRow.shouldHave(text(expectedText));
        return this;
    }
}
