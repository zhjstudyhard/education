package com.education.vo;

import java.util.List;
/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-28-13-46
 */
public class PermissionVo {

    /**
     * 主键
     */
    private String id;

    /**
     * 父级id
     */
    private String pid;

    /**
     * 权限名称
     */
    private String name;

    /**
     *  "类型(1:菜单,2:按钮)"
     */
    private Integer type;

    /**
     *  "权限值"
     */
    private String permissionValue;

    /**
     *  "访问路径"
     */
    private String path;

    /**
     *  "组件路径"
     */
    private String component;

    /**
     *  "图案"
     */
    private String icon;

    /**
     *  层级
     */
    private Integer level;

    /**
     * 子菜单
     */
    private List<PermissionVo> children;

    /**
     * 是否被选中
     */
    private boolean isSelect;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<PermissionVo> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionVo> children) {
        this.children = children;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
