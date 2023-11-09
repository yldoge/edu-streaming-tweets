package com.yldog.elastic.index.client.service.impl;

import com.yldog.elastic.index.client.repository.TwitterElasticsearchIndexRepository;
import com.yldog.elastic.index.client.service.ElasticIndexClient;
import com.yldog.elastic.model.index.impl.TwitterIndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "true", matchIfMissing = true)
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel> {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticIndexClient.class);
    private final TwitterElasticsearchIndexRepository twitterElasticsearchindexRepository;

    public TwitterElasticRepositoryIndexClient(
            TwitterElasticsearchIndexRepository twitterElasticsearchindexRepository) {
        this.twitterElasticsearchindexRepository = twitterElasticsearchindexRepository;
    }

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<TwitterIndexModel> repositoryResponse =
                (List<TwitterIndexModel>) twitterElasticsearchindexRepository.saveAll(documents);
        List<String> ids = repositoryResponse.stream()
                .map(TwitterIndexModel::getId)
                .collect(Collectors.toList());
        LOG.info("Documents indexed successfully with type: {} and ids: {}",
                TwitterIndexModel.class.getName(),
                ids);
        return ids;
    }
}
