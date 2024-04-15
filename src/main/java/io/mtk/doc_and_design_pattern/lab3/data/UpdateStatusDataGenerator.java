package io.mtk.doc_and_design_pattern.lab3.data;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class UpdateStatusDataGenerator {
    private static Integer primary_key = 0;
    private static Faker faker = new Faker(new Locale("en-US"));

    public static List<String> generate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String updateStatusId = Integer.toString(++primary_key);
        String updateStatusDate = dateFormat.format(faker.date().past(1000, TimeUnit.DAYS));
        String fixVersion = faker.app().version();
        String updateLog = String.format("\"%s\"", faker.lorem().paragraph());

        return List.of(updateStatusId, updateStatusDate, fixVersion, updateLog);
    }
}