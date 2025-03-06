package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.ConfigData;
import config.WebDriverConfig;
import helpers.Attachments;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    private final ConfigData configData = ConfigFactory.create(ConfigData.class, System.getProperties());

    @BeforeAll
    static void configParams() {
        WebDriverConfig webDriverConfig = new WebDriverConfig();
        webDriverConfig.configParams();
        RestAssured.baseURI = "https://demoqa.com";
    }

    @BeforeEach
    void addSelenideListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }


    @AfterEach
    void addAttachments() {
        if (configData.isRemote()) {
            if (!Configuration.browser.equals("firefox")) {
                Attachments.addScreenshot("Test screenshot");
                Attachments.addPageSource();
                Attachments.addBrowserConsoleLogs();
                Attachments.addVideo();
            }
        } else {
            Attachments.addScreenshot("Test screenshot");
            Attachments.addPageSource();
            Attachments.addBrowserConsoleLogs();
        }
        Selenide.closeWebDriver();
    }
}
