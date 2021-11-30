package com.education.controller.system;

import com.education.common.Result;
import com.education.dto.system.DictionaryDto;
import com.education.dto.system.RoleDto;
import com.education.entity.system.RoleEntity;
import com.education.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-23-17-23
 */
@RestController
@RequestMapping("/api/role")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * @param roleDto
     * @return java.lang.Object
     * @description 分页查询角色
     * @author 橘白
     * @date 2021/11/23 17:36
     */
    @PostMapping("/queryRoleAllPage")
    public Object queryRoleAllPage(@RequestBody RoleDto roleDto) {
        return roleService.queryRoleAllPage(roleDto);
    }

    /**
     * @return java.lang.Object
     * @description 查询所有角色
     * @author 橘白
     * @date 2021/11/23 17:36
     */
    @PostMapping("/queryRoleAll")
    public Object queryRoleAll(@RequestBody RoleDto roleDto) {
        return roleService.queryRoleAll(roleDto);
    }

    /**
     * @description  删除数据字典
     * @param roleDto
     * @return java.lang.Object
     * @author 橘白
     * @date 2021/11/22 21:29
     */

    @PostMapping("/deleteRole")
    public Object deleteRole(@RequestBody RoleDto roleDto){
        roleService.deleteRole(roleDto);
        return Result.success();
    }

    /**
     * @description  更新字典
     * @param roleDto
     * @return java.lang.Object
     * @author 橘白
     * @date 2021/11/22 21:47
     */

    @PostMapping("/editRole")
    public Object editRole(@Validated @RequestBody RoleDto roleDto){
        roleService.editRole(roleDto);
        return Result.success();
    }

    /**
     * @description  添加角色
     * @param roleDto
     * @return java.lang.Object
     * @author 橘白
     * @date 2021/11/23 16:21
     */

    @PostMapping("/addRole")
    public Object addRole(@Validated @RequestBody RoleDto roleDto){
        roleService.addRole(roleDto);
        return Result.success();
    }
}
