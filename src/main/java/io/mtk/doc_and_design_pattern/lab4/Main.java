package io.mtk.doc_and_design_pattern.lab4;

import io.mtk.doc_and_design_pattern.lab4.strategies.ConsoleOutputDataStrategy;
import io.mtk.doc_and_design_pattern.lab4.strategies.KafkaOutputDataStrategy;

import java.util.Properties;

public class Main {
    private static final String CONSOLE_KEY = "CONSOLE";
    private static final String KAFKA_KEY = "KAFKA";

    public static void main(String[] args) {
        DataProcessor processor = new DataProcessor();
        Properties config = DataFileReader.loadConfigProperty();
        String outputDataStrategy = config.getProperty("STRATEGY");

        if (CONSOLE_KEY.equals(outputDataStrategy)) {
            processor.setOutputStrategy(new ConsoleOutputDataStrategy());
        } else if (KAFKA_KEY.equals(outputDataStrategy)) {
            processor.setOutputStrategy(new KafkaOutputDataStrategy());
        }

        processor.processData(DataFileReader.getReadDataFromCSV());
    }
}