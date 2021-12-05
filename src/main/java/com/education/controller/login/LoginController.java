package com.education.controller.login;

import com.education.common.Result;
import com.education.dto.system.UserDto;
import com.education.entity.system.PermissionEntity;
import com.education.service.login.UserLoginService;
import com.education.service.system.UserService;
import com.education.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-03-11-39
 */
@RestController
@RequestMapping("/api/userLogin")
@CrossOrigin
public class LoginController {
    @Autowired
    private UserLoginService userLoginService;

    /**
     * @param userDto
     * @return com.education.common.Result
     * @description 用户登录
     * @author 橘白
     * @date 2021/12/3 11:46
     */

    @PostMapping("/login")
    public Result userLogin(@RequestBody UserDto userDto, HttpServletResponse response) {
        return userLoginService.login(userDto,response);
    }

    @PostMapping("/logout")
    public Result userLogOut() {
        userLoginService.loginOut();
        return Result.success();
    }
}
