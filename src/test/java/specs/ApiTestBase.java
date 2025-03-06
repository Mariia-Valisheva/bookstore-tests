package specs;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class ApiTestBase {

    @BeforeAll
    public static void configParams() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    public  static final String loginPath = "/Account/v1/Login";
    public  static final String getUserPath = "/Account/v1/User/";
    public  static final String getBooksPath = "/BookStore/v1/Books";
    public  static final String generateTokenPath = "/Account/v1/GenerateToken";
}