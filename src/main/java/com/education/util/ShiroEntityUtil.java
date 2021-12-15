package com.education.util;

import com.education.common.ResultCode;
import com.education.entity.system.UserEntity;
import com.education.exceptionhandler.EducationException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-14-09-37
 */
public class ShiroEntityUtil {
    public static UserEntity getShiroEntity(){
        Subject subject = SecurityUtils.getSubject();
        UserEntity userEntity = (UserEntity) subject.getPrincipal();
        if (userEntity == null){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"登陆失效或用户未登录");
        }
        return userEntity;
    }
}
