package com.education.service.login;

import com.education.common.Result;
import com.education.dto.system.UserDto;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-03-11-41
 */
public interface UserLoginService {
    Result login(UserDto userDto, HttpServletResponse response);

    void loginOut();
}
