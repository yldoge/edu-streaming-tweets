package com.yldog.elastic.query.serivce.business.impl;

import com.yldog.elastic.model.index.impl.TwitterIndexModel;
import com.yldog.elastic.query.client.service.ElasticQueryClient;
import com.yldog.elastic.query.serivce.business.ElasticQueryService;
import com.yldog.elastic.query.serivce.model.ElasticQueryServiceResponseModel;
import com.yldog.elastic.query.serivce.model.assembler.ElasticQueryServiceResponseModelAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TwitterElasticQueryService implements ElasticQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryService.class);
    private final ElasticQueryServiceResponseModelAssembler elasticQueryServiceResponseModelAssembler;
    private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;

    public TwitterElasticQueryService(
            ElasticQueryServiceResponseModelAssembler elasticQueryServiceResponseModelAssembler,
            ElasticQueryClient<TwitterIndexModel> elasticQueryClient
    ) {
        this.elasticQueryServiceResponseModelAssembler = elasticQueryServiceResponseModelAssembler;
        this.elasticQueryClient = elasticQueryClient;
    }

    @Override
    public ElasticQueryServiceResponseModel getDocumentById(String id) {
        LOG.info("Querying elasticsearch by id {}", id);
        return elasticQueryServiceResponseModelAssembler
                .toModel(elasticQueryClient.getIndexModelById(id));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        LOG.info("Querying elasticsearch by text {}", text);
        return elasticQueryServiceResponseModelAssembler
                .toModels(elasticQueryClient.getIndexModelByText(text));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getAllDocuments() {
        LOG.info("Querying all documents in elasticsearch");
        return elasticQueryServiceResponseModelAssembler
                .toModels(elasticQueryClient.getAllIndexModels());
    }
}
