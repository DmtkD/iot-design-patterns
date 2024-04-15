package io.mtk.doc_and_design_pattern.lab3.data;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;

public class ModeratorDataGenerator {
    private static Integer primary_key = 0;
    private static Faker faker = new Faker(new Locale("en-US"));

    public static List<String> generate() {
        String moderatorId = Integer.toString(++primary_key);
        String moderatorName = faker.name().firstName();
        String moderatorSurname = faker.name().lastName();
        String moderatorFullName = faker.name().fullName();
        String moderatorPosition = faker.company().profession();
        String moderatorEmail = faker.internet().emailAddress();
        String moderatorPassword = faker.internet().password();

        return List.of(moderatorId, moderatorName, moderatorSurname, moderatorFullName,
                moderatorPosition, moderatorEmail, moderatorPassword);
    }
}