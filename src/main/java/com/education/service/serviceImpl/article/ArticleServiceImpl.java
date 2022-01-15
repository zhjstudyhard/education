package com.education.service.serviceImpl.article;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.dto.article.ArticleDto;
import com.education.dto.base.ResponsePageDto;
import com.education.entity.article.ArticleEntity;
import com.education.entity.system.UserEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.article.ArticleMapper;
import com.education.service.article.ArticleService;
import com.education.service.es.ElasticSearchService;
import com.education.util.EntityUtil;
import com.education.util.RedisUtil;
import com.education.util.ShiroEntityUtil;
import com.education.vo.ArticleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-15-09-59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Override
    public void addArticle(ArticleDto articleDto) throws Exception {
        //复制属性
        ArticleEntity articleEntity = new ArticleEntity();
        BeanUtils.copyProperties(articleDto, articleEntity);
        //添加数据
        UserEntity shiroEntity = ShiroEntityUtil.getShiroEntity();
        articleEntity.setUserId(shiroEntity.getId());
        EntityUtil.addCreateInfo(articleEntity);
        articleMapper.insert(articleEntity);

        //判断是否启用,添加入es中
        if (articleEntity.getStatus().equals(Constant.NUMBER_ONE)) {
            elasticSearchService.save(articleEntity);
        }
    }

    @Override
    public Result getArticlePage(ArticleDto articleDto) {
        UserEntity userEntity = ShiroEntityUtil.getShiroEntity();
        if (articleDto.getIsAdmin() != null) {
            if (articleDto.getIsAdmin().equals(0)) {
                articleDto.setUserId(userEntity.getId());
            } else {
                Boolean flag = ShiroEntityUtil.checkPermission();
                if (flag) {
                    articleDto.setUserId(userEntity.getId());
                }
            }
        }

        PageHelper.startPage(articleDto.getCurrentPage(), articleDto.getPageSize());
        PageInfo<ArticleVo> pageInfo = new PageInfo<>(articleMapper.getArticlePage(articleDto));
        ResponsePageDto<ArticleVo> articleVoResponsePageDto = new ResponsePageDto<>(pageInfo.getList(), pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum());
        return Result.success().data("data", articleVoResponsePageDto);
    }

    @Override
    public Result getFontArticlePage(ArticleDto articleDto) {
        PageHelper.startPage(articleDto.getCurrentPage(), articleDto.getPageSize());
        PageInfo<ArticleVo> pageInfo = new PageInfo<>(articleMapper.getFontArticlePage(articleDto));
        ResponsePageDto<ArticleVo> articleVoResponsePageDto = new ResponsePageDto<>(pageInfo.getList(), pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum());
        return Result.success().data("data", articleVoResponsePageDto);
    }

    @Override
    public Result getAllArticle(ArticleDto articleDto) {
        UserEntity userEntity = ShiroEntityUtil.getShiroEntity();

        if (articleDto.getIsAdmin() != null) {
            if (articleDto.getIsAdmin().equals(0)) {
                articleDto.setUserId(userEntity.getId());
            } else {
                Boolean flag = ShiroEntityUtil.checkPermission();
                if (flag) {
                    articleDto.setUserId(userEntity.getId());
                }
            }
        }

        PageInfo<ArticleVo> pageInfo = new PageInfo<>(articleMapper.getArticlePage(articleDto));
        ResponsePageDto<ArticleVo> articleVoResponsePageDto = new ResponsePageDto<>(pageInfo.getList(), pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum());
        return Result.success().data("data", articleVoResponsePageDto);
    }

    @Override
    public void deleteArticleById(ArticleDto articleDto) throws Exception {
        //校验参数
        if (StringUtils.isBlank(articleDto.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不能为空");
        }
        //校验数据
        ArticleEntity articleEntity = articleMapper.selectById(articleDto.getId());
        if (articleEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }
        //更新数据
        articleEntity.setIsDeleted(Constant.ISDELETED_TRUE);
        EntityUtil.addModifyInfo(articleEntity);

        articleMapper.updateById(articleEntity);


    }

    @Override
    public Result getArticleById(ArticleDto articleDto) {
        if (StringUtils.isBlank(articleDto.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不能为空");
        }
        ArticleEntity articleEntity = new ArticleEntity();
        //查询缓存
        if (RedisUtil.hasKey(Constant.REDIS_ARTICE_CACHE + articleDto.getId())) {
            ArticleVo articleVo = JSONObject.parseObject(RedisUtil.get(Constant.REDIS_ARTICE_CACHE + articleDto.getId()), ArticleVo.class);
            BeanUtils.copyProperties(articleVo, articleEntity);
        } else {
            articleEntity = articleMapper.selectById(articleDto.getId());
            if (articleEntity == null) {
                throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
            }
        }
        return Result.success().data("data", articleEntity);
    }

    @Override
    public void updateArticle(ArticleDto articleDto) throws Exception {
        //校验数据
        ArticleEntity articleEntityLocal = new ArticleEntity();
        ArticleEntity articleEntity = articleMapper.selectById(articleDto.getId());
        if (articleEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }
        BeanUtils.copyProperties(articleEntity, articleEntityLocal);
        BeanUtils.copyProperties(articleDto, articleEntity);
        EntityUtil.addModifyInfo(articleEntity);

        articleMapper.updateById(articleEntity);

        //判断是否启用,添加入es中

        if (articleEntity.getStatus().equals(Constant.NUMBER_ONE)) {
            if (articleEntityLocal.getStatus().equals(articleEntity.getStatus())) {
                //更新es文档
                elasticSearchService.update(articleEntity);
            } else {
                elasticSearchService.save(articleEntity);
            }

        } else {
            //删除es中数据
            elasticSearchService.delete(articleEntity);
        }


    }

    @Override
    public void zsetArticle() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //查询七天内的文章数据
        Calendar calendar = Calendar.getInstance();
        //日历设置时间
        Date nowTime = new Date();
        calendar.setTime(nowTime);
        //获取七天前的时间
        calendar.add(Calendar.DATE, -7);
        Date queryDate = calendar.getTime();
        //格式化时间，只是用年月日查询
        Date formatAfterTime = format.parse(format.format(queryDate));
        Date formatBeforeTime = format.parse(format.format(nowTime));
        //查询数据
        List<ArticleVo> articleVos = articleMapper.queryZsetArticle(formatAfterTime, formatBeforeTime);
        //1.redis缓存文章，ZSET数据类型，2.设置redis的String缓存文章数据(对象字段过多，且更改不频繁，暂不考虑hash)

        //删除缓存(由于数据量不大使用keys命令，可以改成scan非阻塞IO,因为懒不想太麻烦)
        Set<String> keys = RedisUtil.keys(Constant.REDIS_ARTICE_CACHE + "*");
        List<String> keysList = keys.stream().collect(Collectors.toList());
        //删除文章热议缓存
        Set<ZSetOperations.TypedTuple<String>> weekRankData = RedisUtil.zReverseRangeWithScores(Constant.REDIS_ARTICE_KEY, 0, -1);
        if (keysList != null && keysList.size() > 0) {
            if (weekRankData != null && weekRankData.size() > 0) {
                keysList.add(Constant.REDIS_ARTICE_KEY);
            }
            RedisUtil.delete(keysList);
        }
        //添加缓存
        for (ArticleVo articleVo : articleVos) {
            RedisUtil.zAdd(Constant.REDIS_ARTICE_KEY, articleVo.getId(), articleVo.getCommentQuality());
            //缓存文章数据，1.设置文章过期时间，2.缓存数据
            long expireTime = formatBeforeTime.getTime() - articleVo.getGmtCreate().getTime();
            String articleKey = Constant.REDIS_ARTICE_CACHE + articleVo.getId();
            this.addArticleCache(articleKey, articleVo, expireTime);
        }
    }

    private void addArticleCache(String articleKey, ArticleVo articleVo, Long expireTime) {
        //把文章数据加入缓存
        RedisUtil.setEx(articleKey, JSON.toJSONString(articleVo), expireTime / 1000, TimeUnit.SECONDS);
    }

    @Override
    public Result queryCacheArticle() {
        //结果集
        ArrayList<ArticleVo> list = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<String>> Articles = RedisUtil.zReverseRangeWithScores(Constant.REDIS_ARTICE_KEY, 0, -1);
        for (ZSetOperations.TypedTuple article : Articles) {
            ArticleVo articleVo = new ArticleVo();
            //缓存查询属性
            if (RedisUtil.hasKey(Constant.REDIS_ARTICE_CACHE + article.getValue())) {
                ArticleVo articleVoCache = JSONObject.parseObject(RedisUtil.get(Constant.REDIS_ARTICE_CACHE + article.getValue()), ArticleVo.class);
                BeanUtils.copyProperties(articleVoCache, articleVo);
            } else {
                ArticleEntity articleEntity = articleMapper.selectById(article.getValue().toString());
                if (null == articleEntity) {
                    throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
                }
                BeanUtils.copyProperties(articleEntity, articleVo);
            }
            //属性赋值
            articleVo.setId(article.getValue().toString());
            articleVo.setCommentQuality(article.getScore().intValue());
            list.add(articleVo);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", list);
        //返回结果集
        return Result.success().data(map);
    }
}
