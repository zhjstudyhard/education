package com.education.controller.article;

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
     * @description 前台查询数据
     * @author 橘白
     * @date 2022/1/7 16:43
     */

    @PostMapping("getFontArticlePage")
    public Result getFontArticlePage(@RequestBody ArticleDto articleDto) {
        return articleService.getFontArticlePage(articleDto);
    }

    /**
     * @return com.education.common.Result
     * @description 查询所有文章
     * @author 橘白
     * @date 2022/1/6 20:42
     */
    @PostMapping("getAllArticle")
    public Result getAllArticle() {
        return articleService.getAllArticle();
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

    /**
     * @param articleDto
     * @return com.education.common.Result
     * @description 根据文章id查询数据
     * @author 橘白
     * @date 2021/12/16 16:18
     */

    @PostMapping("getArticleById")
    public Result getArticleById(@RequestBody ArticleDto articleDto) {
        return articleService.getArticleById(articleDto);
    }

    /**
     * @param articleDto
     * @return com.education.common.Result
     * @description 更新文章
     * @author 橘白
     * @date 2021/12/16 19:32
     */

    @PostMapping("updateArticle")
    public Result updateArticle(@RequestBody ArticleDto articleDto) {
        articleService.updateArticle(articleDto);
        return Result.success();
    }

    /**
     * @param articleDto
     * @return com.education.common.Result
     * @description 设置文章周榜
     * @author 橘白
     * @date 2021/12/30 17:29
     */

    @PostMapping("zsetArticle")
    public Result zsetArticle(@RequestBody ArticleDto articleDto) throws Exception {
        articleService.zsetArticle();
        return Result.success();
    }

    /**
     * @return com.education.common.Result
     * @description 缓存查询
     * @author 橘白
     * @date 2022/1/5 19:47
     */

    @PostMapping("queryCacheArticle")
    public Result queryCacheArticle() {
        return articleService.queryCacheArticle();
    }
}
