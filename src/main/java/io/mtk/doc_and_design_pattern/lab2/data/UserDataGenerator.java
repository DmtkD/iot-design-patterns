package io.mtk.doc_and_design_pattern.lab2.data;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;

public class UserDataGenerator {
    private static Integer primary_key = 0;
    private static Faker faker = new Faker(new Locale("en-US"));

    public static List<String> generate() {
        String userId = Integer.toString(++primary_key);
        String userName = faker.name().firstName();
        String userSurname = faker.name().lastName();
        String userUsername = faker.name().username();
        String userEmail = faker.internet().emailAddress();
        String userPassword = faker.internet().password();

        return List.of(userId, userName, userSurname, userUsername, userEmail, userPassword);
    }
}