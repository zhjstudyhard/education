package com.education.config;

import com.education.service.article.ArticleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Set;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-05-18-43
 */
@Configuration
public class ArticleRedisTask {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ArticleService articleService;

    //3.添加定时任务
    @Scheduled(cron = "0 0 0 * * ?") //每天凌晨0点触发
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() throws Exception {
        //更新文章排行榜数据
        articleService.zsetArticle();
        logger.info("文章缓存定时更新完成");
    }
}
