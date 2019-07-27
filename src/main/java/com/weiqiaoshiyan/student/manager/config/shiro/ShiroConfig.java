package com.weiqiaoshiyan.student.manager.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * Created by zhang_htao on 2019/7/25.
 */
@Configuration
public class ShiroConfig {

    @Value(value = "${mysharo.hashAlgorithmName}")
    private  String hashAlgorithmName;
    @Value(value = "${mysharo.hashIterations}")
    private Integer hashIterations;

    /*创建ShiroFilterFactoryBean*/
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //link过滤器要添加的规则，具体表现为对哪些路径进行过滤
        LinkedHashMap<String, String> link = new LinkedHashMap<>();
        /**
         * shiro常用的过滤器规则有
         * anon - 无需认证可以访问
         * authc - 必须认证才能访问
         * user - 如果使用rememberMe可以访问
         * perms - 该资源必须得到资源权限才能访问
         * role -该资源必须得到角色权限才能访问
         */
        link.put("/ClassInfos","anon");
        link.put("/index","anon");
        link.put("/success","authc");
        link.put("/student/login","anon");
        link.put("/student/register","anon");
        link.put("/student_sign","authc");
        link.put("/teacher/login","anon");
        link.put("/teacher/*","perms[teacher:operator]");
        link.put("/logout","logout");
        link.put("/student/*","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(link);
        shiroFilterFactoryBean.setLoginUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("unAuth");
        return shiroFilterFactoryBean;
    }
    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }


    /**
     * 创建Realm
     */
    @Bean(value = "userRealm")
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        /**
         * 设置认证算法为MD5算法
         */
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        hashedCredentialsMatcher.setHashIterations(hashIterations);
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }
}
