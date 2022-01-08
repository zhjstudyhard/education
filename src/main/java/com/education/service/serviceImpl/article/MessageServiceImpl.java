package com.education.service.serviceImpl.article;

import com.education.common.Result;
import com.education.entity.system.UserEntity;
import com.education.mapper.article.MessageMapper;
import com.education.service.article.MessageService;
import com.education.util.ShiroEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return Result.success().data("data",count);
    }
}
