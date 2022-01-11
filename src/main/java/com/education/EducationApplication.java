package com.education;

import com.education.service.article.ArticleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EducationApplication implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ArticleService articleService;

    public static void main(String[] args) {
        SpringApplication.run(EducationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //加载缓存
        articleService.zsetArticle();
        logger.info("启动加载缓存");
    }
}
