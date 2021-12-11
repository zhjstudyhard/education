package com.education.util;


import com.education.entity.base.BaseEntity;
import com.education.entity.system.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.Date;


/**
 * @description  实体类工具
 * @author 橘白
 * @date 2021/12/7 22:57
 */

public class EntityUtil {

    
    /**
     * 添加实体类创建信息
     *
     * @param object 实体类
     */
    public static void addCreateInfo(Object object) {
        Subject subject = SecurityUtils.getSubject();
        UserEntity userEntity = (UserEntity) subject.getPrincipal();

        if (object instanceof BaseEntity) {
            ((BaseEntity) object).setGmtCreate(new Date());
            ((BaseEntity) object).setNameCreate(userEntity.getRealName());
            ((BaseEntity) object).setGmtModified(null);
            ((BaseEntity) object).setNameModified(null);
        }
    }
    
    /**
     * 添加实体类创建信息
     *
     * @param object 实体类
     */
    public static void addModifyInfo(Object object) {
        Subject subject = SecurityUtils.getSubject();
        UserEntity userEntity = (UserEntity) subject.getPrincipal();

        if (object instanceof BaseEntity) {
            ((BaseEntity) object).setGmtModified(new Date());
            ((BaseEntity) object).setNameModified(userEntity.getRealName());
        }
    }
}
