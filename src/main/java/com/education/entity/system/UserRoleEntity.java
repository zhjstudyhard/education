package com.education.entity.system;


import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

/**
 * @author zhj
 */
@TableName(value = "b_userrole")
public class UserRoleEntity extends BaseEntity {

    private String roleId;

    private String userId;

    public UserRoleEntity() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}