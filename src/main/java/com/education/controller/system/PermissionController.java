package com.education.controller.system;


import com.education.common.Result;
import com.education.dto.system.RolePermissionDto;
import com.education.entity.system.PermissionEntity;
import com.education.service.system.PermissionService;
import com.education.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/api/permission")
@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * @return com.education.common.Result
     * @description 查询所有菜单
     * @author 橘白
     * @date 2021/11/28 12:52
     */

    @PostMapping("/queryAllMenu")
    public Result indexAllPermission(@RequestBody PermissionEntity permissionEntity) {
        List<PermissionVo> list = permissionService.queryAllMenu(permissionEntity);
        return Result.success().data("children", list);
    }

    /*
     * @description 删除权限
     * @param null
     * @author 橘白
     * @date 2021/12/1 21:41
     */

    @PostMapping("/removePermission")
    public Result remove(@RequestBody PermissionEntity permissionEntity) {
        permissionService.removeChildById(permissionEntity);
        return Result.success();
    }


    /**
     * @param rolePermissionDto
     * @return com.education.common.Result
     * @description 给角色分配权限
     * @author 橘白
     * @date 2021/12/2 11:54
     */

    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody RolePermissionDto rolePermissionDto) {
        permissionService.saveRolePermission(rolePermissionDto);
        return Result.success();
    }

    /**
     * @param rolePermissionDto
     * @return com.education.common.Result
     * @description 创建角色分配菜单
     * @author 橘白
     * @date 2021/12/2 11:50
     */

    @PostMapping("/toAssign")
    public Result toAssign(@RequestBody RolePermissionDto rolePermissionDto) {
        List<PermissionVo> list = permissionService.selectAllMenu(rolePermissionDto.getRoleId());
        return Result.success().data("children", list);
    }

    /**
     * @param permissionEntity
     * @return com.education.common.Result
     * @description 新增菜单
     * @author 橘白
     * @date 2021/11/28 18:18
     */
    @PostMapping("save")
    public Result save(@Validated @RequestBody PermissionEntity permissionEntity) {
        permissionService.save(permissionEntity);
        return Result.success();
    }

    /**
     * @param permissionEntity
     * @return com.education.common.Result
     * @description 修改菜单
     * @author 橘白
     * @date 2021/11/28 19:50
     */
    @PostMapping("update")
    public Result updateById(@RequestBody PermissionEntity permissionEntity) {
        permissionService.updateById(permissionEntity);
        return Result.success();
    }

    /**
     * @param permissionEntity
     * @return com.education.common.Result
     * @description 新增按钮权限
     * @author 橘白
     * @date 2021/11/28 18:18
     */
    @PostMapping("saveButton")
    public Result saveButton(@RequestBody PermissionEntity permissionEntity) {
        permissionService.saveButton(permissionEntity);
        return Result.success();
    }

    /**
     * @param permissionEntity
     * @return com.education.common.Result
     * @description 修改按钮权限
     * @author 橘白
     * @date 2021/11/28 19:50
     */
    @PostMapping("updateButton")
    public Result updateButton(@RequestBody PermissionEntity permissionEntity) {
        permissionService.updateButton(permissionEntity);
        return Result.success();
    }
}

