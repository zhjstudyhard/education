package com.education.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.system.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 86181
 */
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

}