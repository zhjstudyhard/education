package com.education.service.serviceImpl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.dto.base.ResponsePageDto;
import com.education.dto.system.DictionaryDto;
import com.education.dto.system.RoleDto;
import com.education.entity.system.DictionaryEntity;
import com.education.entity.system.RoleEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.system.RoleMapper;
import com.education.service.system.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-23-17-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Result queryRoleAllPage(RoleDto roleDto) {
        //使用pageHelper分页
        PageHelper.startPage(roleDto.getCurrentPage(), roleDto.getPageSize());
        PageInfo pageInfo = new PageInfo<>(roleMapper.queryRoleAllPage(roleDto));
        ResponsePageDto responsePageDto = new ResponsePageDto(pageInfo.getList(), pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getPageNum());
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", responsePageDto);
        return Result.success().data(map);
    }

    @Override
    public Result queryRoleAll(RoleDto roleDto) {
        List<RoleEntity> roleEntityList =  roleMapper.queryRoleAll(roleDto);
        return Result.success().data("data",roleEntityList);
    }

    @Override
    public void deleteRole(RoleDto roleDto) {
        if (StringUtils.isBlank(roleDto.getId())){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"主键不能为空");
        }
        RoleEntity roleEntity = roleMapper.selectById(roleDto.getId());
        if (roleEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"数据不存在");
        }
        roleEntity.setIsDeleted(1);
        roleMapper.updateById(roleEntity);
    }

    @Override
    public void editRole(RoleDto roleDto) {
        if (StringUtils.isBlank(roleDto.getId())){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"主键不能为空");
        }
        //校验角色编码是否重复
        QueryWrapper<RoleEntity> roleEntityQueryWrapper = new QueryWrapper<>();
        roleEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("role_code",roleDto.getRoleCode());
        RoleEntity roleEntity = roleMapper.selectOne(roleEntityQueryWrapper);
        if (roleEntity  != null) {
            if (!roleEntity.getId().equals(roleDto.getId())){
                throw new EducationException(ResultCode.FAILER_CODE.getCode(),"角色编码重复");
            }
        }
        RoleEntity roleEntityLocal = roleMapper.selectById(roleDto.getId());
        //更新角色
        roleEntityLocal.setRoleCode(roleDto.getRoleCode());
        roleEntityLocal.setRoleName(roleDto.getRoleName());
        roleMapper.updateById(roleEntityLocal);
    }

    @Override
    public void addRole(RoleDto roleDto) {
        //校验角色编码是否重复
        QueryWrapper<RoleEntity> roleEntityQueryWrapper = new QueryWrapper<>();
        roleEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("role_code",roleDto.getRoleCode());
        RoleEntity roleEntityLocal = roleMapper.selectOne(roleEntityQueryWrapper);
        if (roleEntityLocal != null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"字典编码重复");
        }
        //copy数据
        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(roleDto, roleEntity);

        roleMapper.insert(roleEntity);
    }
}
