package io.mtk.doc_and_design_pattern.lab3.data;

import com.github.javafaker.Faker;
import io.mtk.doc_and_design_pattern.lab3.model.Type;

import java.util.List;
import java.util.Locale;

public class AppDataGenerator {
    private static Integer primary_key = 0;
    private static Faker faker = new Faker(new Locale("en-US"));

    public static List<String> generate() {
        String appId = Integer.toString(++primary_key);
        String appName = faker.app().name();
        String type = String.valueOf(Type.values()[faker.number().numberBetween(1, 4)]);
        String appRating = Double.toString(faker.number().randomDouble(2, 1, 5));
        String version = faker.app().version();
        String latestUpdateLog = Integer.toString(faker.number().numberBetween(1, 1000));

        return List.of(appId, appName, type, appRating, version, latestUpdateLog);
    }
}