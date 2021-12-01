package com.education.service.system;

import com.education.common.Result;
import com.education.entity.system.PermissionEntity;
import com.education.vo.PermissionVo;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface PermissionService{

    //获取全部菜单
    List<PermissionVo> queryAllMenu(PermissionEntity permissionEntity);

    void save(PermissionEntity permissionEntity);

    void updateById(PermissionEntity permissionEntity);

    void saveButton(PermissionEntity permissionEntity);

    void updateButton(PermissionEntity permissionEntity);

//    //获取全部菜单
//    List<PermissionEntity> queryAllMenu();

//    //根据角色获取菜单
//    List<Permission> selectAllMenu(String roleId);
//
//    //给角色分配权限
//    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);
//
//    //递归删除菜单
//    void removeChildById(String id);
//
//    //根据用户id获取用户菜单
//    List<String> selectPermissionValueByUserId(String id);
//
//    List<JSONObject> selectPermissionByUserId(String id);
//

//
    //递归删除菜单
    void removeChildById(PermissionEntity permissionEntity);
//
//    //给角色分配权限
//    void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionId);
}
