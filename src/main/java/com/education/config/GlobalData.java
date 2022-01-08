package com.education.config;

import com.education.entity.system.DictionaryEntity;
import com.education.service.system.DictionaryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-08-18-12
 */
@Configuration
public class GlobalData implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private DictionaryService dictionaryService;

    public static Map<String, DictionaryEntity> dictMap = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        dictionaryService.globalDictData();
        logger.info("数据字典加载完成");
    }
}
