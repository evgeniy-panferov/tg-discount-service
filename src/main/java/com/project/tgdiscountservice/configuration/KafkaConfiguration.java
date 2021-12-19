//package com.project.tgdiscountservice.configuration;
//
//import com.fasterxml.jackson.databind.JsonSerializer;
//import org.apache.kafka.common.serialization.LongSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.EnableKafkaStreams;
//import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
//import org.springframework.kafka.config.KafkaStreamsConfiguration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.apache.kafka.streams.StreamsConfig.*;
//
//@Configuration
//@EnableKafka
//@EnableKafkaStreams
//public class KafkaConfiguration {
//
//    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
//    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(APPLICATION_ID_CONFIG, "streams-app");
//        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, LongSerializer.class);
//        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerializer.class);
//
//        return new KafkaStreamsConfiguration(props);
//    }
//}
