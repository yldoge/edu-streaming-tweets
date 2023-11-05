package com.yldog.kafka.producer.service.impl;

import com.yldog.kafka.avro.model.TwitterAvroModel;
import com.yldog.kafka.producer.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TwitterKafkaProducer implements KafkaProducer<Long, TwitterAvroModel> {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaProducer.class);
    private final KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;

    public TwitterKafkaProducer(KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, Long key, TwitterAvroModel message) {
        LOG.info("Sending message='{}' to topic='{}'", message, topicName);
        CompletableFuture<SendResult<Long, TwitterAvroModel>> kafkaResultFuture =
                kafkaTemplate.send(topicName, key, message);
        addCallBack(topicName, message, kafkaResultFuture);
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            LOG.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }

    private void addCallBack(String topicName,
                             TwitterAvroModel message,
                             CompletableFuture<SendResult<Long, TwitterAvroModel>> kafkaResultFuture) {
        kafkaResultFuture.whenComplete((result, throwable) -> {
            if (throwable != null) {
                LOG.error("Error while sending message {} to topic {}", message, topicName, throwable);
            } else {
                RecordMetadata metadata = result.getRecordMetadata();
                LOG.debug("Received new metadata, Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            }
        });
    }


}
