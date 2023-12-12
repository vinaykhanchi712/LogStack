package com.vinay.logstack.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinay.logstack.config.KafkaProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static final ObjectMapper MAPPER = new ObjectMapper();

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaProducer<String,String> kafkaProducer;

    @Autowired
    private KafkaProducerConfig kafkaProducerConfig;

    public void pushDataToKafka(Object object , String topic) throws Exception{
        String kafkaMessage=null;

        try{
            LOG.info("[KafkaService]pushing data to kafka topic: {}", topic);
            if(!kafkaProducerConfig.topicExists(topic)){
                kafkaProducerConfig.createTopic(topic);
            }
            kafkaMessage = MAPPER.writeValueAsString(object);

            Future<RecordMetadata> future  = kafkaProducer.send(new ProducerRecord<>(topic, kafkaMessage), (metadata, exception) -> {
                if (exception == null) {
                    LOG.info("[KafkaService] Data pushed successfully to Kafka topic {}: offset={}, partition={}",
                            topic, metadata.offset(), metadata.partition());
                } else {
                    LOG.error("[KafkaService] Error while pushing data {} to Kafka for topic {}", object, topic, exception);
                }
            });

            try {
                RecordMetadata metadata = future.get(5000, TimeUnit.MILLISECONDS); // 5 seconds timeout
                LOG.info("[KafkaService] Data pushed successfully to Kafka topic {}: offset={}, partition={}",
                        topic, metadata.offset(), metadata.partition());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupted status
                LOG.error("[KafkaService] Thread interrupted while waiting for the result", e);
            } catch (ExecutionException | TimeoutException e) {
                LOG.error("[KafkaService] Error or timeout while pushing data {} to Kafka for topic {}", object, topic, e.getCause());
            }


        }catch (Exception exception){
            LOG.error("[KafkaService] Error while pushing data {} to kafka for topic {} : {}",
                    object, topic, exception);
            throw new RuntimeException(exception);
        }
    }
}
