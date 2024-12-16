package com.ofg.elasticwithrdbms.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;

import java.util.ArrayList;
import java.util.List;

public class ESUtil {
    public static Query buildQueryForFieldsAndValues(List<String> fieldNames, List<String> searchValues) {
        if (fieldNames == null || searchValues == null || fieldNames.size() != searchValues.size()) {
            throw new IllegalArgumentException("Field names and search values must be non-null and of the same size.");
        }

        List<Query> matchQueries = new ArrayList<>();

        for (int i = 0; i < fieldNames.size(); i++) {
            int counter = i;
            matchQueries.add(Query.of(q -> q.match(buildMatchQueryForFieldAndValue(fieldNames.get(counter), searchValues.get(counter)))));
        }

        return Query.of(q -> q.bool(BoolQuery.of(b -> b.must(matchQueries))));
    }

    public static MatchQuery buildMatchQueryForFieldAndValue(String fieldName, String searchValue) {
        return new MatchQuery.Builder()
                .field(fieldName)
                .query(searchValue)
                .build();
    }
}
