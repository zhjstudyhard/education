package com.education.config.realm;


import com.education.service.system.UserService;
import com.education.util.JWTUntils;
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
    private UserService userService;

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
//        String token = (String) authenticationToken.getPrincipal();
//        String userName = null;
//        try {
//            userName = JWTUntils.getUserName(token);
//        }catch (Exception e) {
//            throw new AuthenticationException("token失效");
//        }
//        if (!JWTUntils.verify(token)){
//            throw new AuthenticationException("token认证失效，token错误或者过期，重新登陆");
//        }
//        User user = userService.userLogin(userName);
//        if (user == null) {
//            throw new AuthenticationException("该用户不存在");
//        }
//        return new SimpleAuthenticationInfo(token,token,getName());
        return null;
    }
}
