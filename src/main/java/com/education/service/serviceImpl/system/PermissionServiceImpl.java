package com.education.service.serviceImpl.system;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.dto.system.RolePermissionDto;
import com.education.entity.system.PermissionEntity;
import com.education.entity.system.RolePermissionEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.system.PermissionMapper;
import com.education.mapper.system.RolePermissionMapper;
import com.education.service.system.PermissionService;
import com.education.util.EntityUtil;
import com.education.vo.PermissionVo;
import com.sun.javafx.tk.PermissionHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 橘白
 * @description 菜单权限业务
 * @date 2021/12/5 15:06
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

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
                .eq("path", permissionEntity.getPath());
        PermissionEntity permissionPathEntityLocalRepart = permissionMapper.selectOne(permissionEntityQueryWrapper);
        if (permissionEntityLocalRepart != null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "访问路径重复");
        }
        //创建人信息
        EntityUtil.addCreateInfo(permissionEntity);
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
                .eq("path", permissionEntity.getPath());
        PermissionEntity permissionPathEntityLocalRepart = permissionMapper.selectOne(permissionEntityQueryWrapper);
        if (permissionEntityLocalRepart != null) {
            if (!permissionEntity.getId().equals(permissionPathEntityLocalRepart.getId())) {
                throw new EducationException(ResultCode.FAILER_CODE.getCode(), "访问路径重复");
            }
        }
        PermissionEntity permissionEntityLocal = permissionMapper.selectById(permissionEntity.getId());
        BeanUtils.copyProperties(permissionEntity, permissionEntityLocal);

        //更新人信息
        EntityUtil.addModifyInfo(permissionEntityLocal);
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
        //创建人信息
        EntityUtil.addCreateInfo(permissionEntityLocal);
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

        //更新人信息
        EntityUtil.addModifyInfo(permissionEntityLocal);
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

    //根据角色获取菜单
    @Override
    public List<PermissionVo> selectAllMenu(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "角色id为空");
        }
        List<PermissionVo> allPermissionList = permissionMapper.selectAllPermission();
//        List<PermissionEntity> allPermissionList = permissionMapper.selectList(new QueryWrapper<PermissionEntity>().eq("is_deleted",Constant.ISDELETED_FALSE).orderByAsc("CAST(id AS SIGNED)"));
        //根据角色id获取角色权限
        List<RolePermissionEntity> rolePermissionEntityList = rolePermissionMapper.selectList(new QueryWrapper<RolePermissionEntity>().eq("role_id", roleId).eq("is_deleted", Constant.ISDELETED_FALSE));
        //转换给角色id与角色权限对应Map对象
        List<String> permissionIdList = rolePermissionEntityList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
        allPermissionList.forEach(permissionVo -> {
            if (permissionIdList.contains(permissionVo.getId())) {
                permissionVo.setSelect(true);
            } else {
                permissionVo.setSelect(false);
            }
        });
//        for (int i = 0; i < allPermissionList.size(); i++) {
//            PermissionVo permissionVo = allPermissionList.get(i);
//            for (int m = 0; m < rolePermissionEntityList.size(); m++) {
//                RolePermissionEntity rolePermissionEntity = rolePermissionEntityList.get(m);
//                if(rolePermissionEntity.getPermissionId().equals(permissionVo.getId())) {
//                    permissionVo.setSelect(true);
//                }
//            }
//        }

        List<PermissionVo> permissionList = bulid(allPermissionList);
        return permissionList;
    }
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

    /**
     * 使用递归方法建菜单
     *
     * @param treeNodes
     * @return
     */
    private static List<PermissionVo> bulid(List<PermissionVo> treeNodes) {
        List<PermissionVo> trees = new ArrayList<>();
        for (PermissionVo treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private static PermissionVo findChildren(PermissionVo treeNode, List<PermissionVo> treeNodes) {
        treeNode.setChildren(new ArrayList<PermissionVo>());

        for (PermissionVo it : treeNodes) {
            if (treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }


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
                if (it.getType().equals(1)) {
                    it.setTypeName("页面");
                } else if (it.getType().equals(2)) {
                    it.setTypeName("按钮");
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
        if (StringUtils.isBlank(permissionEntity.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不存在");
        }
        PermissionEntity permissionEntityLocal = permissionMapper.selectById(permissionEntity.getId());
        if (permissionEntityLocal == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "此数据不存在");
        }
        //1 创建list集合，用于封装所有删除菜单id值
        List<String> idList = new ArrayList<>();
        //2 向idList集合设置删除菜单id
        this.selectPermissionChildById(permissionEntity.getId(), idList);
        //把当前id封装到list里面
        idList.add(permissionEntity.getId());
//        baseMapper.deleteBatchIds(idList);
        for (String id : idList) {
            PermissionEntity permissionEntityUpdate = new PermissionEntity();
            permissionEntityUpdate.setId(id);
            permissionEntityUpdate.setIsDeleted(1);
            //更新人信息
            EntityUtil.addModifyInfo(permissionEntityUpdate);
            permissionMapper.updateById(permissionEntityUpdate);
        }
    }

    //2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    private void selectPermissionChildById(String id, List<String> idList) {
        //查询菜单里面子菜单id
        QueryWrapper<PermissionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        wrapper.select("id");
//        List<PermissionEntity> childIdList = baseMapper.selectList(wrapper);
        List<PermissionEntity> childIdList = permissionMapper.selectList(wrapper);
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.stream().forEach(item -> {
            //封装idList里面
            idList.add(item.getId());
            //递归查询
            this.selectPermissionChildById(item.getId(), idList);
        });
    }

    //=========================给角色分配菜单=======================
    @Override
    public void saveRolePermission(RolePermissionDto rolePermissionDto) {
        //roleId角色id
        //permissionId菜单id String多个数据使用逗号隔开
        //删除原有的权限关联数据
        QueryWrapper<RolePermissionEntity> rolePermissionEntityQueryWrapper = new QueryWrapper<>();
        rolePermissionEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("role_id", rolePermissionDto.getRoleId());
        rolePermissionMapper.delete(rolePermissionEntityQueryWrapper);

        //添加分配的权限
        List<String> permissionIdsList = Arrays.asList(rolePermissionDto.getPermissionIds().split(","));
        if (permissionIdsList.size() > 0 && permissionIdsList != null) {
            for (String permissionId : permissionIdsList) {
                RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
                rolePermissionEntity.setRoleId(rolePermissionDto.getRoleId());
                rolePermissionEntity.setPermissionId(permissionId);
                //创建人信息
                EntityUtil.addCreateInfo(rolePermissionEntity);
                rolePermissionMapper.insert(rolePermissionEntity);
            }
        }
    }

    @Override
    public List<JSONObject> getMenu(String id) {
        List<PermissionVo> selectPermissionList = permissionMapper.selectPermissionByUserId(id);
        List<PermissionVo> resultList = bulidPermission(selectPermissionList);
        List<JSONObject> permissionList = bulidMenus(resultList);
        return permissionList;
    }

    /**
     * 构建菜单
     *
     * @param treeNodes
     * @return
     */
    public static List<JSONObject> bulidMenus(List<PermissionVo> treeNodes) {
        List<JSONObject> meuns = new ArrayList<>();
        if (treeNodes.size() == 1) {
            PermissionVo topNode = treeNodes.get(0);
            //1.左侧除去全部数据目录以外的菜单进行遍历（比如系统管理目录）
            List<PermissionVo> oneMeunList = topNode.getChildren();
            for (PermissionVo one : oneMeunList) {
                JSONObject oneMeun = new JSONObject();
                oneMeun.put("path", one.getPath());
                oneMeun.put("component", one.getComponent());
                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", "name_" + one.getId());
                oneMeun.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                oneMeun.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<PermissionVo> twoMeunList = one.getChildren();
                //2.遍历目录下的所有菜单加入到第二级目录的childen集合（比如系统管理下的所有菜单目录下的所有一级目录，就像系统管理目录）
                for (PermissionVo two : twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("path", two.getPath());
                    twoMeun.put("component", two.getComponent());
                    twoMeun.put("name", "name_" + two.getId());
                    twoMeun.put("hidden", false);

                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    twoMeun.put("meta", twoMeta);

                    children.add(twoMeun);

                    List<PermissionVo> threeMeunList = two.getChildren();
                    //遍历菜单下的按钮所对应的菜单加入到children集合
                    for (PermissionVo three : threeMeunList) {
                        if (StringUtils.isEmpty(three.getPath())) {
                            continue;
                        }

                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("path", three.getPath());
                        threeMeun.put("component", three.getComponent());
                        threeMeun.put("name", "name_" + three.getId());
                        threeMeun.put("hidden", true);

                        JSONObject threeMeta = new JSONObject();
                        threeMeta.put("title", three.getName());
                        threeMeun.put("meta", threeMeta);

                        children.add(threeMeun);
                    }
                }
                oneMeun.put("children", children);
                meuns.add(oneMeun);
            }
        }
        return meuns;
    }

}
