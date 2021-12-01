package com.education.service.serviceImpl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.entity.system.PermissionEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.system.PermissionMapper;
import com.education.service.system.PermissionService;
import com.education.vo.PermissionVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

//    @Autowired
//    private RolePermissionService rolePermissionService;

//    @Autowired
//    private UserService userService;
//

    @Override
    public void save(PermissionEntity permissionEntity) {
        //校验数据重复
        QueryWrapper<PermissionEntity> permissionEntityQueryWrapper = new QueryWrapper<>();
        permissionEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("type", permissionEntity.getType())
                .eq("name", permissionEntity.getName());
        PermissionEntity permissionEntityLocalRepart = permissionMapper.selectOne(permissionEntityQueryWrapper);
        if (permissionEntityLocalRepart != null) {
                throw new EducationException(ResultCode.FAILER_CODE.getCode(), "菜单名称重复");
        }
        //校验数据重复
        QueryWrapper<PermissionEntity> permissionPathEntityQueryWrapper = new QueryWrapper<>();
        permissionPathEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("type", permissionEntity.getType())
                .eq("path",permissionEntity.getPath());
        PermissionEntity permissionPathEntityLocalRepart = permissionMapper.selectOne(permissionEntityQueryWrapper);
        if (permissionEntityLocalRepart != null) {
                throw new EducationException(ResultCode.FAILER_CODE.getCode(),"访问路径重复");
        }
        permissionMapper.insert(permissionEntity);
    }

    @Override
    public void updateById(PermissionEntity permissionEntity) {
        if (StringUtils.isBlank(permissionEntity.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不能为空");
        }
        //校验数据重复
        QueryWrapper<PermissionEntity> permissionEntityQueryWrapper = new QueryWrapper<>();
        permissionEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("type", permissionEntity.getType())
                .eq("name", permissionEntity.getName());
        PermissionEntity permissionEntityLocalRepart = permissionMapper.selectOne(permissionEntityQueryWrapper);
        if (permissionEntityLocalRepart != null) {
            if (!permissionEntity.getId().equals(permissionEntityLocalRepart.getId())) {
                throw new EducationException(ResultCode.FAILER_CODE.getCode(), "菜单名称重复");
            }
        }
        //校验数据重复
        QueryWrapper<PermissionEntity> permissionPathEntityQueryWrapper = new QueryWrapper<>();
        permissionPathEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("type", permissionEntity.getType())
                .eq("path",permissionEntity.getPath());
        PermissionEntity permissionPathEntityLocalRepart = permissionMapper.selectOne(permissionEntityQueryWrapper);
        if (permissionEntityLocalRepart != null) {
                if (!permissionEntity.getId().equals(permissionPathEntityLocalRepart.getId())){
                    throw new EducationException(ResultCode.FAILER_CODE.getCode(),"访问路径重复");
                }
        }
        PermissionEntity permissionEntityLocal = permissionMapper.selectById(permissionEntity.getId());
        BeanUtils.copyProperties(permissionEntity, permissionEntityLocal);

        permissionMapper.updateById(permissionEntityLocal);
    }

    @Override
    public void saveButton(PermissionEntity permissionEntity) {
        if (StringUtils.isBlank(permissionEntity.getName())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "功能名称重复");
        }
        if (StringUtils.isBlank(permissionEntity.getPermissionValue())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "功能权限值为空");
        }
        //校验数据重复
        QueryWrapper<PermissionEntity> permissionEntityQueryWrapper = new QueryWrapper<>();
        permissionEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("permission_value", permissionEntity.getPermissionValue())
                .eq("type", permissionEntity.getType());
        PermissionEntity permissionEntityLocal = permissionMapper.selectOne(permissionEntityQueryWrapper);
        if (permissionEntityLocal != null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "功能权限值已存在");
        }
        permissionMapper.insert(permissionEntity);
    }

    @Override
    public void updateButton(PermissionEntity permissionEntity) {
        if (StringUtils.isBlank(permissionEntity.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不能为空");
        }
        //校验数据重复
        QueryWrapper<PermissionEntity> permissionEntityQueryWrapper = new QueryWrapper<>();
        permissionEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("permission_value", permissionEntity.getPermissionValue())
                .eq("type", permissionEntity.getType());
        PermissionEntity permissionEntityLocalRepart = permissionMapper.selectOne(permissionEntityQueryWrapper);
        if (permissionEntityLocalRepart != null) {
            if (!permissionEntity.getId().equals(permissionEntityLocalRepart.getId())) {
                throw new EducationException(ResultCode.FAILER_CODE.getCode(), "功能权限值已存在");
            }
        }
        PermissionEntity permissionEntityLocal = permissionMapper.selectById(permissionEntity.getId());
        permissionEntityLocal.setName(permissionEntity.getName());
        permissionEntityLocal.setPermissionValue(permissionEntity.getPermissionValue());
        permissionEntityLocal.setPath(permissionEntity.getPath());
        permissionEntityLocal.setComponent(permissionEntity.getComponent());

        permissionMapper.updateById(permissionEntityLocal);
    }

    //    //获取全部菜单
//    @Override
//    public List<PermissionEntity> queryAllMenu() {
//
//        QueryWrapper<PermissionEntity> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("id");
//        List<PermissionEntity> permissionList = baseMapper.selectList(wrapper);
//
//        List<PermissionEntity> result = bulid(permissionList);
//
//        return result;
//    }

    //    //根据角色获取菜单
//    @Override
//    public List<Permission> selectAllMenu(String roleId) {
//        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));
//
//        //根据角色id获取角色权限
//        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
//        //转换给角色id与角色权限对应Map对象
////        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
////        allPermissionList.forEach(permission -> {
////            if(permissionIdList.contains(permission.getId())) {
////                permission.setSelect(true);
////            } else {
////                permission.setSelect(false);
////            }
////        });
//        for (int i = 0; i < allPermissionList.size(); i++) {
//            Permission permission = allPermissionList.get(i);
//            for (int m = 0; m < rolePermissionList.size(); m++) {
//                RolePermission rolePermission = rolePermissionList.get(m);
//                if(rolePermission.getPermissionId().equals(permission.getId())) {
//                    permission.setSelect(true);
//                }
//            }
//        }
//
//
//        List<Permission> permissionList = bulid(allPermissionList);
//        return permissionList;
//    }
//
//    //给角色分配权限
//    @Override
//    public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {
//
//        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));
//
//
//
//        List<RolePermission> rolePermissionList = new ArrayList<>();
//        for(String permissionId : permissionIds) {
//            if(StringUtils.isEmpty(permissionId)) continue;
//
//            RolePermission rolePermission = new RolePermission();
//            rolePermission.setRoleId(roleId);
//            rolePermission.setPermissionId(permissionId);
//            rolePermissionList.add(rolePermission);
//        }
//        rolePermissionService.saveBatch(rolePermissionList);
//    }
//
//    //递归删除菜单
//    @Override
//    public void removeChildById(String id) {
//        List<String> idList = new ArrayList<>();
//        this.selectChildListById(id, idList);
//
//        idList.add(id);
//        baseMapper.deleteBatchIds(idList);
//    }
//
//    //根据用户id获取用户菜单
//    @Override
//    public List<String> selectPermissionValueByUserId(String id) {
//
//        List<String> selectPermissionValueList = null;
//        if(this.isSysAdmin(id)) {
//            //如果是系统管理员，获取所有权限
//            selectPermissionValueList = baseMapper.selectAllPermissionValue();
//        } else {
//            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
//        }
//        return selectPermissionValueList;
//    }
//
//    @Override
//    public List<JSONObject> selectPermissionByUserId(String userId) {
//        List<Permission> selectPermissionList = null;
//        if(this.isSysAdmin(userId)) {
//            //如果是超级管理员，获取所有菜单
//            selectPermissionList = baseMapper.selectList(null);
//        } else {
//            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
//        }
//
//        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
//        List<JSONObject> result = MemuHelper.bulid(permissionList);
//        return result;
//    }
//
//    /**
//     * 判断用户是否系统管理员
//     * @param userId
//     * @return
//     */
//    private boolean isSysAdmin(String userId) {
//        User user = userService.getById(userId);
//
//        if(null != user && "admin".equals(user.getUsername())) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     *	递归获取子节点
//     * @param id
//     * @param idList
//     */
//    private void selectChildListById(String id, List<String> idList) {
//        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
//        childList.stream().forEach(item -> {
//            idList.add(item.getId());
//            this.selectChildListById(item.getId(), idList);
//        });
//    }
//
//    /**
//     * 使用递归方法建菜单
//     * @param treeNodes
//     * @return
//     */
//    private static List<Permission> bulid(List<Permission> treeNodes) {
//        List<Permission> trees = new ArrayList<>();
//        for (Permission treeNode : treeNodes) {
//            if ("0".equals(treeNode.getPid())) {
//                treeNode.setLevel(1);
//                trees.add(findChildren(treeNode,treeNodes));
//            }
//        }
//        return trees;
//    }
//
//    /**
//     * 递归查找子节点
//     * @param treeNodes
//     * @return
//     */
//    private static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
//        treeNode.setChildren(new ArrayList<Permission>());
//
//        for (Permission it : treeNodes) {
//            if(treeNode.getId().equals(it.getPid())) {
//                int level = treeNode.getLevel() + 1;
//                it.setLevel(level);
//                if (treeNode.getChildren() == null) {
//                    treeNode.setChildren(new ArrayList<>());
//                }
//                treeNode.getChildren().add(findChildren(it,treeNodes));
//            }
//        }
//        return treeNode;
//    }
//
//
    //========================递归查询所有菜单================================================
    //获取全部菜单
    @Override
    public List<PermissionVo> queryAllMenu(PermissionEntity permissionEntity) {
        //1 查询菜单表所有数据
        List<PermissionVo> permissionList = permissionMapper.queryAllMenu(permissionEntity);

        //2 把查询所有菜单list集合按照要求进行封装
        List<PermissionVo> resultList = bulidPermission(permissionList);
        return resultList;
    }

    //把返回所有菜单list集合进行封装的方法
    public static List<PermissionVo> bulidPermission(List<PermissionVo> permissionList) {

        //创建list集合，用于数据最终封装
        List<PermissionVo> finalNode = new ArrayList<>();
        //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for (PermissionVo permissionNode : permissionList) {
            //得到顶层菜单 pid=0菜单
            if ("0".equals(permissionNode.getPid())) {
                //设置顶层菜单的level是1
                permissionNode.setLevel(1);
                //根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(permissionNode, permissionList));
            }
        }
        return finalNode;
    }

    private static PermissionVo selectChildren(PermissionVo permissionNode, List<PermissionVo> permissionList) {
        //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<PermissionVo>());

        //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for (PermissionVo it : permissionList) {
            //判断 id和pid值是否相同
            if (permissionNode.getId().equals(it.getPid())) {
                //把父菜单的level值+1
                int level = permissionNode.getLevel() + 1;
                it.setLevel(level);
                //如果children为空，进行初始化操作
                if (permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<PermissionVo>());
                }
                //把查询出来的子菜单放到父菜单里面
                permissionNode.getChildren().add(selectChildren(it, permissionList));
            }
        }
        return permissionNode;
    }
//
    //============递归删除菜单==================================
    @Override
    public void removeChildById(PermissionEntity permissionEntity) {
        //校验数据
        if (StringUtils.isBlank(permissionEntity.getId())){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"主键不存在");
        }
        PermissionEntity permissionEntityLocal = permissionMapper.selectById(permissionEntity.getId());
        if (permissionEntityLocal == null){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"此数据不存在");
        }
        //1 创建list集合，用于封装所有删除菜单id值
        List<String> idList = new ArrayList<>();
        //2 向idList集合设置删除菜单id
        this.selectPermissionChildById(permissionEntity.getId(),idList);
        //把当前id封装到list里面
        idList.add(permissionEntity.getId());
//        baseMapper.deleteBatchIds(idList);
        for (String id : idList) {
            PermissionEntity permissionEntityUpdate = new PermissionEntity();
            permissionEntityUpdate.setId(id);
            permissionEntityUpdate.setIsDeleted(1);

            permissionMapper.updateById(permissionEntityUpdate);
        }
    }

    //2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    private void selectPermissionChildById(String id, List<String> idList) {
        //查询菜单里面子菜单id
        QueryWrapper<PermissionEntity>  wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
//        List<PermissionEntity> childIdList = baseMapper.selectList(wrapper);
        List<PermissionEntity> childIdList = permissionMapper.selectList(wrapper);
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.stream().forEach(item -> {
            //封装idList里面
            idList.add(item.getId());
            //递归查询
            this.selectPermissionChildById(item.getId(),idList);
        });
    }
//
//    //=========================给角色分配菜单=======================
//    @Override
//    public void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionIds) {
//        //roleId角色id
//        //permissionId菜单id 数组形式
//        //1 创建list集合，用于封装添加数据
//        List<RolePermission> rolePermissionList = new ArrayList<>();
//        //遍历所有菜单数组
//        for(String perId : permissionIds) {
//            //RolePermission对象
//            RolePermission rolePermission = new RolePermission();
//            rolePermission.setRoleId(roleId);
//            rolePermission.setPermissionId(perId);
//            //封装到list集合
//            rolePermissionList.add(rolePermission);
//        }
//        //添加到角色菜单关系表
//        rolePermissionService.saveBatch(rolePermissionList);
//    }
}
