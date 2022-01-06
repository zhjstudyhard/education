package com.education.config.realm;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.constant.Constant;
import com.education.entity.system.UserEntity;
import com.education.mapper.system.UserMapper;
import com.education.mapper.system.UserRoleMapper;
import com.education.util.JWTUntils;
import com.education.util.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-02-04-13-25
 */
public class UserRealm extends AuthorizingRealm {
    private static final Logger logger =  LogManager.getLogger();

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("进入doGetAuthorizationInfo授权。。。。。。");
        //获取登录验证的参数是什么,principalCollection就是什么
        UserEntity userEntity = (UserEntity)principalCollection.getPrimaryPrincipal();
        //返回授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取当前用户的角色信息
        List<String> roles =  userRoleMapper.queryUserRoles(userEntity);
        if (roles != null && roles.size() > 0){
            simpleAuthorizationInfo.addRoles(roles);
        }
//        //添加角色信息
//        for (Role role : roles.getRoles()){
//            simpleAuthorizationInfo.addRole(role.getRoleName());
//            for (Permission permission : role.getPermissions()){
//                System.out.println("role.permission: "+permission);
//                simpleAuthorizationInfo.addStringPermission(permission.getSn());
//            }
//        }
        return simpleAuthorizationInfo;
    }

    //用户密码验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("进入doGetAuthenticationInfo认证。。。。。。");
        String token = (String) authenticationToken.getCredentials();
        String username = null;
        try {
            username = JWTUntils.getClaim(token, "username");
        } catch (Exception e) {
            throw new AuthenticationException("token非法，不是规范的token，可能被篡改了，或者过期了");
        }
        if (username == null) {
            throw new AuthenticationException("token中无用户名");
        }
        //根据用户名查询用户
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("username",username);
        UserEntity userEntity = userMapper.selectOne(userEntityQueryWrapper);
        if (userEntity == null) {
            throw new AuthenticationException("该用户不存在");
        }
        //开始认证，只要AccessToken没有过期，或者refreshToken的时间节点和AccessToken一致即可
        if (RedisUtil.hasKey(Constant.SHIRO_REFRESH_TOKEN + username)) {
            //判断AccessToken有无过期
            if (!JWTUntils.verify(token)) {
                throw new TokenExpiredException("token认证失效，token过期，重新登陆");
            } else {
                //判断AccessToken和refreshToken的时间节点是否一致
                Long current = Long.parseLong(RedisUtil.get(Constant.SHIRO_REFRESH_TOKEN + username));
                if (current.equals(Long.parseLong(JWTUntils.getClaim(token,Constant.CURREN_TIIME_MILLIS)))) {
                    return new SimpleAuthenticationInfo(userEntity, token, getName());
                } else {
                    throw new AuthenticationException("token已经失效，请重新登录！");
                }
            }
        } else {
            throw new AuthenticationException("token过期或者Token错误！！");
        }
    }
}
