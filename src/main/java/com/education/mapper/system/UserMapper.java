package com.education.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.dto.system.UserDto;
import com.education.entity.system.UserEntity;
import com.education.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 86181
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    List<UserVo> queryUserAllPage(UserDto userDto);
}