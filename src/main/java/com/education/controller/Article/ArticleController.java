package com.education.controller.Article;

import com.education.common.Result;
import com.education.dto.article.ArticleDto;
import com.education.service.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-15-09-52
 */
@RestController
@RequestMapping("/api/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * @param articleDto
     * @return com.education.common.Result
     * @description 添加文章
     * @author 橘白
     * @date 2021/12/15 9:56
     */
    @PostMapping("addArticle")
    public Result addArticle(@Validated @RequestBody ArticleDto articleDto) {
        articleService.addArticle(articleDto);
        return Result.success();
    }

    /**
     * @param articleDto
     * @return com.education.common.Result
     * @description 分页查询文章数据
     * @author 橘白
     * @date 2021/12/15 15:22
     */
    @PostMapping("getArticlePage")
    public Result getArticlePage(@RequestBody ArticleDto articleDto) {
        return articleService.getArticlePage(articleDto);
    }

    /**
     * @param articleDto
     * @return com.education.common.Result
     * @description 根据id删除文章
     * @author 橘白
     * @date 2021/12/15 16:55
     */

    @PostMapping("deleteArticleById")
    public Result deleteArticleById(@RequestBody ArticleDto articleDto) {
        articleService.deleteArticleById(articleDto);
        return Result.success();
    }
}
