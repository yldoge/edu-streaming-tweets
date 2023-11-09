package com.yldog.kafka.to.elastic.service.consumer.impl;

import com.yldog.config.KafkaConfigData;
import com.yldog.config.KafkaConsumerConfigData;
import com.yldog.elastic.index.client.service.ElasticIndexClient;
import com.yldog.elastic.model.index.impl.TwitterIndexModel;
import com.yldog.kafka.admin.client.KafkaAdminClient;
import com.yldog.kafka.avro.model.TwitterAvroModel;
import com.yldog.kafka.to.elastic.service.consumer.KafkaConsumer;
import com.yldog.kafka.to.elastic.service.transformer.AvroToElasticModelTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TwitterAvroModel> {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaConsumer.class);
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaAdminClient kafkaAdminClient;
    private final KafkaConfigData kafkaConfigData;
    private final KafkaConsumerConfigData kafkaConsumerConfigData;
    private final AvroToElasticModelTransformer avroToElasticmodelTransformer;
    private final ElasticIndexClient<TwitterIndexModel> elasticIndexClient;

    public TwitterKafkaConsumer(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
                                KafkaAdminClient kafkaAdminClient,
                                KafkaConfigData kafkaConfigData,
                                KafkaConsumerConfigData kafkaConsumerConfigData,
                                AvroToElasticModelTransformer avroToElasticmodelTransformer,
                                ElasticIndexClient<TwitterIndexModel> elasticIndexClient) {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.kafkaAdminClient = kafkaAdminClient;
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaConsumerConfigData = kafkaConsumerConfigData;
        this.avroToElasticmodelTransformer = avroToElasticmodelTransformer;
        this.elasticIndexClient = elasticIndexClient;
    }

    @EventListener
    public void onAppStarted(ApplicationStartedEvent event) {
        kafkaAdminClient.checkTopicCreated();
        LOG.info("Topics with name {} is ready for operations!",
                kafkaConfigData.getTopicNamesToCreate().toArray());
        Objects.requireNonNull(
                kafkaListenerEndpointRegistry
                        .getListenerContainer(kafkaConsumerConfigData.getConsumerGroupId())
        ).start();
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.consumer-group-id}",
            topics = "${kafka-config.topic-name}")
    public void receive(@Payload List<TwitterAvroModel> message,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<Integer> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LOG.info("{} number of message received with keys {}, partitions {} and offsets {},  " +
                "sending it to elastic: Thread id {}",
                message.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString(),
                Thread.currentThread().getId());

        List<TwitterIndexModel> twitterIndex = avroToElasticmodelTransformer.getElasticModels(message);
        List<String> documentIds = elasticIndexClient.save(twitterIndex);
        LOG.info("Documents saved to elasticsearch with ids {}", documentIds.toArray());
    }
}
