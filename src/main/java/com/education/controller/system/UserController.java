package com.education.controller.system;

import com.education.common.Result;
import com.education.dto.system.PasswordDto;
import com.education.dto.system.UserDto;
import com.education.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-23-17-23
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @param userDto
     * @return java.lang.Object
     * @description 分页查询用户
     * @author 橘白
     * @date 2021/11/23 17:36
     */
    @PostMapping("/queryUserAllPage")
    public Object queryUserAllPage(@RequestBody UserDto userDto) {
        return userService.queryUserAllPage(userDto);
    }

    /**
     * @description  删除用户
     * @param userDto
     * @return java.lang.Object
     * @author 橘白
     * @date 2021/11/22 21:29
     */

    @PostMapping("/deleteUser")
    public Object deleteUser(@RequestBody UserDto userDto){
        userService.deleteUser(userDto);
        return Result.success();
    }

    /**
     * @description  更新用户
     * @param userDto
     * @return java.lang.Object
     * @author 橘白
     * @date 2021/11/22 21:47
     */

    @PostMapping("/editUser")
    public Object editUser(@Validated @RequestBody UserDto userDto){
        userService.editUser(userDto);
        return Result.success();
    }

    /**
     * @description  添加用户名
     * @param userDto
     * @return java.lang.Object
     * @author 橘白
     * @date 2021/11/23 16:21
     */
    @PostMapping("/addUser")
    public Object addUser(@Validated @RequestBody UserDto userDto){
        userService.addUser(userDto);
        return Result.success();
    }

    @PostMapping("/updatePassword")
    public Object updatePassword(@Validated @RequestBody PasswordDto passwordDto) throws Exception {
        userService.updatePassword(passwordDto);
        return Result.success();
    }
}
