package com.education.service.serviceImpl.article;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.dto.article.ArticleDto;
import com.education.dto.base.ResponsePageDto;
import com.education.entity.article.ArticleEntity;
import com.education.entity.system.UserEntity;
import com.education.entity.uploadfile.UploadFileEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.article.ArticleMapper;
import com.education.mapper.upload.UploadFileMapper;
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
import org.apache.tomcat.util.bcel.Const;
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

    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Override
    public void addArticle(ArticleDto articleDto) throws Exception {
        //????????????
        ArticleEntity articleEntity = new ArticleEntity();
        BeanUtils.copyProperties(articleDto, articleEntity);
        //????????????
        UserEntity shiroEntity = ShiroEntityUtil.getShiroEntity();
        articleEntity.setUserId(shiroEntity.getId());
        EntityUtil.addCreateInfo(articleEntity);
        articleMapper.insert(articleEntity);
        //?????????????????????????????????
        if (StringUtils.isNotBlank(articleDto.getFilePaths())) {
            List<String> filePathList = Arrays.asList(articleDto.getFilePaths().split(","));

            uploadFileMapper.updateRelativeId(filePathList, articleEntity.getId());
        }

        //??????????????????,?????????es???
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
        //????????????
        if (StringUtils.isBlank(articleDto.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "??????????????????");
        }
        //????????????
        ArticleEntity articleEntity = articleMapper.selectById(articleDto.getId());
        if (articleEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "???????????????");
        }
        //????????????
        articleEntity.setIsDeleted(Constant.ISDELETED_TRUE);
        EntityUtil.addModifyInfo(articleEntity);

        articleMapper.updateById(articleEntity);


    }

    @Override
    public Result getArticleById(ArticleDto articleDto) {
        if (StringUtils.isBlank(articleDto.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "??????????????????");
        }
        ArticleEntity articleEntity = new ArticleEntity();
        //????????????
        if (RedisUtil.hasKey(Constant.REDIS_ARTICE_CACHE + articleDto.getId())) {
            ArticleVo articleVo = JSONObject.parseObject(RedisUtil.get(Constant.REDIS_ARTICE_CACHE + articleDto.getId()), ArticleVo.class);
            BeanUtils.copyProperties(articleVo, articleEntity);
        } else {
            articleEntity = articleMapper.selectById(articleDto.getId());
            if (articleEntity == null) {
                throw new EducationException(ResultCode.FAILER_CODE.getCode(), "???????????????");
            }
        }
        return Result.success().data("data", articleEntity);
    }

    @Override
    public void updateArticle(ArticleDto articleDto) throws Exception {
        //????????????
        ArticleEntity articleEntityLocal = new ArticleEntity();
        ArticleEntity articleEntity = articleMapper.selectById(articleDto.getId());
        if (articleEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "???????????????");
        }
        BeanUtils.copyProperties(articleEntity, articleEntityLocal);
        BeanUtils.copyProperties(articleDto, articleEntity);
        EntityUtil.addModifyInfo(articleEntity);

        articleMapper.updateById(articleEntity);
        //??????????????????
        if (StringUtils.isNotBlank(articleDto.getFilePaths())) {
            List<String> filePathListLocal = Arrays.asList(articleDto.getFilePaths().split(","));
            ArrayList<String> filePathList = new ArrayList<>(filePathListLocal);
            ArrayList<String> delFilePathList = new ArrayList<>(filePathListLocal);
            //???????????????????????????????????????
            QueryWrapper<UploadFileEntity> uploadFileEntityQueryWrapper = new QueryWrapper<>();
            uploadFileEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                    .eq("relative_id", articleEntity.getId());
            List<UploadFileEntity> uploadFileEntities = uploadFileMapper.selectList(uploadFileEntityQueryWrapper);
            List<String> uploadFileList = uploadFileEntities.stream().map(o -> o.getFilePath()).collect(Collectors.toList());

            //?????????????????????????????????
            filePathList.removeAll(uploadFileList);
            if (filePathList.size() > 0) {
                uploadFileMapper.updateRelativeId(filePathList, articleEntity.getId());
            }
            //???????????????????????????
            uploadFileList.removeAll(delFilePathList);
            if (uploadFileList.size() > 0) {
                uploadFileMapper.delRelativeId(uploadFileList, articleEntity.getId());
            }

        }
        //??????????????????,?????????es???

        if (articleEntity.getStatus().equals(Constant.NUMBER_ONE)) {
            if (articleEntityLocal.getStatus().equals(articleEntity.getStatus())) {
                //??????es??????
                elasticSearchService.update(articleEntity);
            } else {
                elasticSearchService.save(articleEntity);
            }

        } else {
            //??????es?????????
            elasticSearchService.delete(articleEntity);
        }


    }

    @Override
    public void zsetArticle() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //??????????????????????????????
        Calendar calendar = Calendar.getInstance();
        //??????????????????
        Date nowTime = new Date();
        calendar.setTime(nowTime);
        //????????????????????????
        calendar.add(Calendar.DATE, -7);
        Date queryDate = calendar.getTime();
        //??????????????????????????????????????????
        Date formatAfterTime = format.parse(format.format(queryDate));
        Date formatBeforeTime = format.parse(format.format(nowTime));
        //????????????
        List<ArticleVo> articleVos = articleMapper.queryZsetArticle(formatAfterTime, formatBeforeTime);
        //1.redis???????????????ZSET???????????????2.??????redis???String??????????????????(??????????????????????????????????????????????????????hash)

        //????????????(???????????????????????????keys?????????????????????scan?????????IO,????????????????????????)
        Set<String> keys = RedisUtil.keys(Constant.REDIS_ARTICE_CACHE + "*");
        List<String> keysList = keys.stream().collect(Collectors.toList());
        //????????????????????????
        Set<ZSetOperations.TypedTuple<String>> weekRankData = RedisUtil.zReverseRangeWithScores(Constant.REDIS_ARTICE_KEY, 0, -1);
        if (keysList != null && keysList.size() > 0) {
            if (weekRankData != null && weekRankData.size() > 0) {
                keysList.add(Constant.REDIS_ARTICE_KEY);
            }
            RedisUtil.delete(keysList);
        }
        //????????????
        for (ArticleVo articleVo : articleVos) {
            RedisUtil.zAdd(Constant.REDIS_ARTICE_KEY, articleVo.getId(), articleVo.getCommentQuality());
            //?????????????????????1.???????????????????????????2.????????????
            long expireTime = formatBeforeTime.getTime() - articleVo.getGmtCreate().getTime();
            String articleKey = Constant.REDIS_ARTICE_CACHE + articleVo.getId();
            this.addArticleCache(articleKey, articleVo, expireTime);
        }
    }

    private void addArticleCache(String articleKey, ArticleVo articleVo, Long expireTime) {
        //???????????????????????????
        RedisUtil.setEx(articleKey, JSON.toJSONString(articleVo), expireTime / 1000, TimeUnit.SECONDS);
    }

    @Override
    public Result queryCacheArticle() {
        //?????????
        ArrayList<ArticleVo> list = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<String>> Articles = RedisUtil.zReverseRangeWithScores(Constant.REDIS_ARTICE_KEY, 0, -1);
        for (ZSetOperations.TypedTuple article : Articles) {
            ArticleVo articleVo = new ArticleVo();
            //??????????????????
            if (RedisUtil.hasKey(Constant.REDIS_ARTICE_CACHE + article.getValue())) {
                ArticleVo articleVoCache = JSONObject.parseObject(RedisUtil.get(Constant.REDIS_ARTICE_CACHE + article.getValue()), ArticleVo.class);
                BeanUtils.copyProperties(articleVoCache, articleVo);
            } else {
                ArticleEntity articleEntity = articleMapper.selectById(article.getValue().toString());
                if (null == articleEntity) {
                    throw new EducationException(ResultCode.FAILER_CODE.getCode(), "???????????????");
                }
                BeanUtils.copyProperties(articleEntity, articleVo);
            }
            //????????????
            articleVo.setId(article.getValue().toString());
            articleVo.setCommentQuality(article.getScore().intValue());
            list.add(articleVo);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", list);
        //???????????????
        return Result.success().data(map);
    }
}
