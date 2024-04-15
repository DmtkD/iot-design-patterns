package io.mtk.doc_and_design_pattern.lab3.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManagerDataGenerator {
    private static final String fileName = "C:\\Users\\Turchynyak\\LPNU\\Iot-design-patterns\\src\\main\\resources\\test_data.csv";
    private static final int NUMBER_OF_ROWS = 1000;
    private static final List<String> APP_HEADER = List.of("appId", "appName", "type", "appRating", "version", "latestUpdateLog");
    private static final List<String> DEVELOPER_HEADER = List.of("developerId", "developerName", "developerSurname", "bankCard", "nameOfCompany", "developerEmail", "developerPassword");
    private static final List<String> MODERATOR_HEADER = List.of("moderatorId", "moderatorName", "moderatorSurname", "moderatorFullName", "moderatorPosition", "moderatorEmail", "moderatorPassword");
    private static final List<String> REVIEW_HEADER = List.of("reviewId", "reviewUser", "text", "reviewRating", "reviewDate", "reviewTime", "editStatus");
    private static final List<String> UPDATE_STATUS_HEADER = List.of("updateStatusId", "updateStatusDate", "fixVersion", "updateLog");
    private static final List<String> USER_HEADER = List.of("userId", "userName", "userSurname", "userUsername", "userEmail", "userPassword");
    private static final List<String> INSTALL_APPS_HEADER = List.of("installUserId", "installAppId");
    private static final List<String> PUBLISHED_APPS_HEADER = List.of("publishedDeveloperId", "publishedAppId");
    private static final List<String> UPDATE_STATUS_HISTORY_HEADER = List.of("appHistoryId", "updateStatusHistoryId");
    private static final List<String> WISHED_APPS_HEADER = List.of("wishedUserId", "wishedAppId");

    public static void main(String[] args) {
        generateCSV();
    }

    private static void generateCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(String.join(",", createHeader()));
            writer.newLine();

            List<String> rowData = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_ROWS; i++) {
                rowData.addAll(AppDataGenerator.generate());
                rowData.addAll(DeveloperDataGenerator.generate());
                rowData.addAll(ModeratorDataGenerator.generate());
                rowData.addAll(ReviewDataGenerator.generate());
                rowData.addAll(UpdateStatusDataGenerator.generate());
                rowData.addAll(UserDataGenerator.generate());
                rowData.addAll(DockingTableDataGenerator.generate());  // INSTALL_APPS
                rowData.addAll(DockingTableDataGenerator.generate());  // PUBLISHED_APPS
                rowData.addAll(DockingTableDataGenerator.generate());  // UPDATE_STATUS_HISTORY
                rowData.addAll(DockingTableDataGenerator.generate());  // WISHED_APPS

                writer.write(String.join(",", rowData));
                if (999 != i) writer.newLine();
                rowData.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> createHeader() {
        List<String> HEADER = new ArrayList<>();

        HEADER.addAll(APP_HEADER);
        HEADER.addAll(DEVELOPER_HEADER);
        HEADER.addAll(MODERATOR_HEADER);
        HEADER.addAll(REVIEW_HEADER);
        HEADER.addAll(UPDATE_STATUS_HEADER);
        HEADER.addAll(USER_HEADER);
        HEADER.addAll(INSTALL_APPS_HEADER);
        HEADER.addAll(PUBLISHED_APPS_HEADER);
        HEADER.addAll(UPDATE_STATUS_HISTORY_HEADER);
        HEADER.addAll(WISHED_APPS_HEADER);

        return HEADER;
    }

    public static List<List<String>> getGeneratedDataByType(TypeData typeData) {
        int[] indexOfData = new int[2];
        List<List<String>> generatedData = new ArrayList<>();
        switch (typeData) {
            case APP -> indexOfData = new int[]{0, 5};
            case DEVELOPER -> indexOfData = new int[]{6, 12};
            case MODERATOR -> indexOfData = new int[]{13, 19};
            case REVIEW -> indexOfData = new int[]{20, 26};
            case UPDATE_STATUS -> indexOfData = new int[]{27, 30};
            case USER -> indexOfData = new int[]{31, 36};
            case INSTALL_APPS -> indexOfData = new int[]{37, 38};
            case PUBLISHED_APPS -> indexOfData = new int[]{39, 40};
            case UPDATE_STATUS_HISTORY -> indexOfData = new int[]{41, 42};
            case WISHED_APPS -> indexOfData = new int[]{43, 44};
        }

        int startIndex = indexOfData[0];
        int endIndex = indexOfData[1] + 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                List<String> rowData = csvRecord.toMap().values().stream()
                        .toList().subList(startIndex, endIndex);
                generatedData.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return generatedData;
    }
}