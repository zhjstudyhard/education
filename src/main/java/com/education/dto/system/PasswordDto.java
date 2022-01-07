package com.education.dto.system;

import javax.validation.constraints.NotBlank;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-07-18-46
 */
public class PasswordDto {

    /**
     * 主键id
     */
    private String id;

    /**
     * 旧密码
     */
    @NotBlank(message = "密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "密码不能为空")
    private String newPassword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
