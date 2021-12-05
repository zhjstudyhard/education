package com.education.config;

import com.education.config.realm.JWTFilter;
import com.education.config.realm.UserRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-02-04-13-32
 */
@Configuration
public class shiroConfig {
    @Bean
    @Qualifier(value = "userRealm")
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    @Bean
    @Qualifier(value = "defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean filterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", new JWTFilter());
        //在shiro过滤器加入自己的jwt过滤器
        shiroFilterFactoryBean.setFilters(filters);
        //设置请求拦截
        Map<String, String> map = new HashMap<>();
        //所有请求使用jwt过滤器拦截
//        map.put("/category/**","anon");
        map.put("/api/userLogin/login", "anon");
//        map.put("/Post/levelBlogs","anon");
//        map.put("/Post/detailBlog","anon");
//        map.put("/Comment/getComments","anon");
//        map.put("/search/searchBlogs","anon");
        map.put("/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //开启注解模式
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;

    }

    //shiro注解代理
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        return creator;

    }
}
