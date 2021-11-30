package com.education.entity.system;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * <p>
 * 权限
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@TableName(value = "b_permission")
public class PermissionEntity extends BaseEntity implements Serializable {

    /**
     * 所属上级
     */
    private String pid;
    /**
     * 名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 类型(1:菜单,2:按钮)
     */
    private Integer type;

    /**
     * 权限值
     */
    private String permissionValue;
    /**
     * 访问路径
     */
    @NotBlank(message = "访问路径不能为空")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String path;
    /**
     * 组件路径
     */
    @NotBlank(message = "组件路径不能为空")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String component;
    /**
     * 图标
     */
    private String icon;


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}
