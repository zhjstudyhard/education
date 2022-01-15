package com.education.service.serviceImpl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.config.SecurityBean;
import com.education.constant.Constant;
import com.education.controller.upload.UploadFileUtil;
import com.education.dto.base.ResponsePageDto;
import com.education.dto.system.DictionaryDto;
import com.education.dto.system.PasswordDto;
import com.education.dto.system.UserDto;
import com.education.entity.system.DictionaryEntity;
import com.education.entity.system.RoleEntity;
import com.education.entity.system.UserEntity;
import com.education.entity.system.UserRoleEntity;
import com.education.entity.uploadfile.UploadFileEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.system.DictionaryMapper;
import com.education.mapper.system.RoleMapper;
import com.education.mapper.system.UserMapper;
import com.education.mapper.system.UserRoleMapper;
import com.education.mapper.upload.UploadFileMapper;
import com.education.service.system.DictionaryService;
import com.education.service.system.UserService;
import com.education.util.EntityUtil;
import com.education.util.MD5Util;
import com.education.util.RSAUtil;
import com.education.util.ShiroEntityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.util.Password;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-22-13-55
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Override
    public Result queryUserAllPage(UserDto userDto) {
        //使用pageHelper分页
        PageHelper.startPage(userDto.getCurrentPage(), userDto.getPageSize());
        PageInfo pageInfo = new PageInfo<>(userMapper.queryUserAllPage(userDto));
        ResponsePageDto responsePageDto = new ResponsePageDto(pageInfo.getList(), pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getPageNum());
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", responsePageDto);
        return Result.success().data(map);
    }

    @Override
    public void deleteUser(UserDto userDto) {
        if (StringUtils.isBlank(userDto.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不能为空");
        }
        UserEntity userEntity = userMapper.selectById(userDto.getId());
        if (userEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }
        userEntity.setIsDeleted(1);
        //更信任信息
        EntityUtil.addModifyInfo(userEntity);
        userMapper.updateById(userEntity);
    }

    @Override
    public void editUser(UserDto userDto) {
        if (StringUtils.isBlank(userDto.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不能为空");
        }
        //校验用户名是否重复
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("username", userDto.getUsername());
        UserEntity userEntityRepart = userMapper.selectOne(userEntityQueryWrapper);
        if (userEntityRepart != null) {
            if (!userEntityRepart.getId().equals(userDto.getId())) {
                throw new EducationException(ResultCode.FAILER_CODE.getCode(), "用户名重复");
            }
        }
        UserEntity userEntityLocal = userMapper.selectById(userDto.getId());
        //更新用户
        userEntityLocal.setEnabled(userDto.getEnabled());
        userEntityLocal.setExpired(userDto.getExpired());
        userEntityLocal.setLocked(userDto.getLocked());
        userEntityLocal.setIsExpired(userDto.getIsExpired());
        userEntityLocal.setExpired(userDto.getExpired());
        userEntityLocal.setUsername(userDto.getUsername());
        userEntityLocal.setRealName(userDto.getRealName());
        userMapper.updateById(userEntityLocal);

        //更新角色
        QueryWrapper<UserRoleEntity> userRoleEntityQueryWrapper = new QueryWrapper<>();
        userRoleEntityQueryWrapper.eq("is_deleted",Constant.ISDELETED_FALSE)
                .eq("user_id",userEntityLocal.getId());
        UserRoleEntity userRoleEntity = userRoleMapper.selectOne(userRoleEntityQueryWrapper);
        if (userRoleEntity != null ){
            userRoleEntity.setRoleId(userDto.getRoleId());
            //更信任信息
            EntityUtil.addModifyInfo(userRoleEntity);
            userRoleMapper.updateById(userRoleEntity);
        }
    }

    @Override
    public void addUser(UserDto userDto) {
        //校验用户名是否重复
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("username", userDto.getUsername());
        UserEntity userEntityLocal = userMapper.selectOne(userEntityQueryWrapper);
        if (userEntityLocal != null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "用户名重复");
        }
        //copy数据
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setIsExpired(0);
        userEntity.setLocked(0);
        userMapper.insert(userEntity);

        //添加角色
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userEntity.getId());
        userRoleEntity.setRoleId(userDto.getRoleId());
        //创建人信息
        EntityUtil.addCreateInfo(userRoleEntity);
        userRoleMapper.insert(userRoleEntity);
    }

    @Override
    public void updatePassword(PasswordDto passwordDto) throws Exception{
        UserEntity userEntity = ShiroEntityUtil.getShiroEntity();

        String oldPassword = RSAUtil.decrypt(passwordDto.getOldPassword(), SecurityBean.encryptRSAKey);
        if (!MD5Util.encrypt(oldPassword).equals(userEntity.getPassword())){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"旧密码错误");
        }
        EntityUtil.addModifyInfo(userEntity);
        //设置密码
        String newPassword = RSAUtil.decrypt(passwordDto.getNewPassword(), SecurityBean.encryptRSAKey);
        userEntity.setPassword(MD5Util.encrypt(newPassword));
        //更改数据
        userMapper.updateById(userEntity);
    }

    @Override
    public void registeUser(UserDto userDto) throws Exception{
        //校验数据
        if (StringUtils.isBlank(userDto.getUsername()) || StringUtils.isBlank(userDto.getPassword())){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"请正确填写信息");
        }
        //校验用户名是否重复
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("username", userDto.getUsername());
        UserEntity userEntityLocal = userMapper.selectOne(userEntityQueryWrapper);
        if (userEntityLocal != null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "用户名重复");
        }
        //密码解密
        String password = userDto.getPassword();
        String decryptPassword = RSAUtil.decrypt(password, SecurityBean.encryptRSAKey);
        //密码加密
        String encryptPassword = MD5Util.encrypt(decryptPassword);
        //属性赋值
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encryptPassword);
        userEntity.setIsExpired(0);
        userEntity.setLocked(0);
        userEntity.setEnabled(0);
        userEntity.setGmtCreate(new Date());
        userEntity.setNameCreate(userEntity.getUsername());

        //添加用户
        userMapper.insert(userEntity);

        //添加普通人员角色信息
        QueryWrapper<RoleEntity> roleEntityQueryWrapper = new QueryWrapper<>();
        roleEntityQueryWrapper.eq("is_deleted",Constant.ISDELETED_FALSE)
                .eq("role_code",Constant.ROLE_USER);
        RoleEntity roleEntity = roleMapper.selectOne(roleEntityQueryWrapper);
        if (roleEntity != null) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setRoleId(roleEntity.getId());
            userRoleEntity.setUserId(userEntity.getId());
            userRoleEntity.setGmtCreate(new Date());
            userRoleEntity.setNameCreate(userEntity.getUsername());

            userRoleMapper.insert(userRoleEntity);
        }
    }

    @Override
    public void updateAvatar(UserDto userDto) {
        UserEntity userEntity = ShiroEntityUtil.getShiroEntity();
        //更新关联id
        UploadFileEntity uploadFileEntity = uploadFileMapper.selectById(userDto.getFileId());
        if (uploadFileEntity != null){
            uploadFileEntity.setRelativeId(userEntity.getId());
            EntityUtil.addModifyInfo(uploadFileEntity);
            uploadFileMapper.updateById(uploadFileEntity);

            //修改用户表
            userEntity.setAvatar(uploadFileEntity.getFilePath());
            EntityUtil.addModifyInfo(userEntity);
            userMapper.updateById(userEntity);

        }else {
            throw new EducationException(Result.fail().getCode(),"图片不存在");
        }
    }
}
