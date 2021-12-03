package com.education.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.system.RolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-01-22-11
 */
@Mapper
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermissionEntity> {
}
