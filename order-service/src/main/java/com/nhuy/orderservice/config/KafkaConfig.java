package com.nhuy.orderservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.*;
import org.springframework.kafka.core.*;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public ProducerFactory<String, String> producerFactory(KafkaProperties props) {
        Map<String, Object> cfg = props.buildProducerProperties();
        cfg.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        cfg.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        cfg.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        cfg.put(ProducerConfig.ACKS_CONFIG, "all");
        cfg.put(ProducerConfig.RETRIES_CONFIG, 10);
        cfg.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "ordersvc-tx"); // unique per instance
        return new DefaultKafkaProducerFactory<>(cfg);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> pf) {
        var kt = new KafkaTemplate<>(pf);
        kt.setObservationEnabled(true);
        return kt;
    }


    @Bean(name = "kafkaTxManager")
    public KafkaTransactionManager<String, String> kafkaTxManager(ProducerFactory<String, String> pf) {
        return new KafkaTransactionManager<>(pf);
    }
}

