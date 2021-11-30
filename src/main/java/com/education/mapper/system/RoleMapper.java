package com.education.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.dto.system.RoleDto;
import com.education.entity.system.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 86181
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {

    List<RoleEntity> queryRoleAllPage(RoleDto roleDto);

    List<RoleEntity> queryRoleAll(RoleDto roleDto);
}