package com.education.service.serviceImpl.article;

import com.education.common.Result;
import com.education.constant.Constant;
import com.education.dto.article.MessageDto;
import com.education.dto.base.ResponsePageDto;
import com.education.entity.system.UserEntity;
import com.education.mapper.article.MessageMapper;
import com.education.service.article.MessageService;
import com.education.util.ShiroEntityUtil;
import com.education.vo.MessageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Result queryMessagePage(MessageDto messageDto) {
        UserEntity userEntity = ShiroEntityUtil.getShiroEntity();
        messageDto.setUserId(userEntity.getId());

        PageHelper.startPage(messageDto.getCurrentPage(), messageDto.getPageSize());
        //发送人和接收人名称查询（连接了两次用户表，想不出来其他方法）
        List<MessageVo> messageVos = messageMapper.queryMessagePage(messageDto);
        //查询父评论数据
        Set<String> parentCommentIds = messageVos.stream().filter(o -> (o.getParentCommentId() != null && o.getParentCommentId() != Constant.NUMBER_NEGATIVE_ONE)).map(o -> o.getParentCommentId()).collect(Collectors.toSet());
        if (parentCommentIds != null && parentCommentIds.size() > 0) {
            Map<String, MessageVo> messageVoMap = messageMapper.queryParentMessage(parentCommentIds);
            for (MessageVo messageVo : messageVos) {
                if (StringUtils.isNotBlank(messageVo.getParentCommentId())) {
                    MessageVo messageVoLocal = messageVoMap.get(messageVo.getParentCommentId());
                    if (messageVoLocal != null){
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
        return Result.success().data("data", new ResponsePageDto<>(messageVoPageInfo.getList(), messageVoPageInfo.getTotal(), messageVoPageInfo.getPageSize(), messageVoPageInfo.getPageNum()));
    }
}
