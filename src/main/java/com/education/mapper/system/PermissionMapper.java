package com.education.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.system.PermissionEntity;
import com.education.vo.PermissionVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-28-14-26
 */
@Mapper
@Repository
public interface PermissionMapper extends BaseMapper<PermissionEntity> {
    List<PermissionVo> queryAllMenu(PermissionEntity permissionEntity);

    List<PermissionVo> selectAllPermission();

    List<PermissionVo> selectPermissionByUserId(String id);
}
