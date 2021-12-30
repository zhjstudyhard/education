package com.education.service.serviceImpl.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.education.util.EntityUtil;
import com.education.util.RedisUtil;
import com.education.util.ShiroEntityUtil;
import com.education.vo.ArticleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-15-09-59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addArticle(ArticleDto articleDto) {
        //复制属性
        ArticleEntity articleEntity = new ArticleEntity();
        BeanUtils.copyProperties(articleDto, articleEntity);
        //添加数据
        UserEntity shiroEntity = ShiroEntityUtil.getShiroEntity();
        articleEntity.setUserId(shiroEntity.getId());
        EntityUtil.addCreateInfo(articleEntity);
        articleMapper.insert(articleEntity);
    }

    @Override
    public Result getArticlePage(ArticleDto articleDto) {
        PageHelper.startPage(articleDto.getCurrentPage(), articleDto.getPageSize());
        PageInfo<ArticleVo> pageInfo = new PageInfo<>(articleMapper.getArticlePage(articleDto));
        ResponsePageDto<ArticleVo> articleVoResponsePageDto = new ResponsePageDto<>(pageInfo.getList(), pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum());
        return Result.success().data("data", articleVoResponsePageDto);
    }

    @Override
    public void deleteArticleById(ArticleDto articleDto) {
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
        ArticleEntity articleEntity = articleMapper.selectById(articleDto.getId());
        if (articleEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }
        return Result.success().data("data", articleEntity);
    }

    @Override
    public void updateArticle(ArticleDto articleDto) {
        //校验数据
        ArticleEntity articleEntity = articleMapper.selectById(articleDto.getId());
        if (articleEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }
        BeanUtils.copyProperties(articleDto, articleEntity);
        EntityUtil.addModifyInfo(articleEntity);

        articleMapper.updateById(articleEntity);
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
        Date formatAfterTime = format.parse(format.format(format));
        Date formatBeforeTime = format.parse(format.format(nowTime));
        //查询数据
        List<ArticleVo> articleVos = articleMapper.queryZsetArticle(formatAfterTime, formatBeforeTime);
        //1.redis缓存文章，ZSET数据类型，2.设置redis的String缓存文章数据(对象字段过多，且更改不频繁，暂不考虑hash)
        for (ArticleVo articleVo : articleVos) {
            RedisUtil.zAdd(Constant.REDIS_ARTICE_KEY, articleVo.getId(), articleVo.getCommentQuality());
            //缓存文章数据，1.设置文章过期时间，2.缓存数据
            long expireTime = articleVo.getGmtCreate().getTime() - nowTime.getTime();
            String articleKey = Constant.REDIS_ARTICE_CACHE + articleVo.getId();
            this.addArticleCache(articleKey, articleVo, expireTime);
        }
    }

    private void addArticleCache(String articleKey, ArticleVo articleVo, Long expireTime) {

    }
}
