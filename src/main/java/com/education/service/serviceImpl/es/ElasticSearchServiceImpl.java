package com.education.service.serviceImpl.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.entity.article.ArticleEntity;
import com.education.entity.article.DocumentArticleEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.article.ArticleMapper;
import com.education.service.es.ElasticSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.bcel.Const;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-11-18-46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ElasticSearchServiceImpl implements ElasticSearchService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void saveAll() throws Exception {
        //同步es数据,1.创建索引,2.查询mysql数据库,3,插入es数据库
        QueryWrapper<ArticleEntity> articleEntityQueryWrapper = new QueryWrapper<>();
        articleEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("status", Constant.NUMBER_ONE);
        //查询数据
        List<ArticleEntity> articleEntities = articleMapper.selectList(articleEntityQueryWrapper);
        //封装es数据模型
        ArrayList<DocumentArticleEntity> documentArticleEntities = new ArrayList<>();
        for (ArticleEntity articleEntity : articleEntities) {
            DocumentArticleEntity documentArticleEntity = new DocumentArticleEntity();
            BeanUtils.copyProperties(articleEntity, documentArticleEntity);

            documentArticleEntities.add(documentArticleEntity);
        }
        //创建索引并加入到es中
        BulkRequest bulkRequest = new BulkRequest();
        for (DocumentArticleEntity documentArticleEntity : documentArticleEntities) {
            IndexRequest indexRequest = new IndexRequest(Constant.ARTICLE_INDEX).id(documentArticleEntity.getId())
                    .source(JSON.toJSONString(documentArticleEntity), XContentType.JSON);

            bulkRequest.add(indexRequest);
        }
        //插入es数据
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (!bulk.hasFailures()) {
            logger.info("es数据新增成功");
        } else {
            logger.info("es数据新增出现错误");
        }
    }

    @Async
    @Override
    public void save(ArticleEntity articleEntity) throws Exception {
        DocumentArticleEntity documentArticleEntity = copyEntity(articleEntity);
        //添加es数据
        IndexRequest indexRequest = new IndexRequest(Constant.ARTICLE_INDEX).id(documentArticleEntity.getId())
                .source(JSON.toJSONString(documentArticleEntity), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        logger.info("es数据添加成功");
    }

    @Async
    @Override
    public void delete(ArticleEntity articleEntity) throws Exception {
        //判断目标文档是否存在
        Boolean flag = exists(articleEntity.getId());
        if (flag) {
            //删除文档数据
            DocumentArticleEntity documentArticleEntity = copyEntity(articleEntity);
            DeleteRequest deleteRequest = new DeleteRequest(Constant.ARTICLE_INDEX, documentArticleEntity.getId());
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        }

        logger.info("es数据删除成功");
    }

    @Async
    @Override
    public void update(ArticleEntity articleEntity) throws Exception {
        //判断目标文档是否存在
        Boolean flag = exists(articleEntity.getId());
        if (flag) {
            //更新文档数据
            DocumentArticleEntity documentArticleEntity = copyEntity(articleEntity);
            UpdateRequest updateRequest = new UpdateRequest(Constant.ARTICLE_INDEX, documentArticleEntity.getId());
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        }

        logger.info("es数据更新成功");
    }

    //属性赋值
    public DocumentArticleEntity copyEntity(ArticleEntity articleEntity) {
        //属性赋值
        DocumentArticleEntity documentArticleEntity = new DocumentArticleEntity();
        BeanUtils.copyProperties(articleEntity, documentArticleEntity);

        return documentArticleEntity;
    }

    //判断文档是否存在
    public Boolean exists(String id) throws IOException {
        GetRequest request = new GetRequest(Constant.ARTICLE_INDEX, id);
        //不获取返回的上下文 _source
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    @Override
    public Result esSearch(String keyWords) throws Exception {
        //条件搜索
        SearchRequest searchRequest = new SearchRequest(Constant.ARTICLE_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询构造器
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyWords, "content", "description", "title");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        //高亮构造器
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //是否多个字段高亮;
        highlightBuilder.requireFieldMatch(true);
        //设置高亮的字段
        highlightBuilder.field("title");
        highlightBuilder.field("description");
        highlightBuilder.field("content");
        //设置前缀
        highlightBuilder.preTags("<span style='color:red'>");
        //设置后缀
        highlightBuilder.postTags("</span>");

        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        //执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //获取查询数据
        List<DocumentArticleEntity> list = new ArrayList<>();

        SearchHit[] hits = searchResponse.getHits().getHits();
        if (hits.length > 0){
            for (SearchHit searchHit : hits) {
                DocumentArticleEntity documentArticleEntity = JSONObject.parseObject(searchHit.getSourceAsString(), DocumentArticleEntity.class);
                //高亮字段
                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                if (highlightFields.containsKey("title")){
                    documentArticleEntity.setTitle(highlightFields.get("title").getFragments()[0].toString());
                }
                if (highlightFields.containsKey("description")){
                    documentArticleEntity.setDescription(highlightFields.get("description").getFragments()[0].toString());
                }
                if (highlightFields.containsKey("content")){
                    documentArticleEntity.setContent(highlightFields.get("content").getFragments()[0].toString());
                }
                list.add(documentArticleEntity);
            }
        }
        return Result.success().data("data", list);
//        return Result.success().data("data", searchResponse);
    }
}
