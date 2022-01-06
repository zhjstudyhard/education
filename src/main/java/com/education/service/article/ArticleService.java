package com.education.service.article;

import com.education.common.Result;
import com.education.dto.article.ArticleDto;

import java.text.ParseException;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-15-09-58
 */
public interface ArticleService {
    void addArticle(ArticleDto articleDto);

    Result getArticlePage(ArticleDto articleDto);

    void deleteArticleById(ArticleDto articleDto);

    Result getArticleById(ArticleDto articleDto);

    void updateArticle(ArticleDto articleDto);

    void zsetArticle() throws Exception;

    Result queryCacheArticle();

    Result getAllArticle();
}
