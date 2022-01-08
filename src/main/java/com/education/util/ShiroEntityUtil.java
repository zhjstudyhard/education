package com.education.util;

import com.education.common.ResultCode;
import com.education.entity.system.UserEntity;
import com.education.exceptionhandler.EducationException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

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

    public static Boolean checkPermission(){
        Subject subject = SecurityUtils.getSubject();
        //判断角色(太麻烦，还不如直接查数据库进行判断)
        boolean[] booleans = subject.hasRoles(Arrays.asList("admin", "manager"));
        Boolean flag = true;
        for (int i = 0; i < booleans.length; i++){
            if (booleans[i] == true){
                flag = false;
                break;
            }
        }
        return flag;
    }
}
