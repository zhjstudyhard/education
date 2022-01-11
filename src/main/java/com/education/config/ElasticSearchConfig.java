package com.education.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-11-17-01
 */
@Configuration
public class ElasticSearchConfig {
    //ES配置类
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient restHighLevelClient =
                new RestHighLevelClient(
                        RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        return restHighLevelClient;
    }

}
