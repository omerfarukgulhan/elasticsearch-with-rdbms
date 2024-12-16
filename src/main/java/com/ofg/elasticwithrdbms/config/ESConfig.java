package com.ofg.elasticwithrdbms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ofg.elasticwithrdbms.repository")
@ComponentScan(basePackages = {"com.ofg.elasticwithrdbms"})
public class ESConfig extends ElasticsearchConfiguration {

    @Value("${app.elasticsearch.url}")
    private String elasticsearchUrl;


    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUrl)
                .build();
    }
}
