package com.yldog.elastic.query.client.util;

import com.yldog.elastic.model.index.IndexModel;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.Queries;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ElasticQueryUtil<T extends IndexModel> {

    public Query getSearchQueryById(String id) {
        return NativeQuery.builder()
                .withIds(Collections.singleton(id))
                .build();
    }

    public Query getSearchQueryByFieldText(String field, String text) {
        return NativeQuery.builder()
                .withQuery(Queries.matchQueryAsQuery(field, text, null, null))
                .build();
    }

    public Query getSearchQueryForAll() {
        return NativeQuery.builder()
                .withQuery(Queries.matchAllQueryAsQuery())
                .build();
    }
}
