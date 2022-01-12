package com.education.controller.es;

import com.education.common.Result;
import com.education.service.es.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-11-18-16
 */
@RestController
@RequestMapping("/api/es")
@CrossOrigin
public class EsController {
    @Autowired
    private ElasticSearchService elasticSearchService;

    /**
     * @description 批量添加ES数据
     * @author 橘白
     * @date 2022/1/11 18:47
     */

    @PostMapping("saveAll")
    public void saveAll() throws Exception {
        elasticSearchService.saveAll();
    }

    /**
     * @param keyWords
     * @return com.education.common.Result
     * @description es搜索查询
     * @author 橘白
     * @date 2022/1/12 15:13
     */

    @PostMapping("esSearch")
    public Result esSearch(@RequestBody String keyWords) throws Exception {
        return elasticSearchService.esSearch(keyWords);
    }

}
