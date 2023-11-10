package com.yldog.elastic.query.serivce.model.assembler;

import com.yldog.elastic.model.index.impl.TwitterIndexModel;
import com.yldog.elastic.query.serivce.api.ElasticDocumentController;
import com.yldog.elastic.query.serivce.model.ElasticQueryServiceResponseModel;
import com.yldog.elastic.query.serivce.transformer.ElasticToResponseModelTransformer;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ElasticQueryServiceResponseModelAssembler
extends RepresentationModelAssemblerSupport<TwitterIndexModel, ElasticQueryServiceResponseModel> {
    private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

    public ElasticQueryServiceResponseModelAssembler(ElasticToResponseModelTransformer elasticToResponseModelTransformer) {
        super(ElasticDocumentController.class, ElasticQueryServiceResponseModel.class);
        this.elasticToResponseModelTransformer = elasticToResponseModelTransformer;
    }

    @Override
    public ElasticQueryServiceResponseModel toModel(TwitterIndexModel twitterIndexModel) {
        ElasticQueryServiceResponseModel responseModel =
                elasticToResponseModelTransformer.getResponseModel(twitterIndexModel);

        responseModel.add(
            linkTo(methodOn(ElasticDocumentController.class)
                    .getDocumentById(twitterIndexModel.getId()))
                    .withSelfRel()
        );

        responseModel.add(
                linkTo(ElasticDocumentController.class)
                        .withRel("documents")
        );
        return responseModel;
    }

    public List<ElasticQueryServiceResponseModel> toModels(List<TwitterIndexModel> twitterIndexModels) {
        return twitterIndexModels.stream().map(this::toModel).collect(Collectors.toList());
    }
}
