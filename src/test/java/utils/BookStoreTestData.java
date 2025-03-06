package utils;

import com.github.javafaker.Faker;

public class BookStoreTestData {
    Faker faker = new Faker();
    private String
            password0 = faker.internet().password(8, 12),
            upperCase = "ABV",
            special = "!#",
            digits = faker.number().digits(2);


    public String
            userName = faker.internet().emailAddress(),
            password = password0 + digits + special + upperCase;
}
