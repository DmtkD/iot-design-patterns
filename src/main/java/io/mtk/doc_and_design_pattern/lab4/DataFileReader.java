package io.mtk.doc_and_design_pattern.lab4;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataFileReader {
    private static final String dataFileName = "C:\\Users\\Turchynyak\\LPNU\\Iot-design-patterns\\src\\main\\resources\\test_data.csv";
    private static final String configFileName = "C:\\Users\\Turchynyak\\LPNU\\iot-design-patterns\\src\\main\\resources\\config.ini";
    private static Properties properties;

    public static List<String> getReadDataFromCSV() {
        List<String> readData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFileName));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                String rowData = csvRecord.toString();
                readData.add(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return readData;
    }

    private static void loadConfigFromFile() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(configFileName)) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Properties loadConfigProperty() {
        if (properties == null) loadConfigFromFile();
        return properties;
    }
}
