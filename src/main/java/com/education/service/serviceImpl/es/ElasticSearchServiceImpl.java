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
        //??????es??????,1.????????????,2.??????mysql?????????,3,??????es?????????
        QueryWrapper<ArticleEntity> articleEntityQueryWrapper = new QueryWrapper<>();
        articleEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("status", Constant.NUMBER_ONE);
        //????????????
        List<ArticleEntity> articleEntities = articleMapper.selectList(articleEntityQueryWrapper);
        //??????es????????????
        ArrayList<DocumentArticleEntity> documentArticleEntities = new ArrayList<>();
        for (ArticleEntity articleEntity : articleEntities) {
            DocumentArticleEntity documentArticleEntity = new DocumentArticleEntity();
            BeanUtils.copyProperties(articleEntity, documentArticleEntity);

            documentArticleEntities.add(documentArticleEntity);
        }
        //????????????????????????es???
        BulkRequest bulkRequest = new BulkRequest();
        for (DocumentArticleEntity documentArticleEntity : documentArticleEntities) {
            IndexRequest indexRequest = new IndexRequest(Constant.ARTICLE_INDEX).id(documentArticleEntity.getId())
                    .source(JSON.toJSONString(documentArticleEntity), XContentType.JSON);

            bulkRequest.add(indexRequest);
        }
        //??????es??????
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (!bulk.hasFailures()) {
            logger.info("es??????????????????");
        } else {
            logger.info("es????????????????????????");
        }
    }

    @Async
    @Override
    public void save(ArticleEntity articleEntity) throws Exception {
        DocumentArticleEntity documentArticleEntity = copyEntity(articleEntity);
        //??????es??????
        IndexRequest indexRequest = new IndexRequest(Constant.ARTICLE_INDEX).id(documentArticleEntity.getId())
                .source(JSON.toJSONString(documentArticleEntity), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        logger.info("es??????????????????");
    }

    @Async
    @Override
    public void delete(ArticleEntity articleEntity) throws Exception {
        //??????????????????????????????
        Boolean flag = exists(articleEntity.getId());
        if (flag) {
            //??????????????????
            DocumentArticleEntity documentArticleEntity = copyEntity(articleEntity);
            DeleteRequest deleteRequest = new DeleteRequest(Constant.ARTICLE_INDEX, documentArticleEntity.getId());
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        }

        logger.info("es??????????????????");
    }

    @Async
    @Override
    public void update(ArticleEntity articleEntity) throws Exception {
        //??????????????????????????????
        Boolean flag = exists(articleEntity.getId());
        if (flag) {
            //??????????????????
            DocumentArticleEntity documentArticleEntity = copyEntity(articleEntity);
            UpdateRequest updateRequest = new UpdateRequest(Constant.ARTICLE_INDEX, documentArticleEntity.getId());
            updateRequest.doc(JSON.toJSONString(documentArticleEntity),XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        }

        logger.info("es??????????????????");
    }

    //????????????
    public DocumentArticleEntity copyEntity(ArticleEntity articleEntity) {
        //????????????
        DocumentArticleEntity documentArticleEntity = new DocumentArticleEntity();
        BeanUtils.copyProperties(articleEntity, documentArticleEntity);

        return documentArticleEntity;
    }

    //????????????????????????
    public Boolean exists(String id) throws IOException {
        GetRequest request = new GetRequest(Constant.ARTICLE_INDEX, id);
        //??????????????????????????? _source
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    @Override
    public Result esSearch(String keyWords) throws Exception {
        //????????????
        SearchRequest searchRequest = new SearchRequest(Constant.ARTICLE_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //???????????????
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyWords, "content", "description", "title");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        //???????????????
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //????????????????????????;
        highlightBuilder.requireFieldMatch(true);
        //?????????????????????
        highlightBuilder.field("title");
        highlightBuilder.field("description");
        highlightBuilder.field("content");
        //????????????
        highlightBuilder.preTags("<span style='color:red'>");
        //????????????
        highlightBuilder.postTags("</span>");

        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        //????????????
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //??????????????????
        List<DocumentArticleEntity> list = new ArrayList<>();

        SearchHit[] hits = searchResponse.getHits().getHits();
        if (hits.length > 0){
            for (SearchHit searchHit : hits) {
                DocumentArticleEntity documentArticleEntity = JSONObject.parseObject(searchHit.getSourceAsString(), DocumentArticleEntity.class);
                //????????????
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
