package com.education.service.serviceImpl.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.dto.article.MessageDto;
import com.education.dto.base.ResponsePageDto;
import com.education.entity.article.MessageEntity;
import com.education.entity.system.UserEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.article.MessageMapper;
import com.education.service.article.MessageService;
import com.education.util.EntityUtil;
import com.education.util.ShiroEntityUtil;
import com.education.util.socket.WebSocketUtil;
import com.education.vo.MessageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-08-17-08
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;


    @Override
    public Result queryMessageCount() {
        UserEntity shiroEntity = ShiroEntityUtil.getShiroEntity();
        Integer count = messageMapper.queryMessageCount(shiroEntity.getId());
        return Result.success().data("data", count);
    }

    @Override
    public Result queryMessagePage(MessageDto messageDto) throws Exception {
        UserEntity userEntity = ShiroEntityUtil.getShiroEntity();
        if (messageDto.getIsAdmin() != null) {
            if (messageDto.getIsAdmin().equals(0)) {
                messageDto.setUserId(userEntity.getId());
            } else {
                Boolean flag = ShiroEntityUtil.checkPermission();
                if (flag) {
                    messageDto.setUserId(userEntity.getId());
                }
            }
        }
        //未读状态消息的id集合
        List<String> ids = new ArrayList<>();
        PageHelper.startPage(messageDto.getCurrentPage(), messageDto.getPageSize());
        //发送人和接收人名称查询（连接了两次用户表，想不出来其他方法）
        List<MessageVo> messageVos = messageMapper.queryMessagePage(messageDto);
        //查询父评论数据
        Set<String> parentCommentIds = messageVos.stream().filter(o -> (o.getParentCommentId() != null && !o.getParentCommentId().equals(Constant.NUMBER_NEGATIVE_ONE))).map(o -> o.getParentCommentId()).collect(Collectors.toSet());
        if (parentCommentIds != null && parentCommentIds.size() > 0) {
            Map<String, MessageVo> messageVoMap = messageMapper.queryParentMessage(parentCommentIds);
            for (MessageVo messageVo : messageVos) {

                if (StringUtils.isNotBlank(messageVo.getParentCommentId())) {
                    MessageVo messageVoLocal = messageVoMap.get(messageVo.getParentCommentId());
                    if (messageVoLocal != null) {
                        messageVo.setParentFromUserName(messageVoLocal.getParentFromUserName());
                        messageVo.setParentToUserName(messageVoLocal.getParentToUserName());
                        messageVo.setParentContent(messageVoLocal.getParentContent());
                        messageVo.setParentFromUserId(messageVoLocal.getParentFromUserId());
                        messageVo.setParentToUserId(messageVoLocal.getParentToUserId());
                    }
                }
            }
        }
        PageInfo<MessageVo> messageVoPageInfo = new PageInfo<>(messageVos);
        //更改消息阅读状态
        for (MessageVo messageVo : messageVos) {
            if (messageVo.getStatus().equals(Constant.NUMBER_ZERO)) {
                ids.add(messageVo.getId());
            }
        }
        if (ids.size() > 0) {
            messageMapper.updateBatchMessageStatus(ids);
            //执行完成后，通知接收消息人
            WebSocketUtil.sendInfoByUserId(messageMapper.queryMessageCount(userEntity.getId()).toString(), userEntity.getId());
        }

        return Result.success().data("data", new ResponsePageDto<>(messageVoPageInfo.getList(), messageVoPageInfo.getTotal(), messageVoPageInfo.getPageSize(), messageVoPageInfo.getPageNum()));
    }


    @Override
    public void delMessagePage(MessageDto messageDto) {
        if (StringUtils.isBlank(messageDto.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不能为空");
        }
        MessageEntity messageEntity = messageMapper.selectById(messageDto.getId());
        if (messageEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }

        //更改数据
        messageEntity.setIsDeleted(Constant.ISDELETED_TRUE);
        EntityUtil.addModifyInfo(messageEntity);
        messageMapper.updateById(messageEntity);
    }
}
