package com.education.service.serviceImpl.login;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.config.SecurityBean;
import com.education.constant.Constant;
import com.education.dto.system.UserDto;
import com.education.entity.system.UserEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.system.UserMapper;
import com.education.service.login.UserLoginService;
import com.education.util.JWTUntils;
import com.education.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-03-11-41
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserMapper userMapper;


//    @Value("${security.refreshTokenExpireTime}")
//    private String refreshTokenExpireTime;

    @Override
    public Result login(UserDto userDto, HttpServletResponse response) {
       if (StringUtils.isBlank(userDto.getUsername())){
           throw new EducationException(ResultCode.FAILER_CODE.getCode(),"用户名不能为空");
       }
       if (StringUtils.isBlank(userDto.getPassword())){
           throw new EducationException(ResultCode.FAILER_CODE.getCode(),"密码不能为空");
       }
       //判断是否存在该用户
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("username",userDto.getUsername());
        UserEntity userEntity = userMapper.selectOne(userEntityQueryWrapper);
        if (userEntity == null){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"该用户不存在");
        }
        if (userEntity.getEnabled().equals(1)){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"账号已锁定，请联系管理员");
        }
        //校验密码正确性和账号是否过期
        if (userDto.getPassword().equals(userEntity.getPassword())){
            Date expired = userEntity.getExpired();
            Date nowDate = new Date();
            if (expired.getTime() < nowDate.getTime()){
                throw new EducationException(ResultCode.FAILER_CODE.getCode(),"账号已过期，请联系管理员");
            }
            long currentTimeMillis = System.currentTimeMillis();
            //生成refreshToken，用于刷新令牌
            RedisUtil.setEx(Constant.SHIRO_REFRESH_TOKEN + userEntity.getUsername(),String.valueOf(currentTimeMillis),Long.parseLong(SecurityBean.refreshTokenExpireTime), TimeUnit.SECONDS);
            //生成token
            HashMap<String, String> claimMap = new HashMap<>();
            claimMap.put("username",userEntity.getUsername());
            claimMap.put(Constant.CURREN_TIIME_MILLIS,String.valueOf(currentTimeMillis));
            //响应生成请求头
            String token = JWTUntils.getToken(claimMap);
//            response.setHeader("Authorization",token);
//            //暴露请求头信息
//            response.setHeader("Access-control-Expose-Headers", "Authorization");
            return Result.success().data("token",token);
        }else {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"密码错误");
        }
    }

    @Override
    public void loginOut() {
        Subject subject = SecurityUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
        subject.logout();
    }
}
