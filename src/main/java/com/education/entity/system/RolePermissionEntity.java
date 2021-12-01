package com.education.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;


/**
 * <p>
 * 角色权限
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@TableName(value = "b_rolepermission")
public class RolePermissionEntity extends BaseEntity {

    private String roleId;

    private String permissionId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
