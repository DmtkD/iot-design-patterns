package io.mtk.doc_and_design_pattern.lab3.data;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReviewDataGenerator {
    private static Integer primary_key = 0;
    private static Faker faker = new Faker(new Locale("en-US"));

    public static List<String> generate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String reviewId = Integer.toString(++primary_key);
        String reviewUser = Integer.toString(faker.number().numberBetween(1, 1000));
        String text = String.format("\"%s\"", faker.lorem().paragraph());
        String reviewRating = Integer.toString(faker.number().numberBetween(1, 5));
        Date dateAndTime = faker.date().past(1000, TimeUnit.DAYS);
        String reviewDate = dateFormat.format(dateAndTime);
        String reviewTime = timeFormat.format(dateAndTime);
        String editStatus = Boolean.toString(faker.bool().bool());

        return List.of(reviewId, reviewUser, text, reviewRating, reviewDate, reviewTime, editStatus);
    }
}