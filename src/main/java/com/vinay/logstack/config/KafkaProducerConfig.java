package com.vinay.logstack.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.internals.KafkaProducerMetrics;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private Environment env;

//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.hostname"));
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 5);
//        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
//        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
//        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15000);
//        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
//
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }

//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }

    @Bean
    public Properties getProducerProps() {
        Properties props = new Properties();
        props.put("bootstrap.servers", env.getProperty("kafka.hostname"));
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }



    @Bean
    public KafkaProducer<String,String> kafkaProducer(){
        return new KafkaProducer<>(getProducerProps());
    }

    public boolean topicExists(String topicName) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.hostname"));
        try (AdminClient client = AdminClient.create(props)) {
            return client.listTopics().names().get().contains(topicName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void createTopic(String topicName) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.hostname"));
        try (AdminClient client = AdminClient.create(props)) {
            NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
            client.createTopics(Collections.singletonList(newTopic));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
