package io.mtk.doc_and_design_pattern.lab2.data;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;

public class DeveloperDataGenerator {
    private static Integer primary_key = 0;
    private static Faker faker = new Faker(new Locale("en-US"));

    public static List<String> generate() {
        String developerId = Integer.toString(++primary_key);
        String developerName = faker.name().firstName();
        String developerSurname = faker.name().lastName();
        String bankCard = faker.business().creditCardNumber();
        String nameOfCompany = String.format("\"%s\"", faker.company().name());
        String developerEmail = faker.internet().emailAddress();
        String developerPassword = faker.internet().password();

        return List.of(developerId, developerName, developerSurname,
                bankCard, nameOfCompany, developerEmail, developerPassword);
    }
}