package org.example.components;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Productor {

    private KafkaProducer<String, String> kafkaProducer;

    public Productor() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducer = new KafkaProducer<>(props);
    }

    public void send(String topic, String message) {
        kafkaProducer.send(new ProducerRecord<>(topic, message));
    }

    public void close() {
        kafkaProducer.close();
    }
}
