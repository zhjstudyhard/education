package com.education.service.system;

import com.education.common.Result;
import com.education.dto.system.PasswordDto;
import com.education.dto.system.RoleDto;
import com.education.dto.system.UserDto;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-23-17-24
 */
public interface UserService {

    Result queryUserAllPage(UserDto userDto);

    void deleteUser(UserDto userDto);

    void editUser(UserDto userDto);

    void addUser(UserDto userDto);

    void updatePassword(PasswordDto passwordDto) throws Exception;

    void registeUser(UserDto userDto) throws Exception;

    void updateAvatar(UserDto userDto);
}
