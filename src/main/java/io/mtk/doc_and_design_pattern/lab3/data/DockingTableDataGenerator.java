package io.mtk.doc_and_design_pattern.lab3.data;


import java.util.List;
import java.util.Random;

public class DockingTableDataGenerator {
    private final static Random random = new Random();

    public static List<String> generate() {
        String firstForeignKeyId = String.valueOf(random.nextInt(1000 - 1) + 1);
        String secondForeignKeyId = String.valueOf(random.nextInt(1000 - 1) + 1);

        return List.of(firstForeignKeyId, secondForeignKeyId);
    }
}