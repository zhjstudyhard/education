package com.education.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.system.UserEntity;
import com.education.entity.system.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 86181
 */
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

    List<String> queryUserRoles(UserEntity userEntity);
}