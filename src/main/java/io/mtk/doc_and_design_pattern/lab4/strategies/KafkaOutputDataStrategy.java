package io.mtk.doc_and_design_pattern.lab4.strategies;

import io.mtk.doc_and_design_pattern.lab4.DataFileReader;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Properties;

public class KafkaOutputDataStrategy implements OutputDataStrategy {
    private Properties properties;
    private String bootstrapServers;
    private String topicName;


    public KafkaOutputDataStrategy() {
        this.properties = new Properties();
        this.bootstrapServers = DataFileReader.loadConfigProperty().getProperty("BOOTS_TRAP_SERVERS");
        this.topicName = DataFileReader.loadConfigProperty().getProperty("TOPIC_NAME");

        this.properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        this.properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        this.properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    }


    @Override
    public void execute(List<String> data) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(this.properties);

        for (int i = 0; i < data.size(); i++) {
            producer.send(new ProducerRecord<>(this.topicName, String.valueOf(i + 1), data.get(i)));
        }

        producer.flush();
        producer.close();
    }
}

