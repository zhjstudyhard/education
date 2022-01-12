package com.education.service.es;

import com.education.common.Result;
import com.education.entity.article.ArticleEntity;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-11-18-46
 */
public interface ElasticSearchService{

    void saveAll() throws Exception;

    void save(ArticleEntity articleEntity) throws Exception;

    void delete(ArticleEntity articleEntity) throws Exception;

    void update(ArticleEntity articleEntity) throws Exception;

    Result esSearch(String keyWords) throws Exception;
}
