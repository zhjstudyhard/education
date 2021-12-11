package com.education.config.realm;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.constant.Constant;
import com.education.entity.system.UserEntity;
import com.education.mapper.system.UserMapper;
import com.education.service.system.UserService;
import com.education.util.JWTUntils;
import com.education.util.RedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-02-04-13-25
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        //获取登录验证的参数是什么,principalCollection就是什么
//        String token = (String)principalCollection.getPrimaryPrincipal();
//        System.out.println("principalCollection: "+token);
//        //获取当前用户的用户名
//        String userName = JWTuntils.getUserName(token);
//        //获取当前用户的角色信息
//        userVO roles = userService.getRoles(userName);
//        System.out.println("roles: "+roles);
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        //添加角色信息
//        for (Role role : roles.getRoles()){
//            simpleAuthorizationInfo.addRole(role.getRoleName());
//            for (Permission permission : role.getPermissions()){
//                System.out.println("role.permission: "+permission);
//                simpleAuthorizationInfo.addStringPermission(permission.getSn());
//            }
//        }
//        return simpleAuthorizationInfo;
        return null;
    }

    //用户密码验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证~~~~~~~");
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
