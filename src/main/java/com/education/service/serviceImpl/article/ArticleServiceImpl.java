package com.education.service.serviceImpl.article;

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
import com.education.util.ShiroEntityUtil;
import com.education.vo.ArticleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "逐渐不能为空");
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
        if (articleEntity == null){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"数据不存在");
        }
        BeanUtils.copyProperties(articleDto, articleEntity);
        EntityUtil.addModifyInfo(articleEntity);

        articleMapper.updateById(articleEntity);
    }
}
