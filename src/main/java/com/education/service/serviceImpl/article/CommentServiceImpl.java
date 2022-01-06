package com.education.service.serviceImpl.article;

import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.dto.article.CommentDto;
import com.education.dto.base.ResponsePageDto;
import com.education.entity.article.ArticleEntity;
import com.education.entity.article.CommentEntity;
import com.education.entity.system.UserEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.article.ArticleMapper;
import com.education.mapper.article.CommentMapper;
import com.education.service.article.CommentService;
import com.education.util.EntityUtil;
import com.education.vo.CommentVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-26-19-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addComment(CommentDto commentDto) {
        Subject subject = SecurityUtils.getSubject();
        UserEntity userEntity = (UserEntity) subject.getPrincipal();
        //属性赋值
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(commentDto, commentEntity,new String[]{"id"});
        commentEntity.setUserId(userEntity.getId());
        EntityUtil.addCreateInfo(commentEntity);
        //判断是否博主自己评论
        ArticleEntity articleEntity = articleMapper.selectById(commentDto.getArticleId());
        if (articleEntity == null){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"文章数据不存在");
        }

        if (articleEntity.getUserId().equals(userEntity.getId())){
            commentEntity.setArticleUser(1);
        }else {
            commentEntity.setArticleUser(0);
        }
        //添加
        commentMapper.insert(commentEntity);
    }

    @Override
    public void delComment(CommentDto commentDto) {
        if (StringUtils.isBlank(commentDto.getId())){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"主键不能为空");
        }
        CommentEntity commentEntity = commentMapper.selectById(commentDto.getId());
        if (commentEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"数据不存在");
        }

        //属性赋值
        EntityUtil.addModifyInfo(commentEntity);
        commentEntity.setIsDeleted(Constant.ISDELETED_TRUE);

        commentMapper.updateById(commentEntity);
    }

    @Override
    public Result queryComment(CommentDto commentDto) {
        PageHelper.startPage(commentDto.getCurrentPage(),commentDto.getPageSize());
        List<CommentVo> commentVos = commentMapper.queryComment(commentDto);
        PageInfo<CommentVo> commentVoPageInfo = new PageInfo<>(commentVos);
        for (CommentVo commentVo : commentVos){
             //查询子评论
            List<CommentVo> applyCommentVos = commentMapper.queryApplyComment(commentVo);
            //设置子评论回复的父属性
            for (CommentVo commentVoLocal : applyCommentVos){
                CommentVo applyUserComment  = commentMapper.queryApplyUserComment(commentVoLocal);
                commentVoLocal.setApplyParentName(applyUserComment.getApplyParentName());
            }
            commentVo.setReplyComments(applyCommentVos);
        }
        return Result.success().data("data",new ResponsePageDto<>(commentVos,commentVoPageInfo.getTotal(),commentVoPageInfo.getPageSize(),commentVoPageInfo.getPageNum()));
    }

    @Override
    public Result queryAllComment(CommentDto commentDto) {
        PageHelper.startPage(commentDto.getCurrentPage(),commentDto.getPageSize());
        PageInfo<CommentVo> commentVoPageInfo = new PageInfo<>(commentMapper.queryAllComment(commentDto));
        return Result.success().data("data",new ResponsePageDto<>(commentVoPageInfo.getList(),commentVoPageInfo.getTotal(),commentVoPageInfo.getPageSize(),commentVoPageInfo.getPageNum()));
    }
}
