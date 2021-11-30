package com.education.controller.system;


import com.baomidou.mybatisplus.extension.api.R;
import com.education.common.Result;
import com.education.entity.system.PermissionEntity;
import com.education.mapper.system.PermissionMapper;
import com.education.service.system.PermissionService;
import com.education.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.acl.Group;
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
     * @description 查询所有菜单
     * @return com.education.common.Result
     * @author 橘白
     * @date 2021/11/28 12:52
     */

    @PostMapping("/queryAllMenu")
    public Result indexAllPermission() {
        List<PermissionVo> list =  permissionService.queryAllMenu();
        return Result.success().data("children",list);
    }

//    @DeleteMapping("remove/{id}")
//    public R remove(@PathVariable String id) {
//        permissionService.removeChildByIdGuli(id);
//        return R.ok();
//    }
//
//    @ApiOperation(value = "给角色分配权限")
//    @PostMapping("/doAssign")
//    public R doAssign(String roleId,String[] permissionId) {
//        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
//        return R.ok();
//    }
//
//    @ApiOperation(value = "根据角色获取菜单")
//    @GetMapping("toAssign/{roleId}")
//    public R toAssign(@PathVariable String roleId) {
//        List<Permission> list = permissionService.selectAllMenu(roleId);
//        return R.ok().data("children", list);
//    }
//
//
    /**
     * @description  新增菜单
     * @param permissionEntity
     * @return com.education.common.Result
     * @author 橘白
     * @date 2021/11/28 18:18
     */
    @PostMapping("save")
    public Result save(@Validated @RequestBody PermissionEntity permissionEntity) {
        permissionService.save(permissionEntity);
        return Result.success();
    }

    /**
     * @description  修改菜单
     * @param permissionEntity
     * @return com.education.common.Result
     * @author 橘白
     * @date 2021/11/28 19:50
     */
    @PostMapping("update")
    public Result updateById(@RequestBody PermissionEntity permissionEntity) {
        permissionService.updateById(permissionEntity);
        return Result.success();
    }

    /**
     * @description  新增按钮权限
     * @param permissionEntity
     * @return com.education.common.Result
     * @author 橘白
     * @date 2021/11/28 18:18
     */
    @PostMapping("saveButton")
    public Result saveButton(@RequestBody PermissionEntity permissionEntity) {
        permissionService.saveButton(permissionEntity);
        return Result.success();
    }

    /**
     * @description  修改按钮权限
     * @param permissionEntity
     * @return com.education.common.Result
     * @author 橘白
     * @date 2021/11/28 19:50
     */
    @PostMapping("updateButton")
    public Result updateButton(@RequestBody PermissionEntity permissionEntity) {
        permissionService.updateButton(permissionEntity);
        return Result.success();
    }
}

