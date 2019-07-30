package com.weiqiaoshiyan.student.manager.config.shiro;

import com.weiqiaoshiyan.student.manager.constant.LoginType;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
         *       anon(AnonymousFilter.class),
                 authc(FormAuthenticationFilter.class),
                 authcBasic(BasicHttpAuthenticationFilter.class),
                 logout(LogoutFilter.class),
                 noSessionCreation(NoSessionCreationFilter.class),
                 perms(PermissionsAuthorizationFilter.class),
                 port(PortFilter.class),
                 rest(HttpMethodPermissionFilter.class),
                 roles(RolesAuthorizationFilter.class),
                 ssl(SslFilter.class),
                 user(UserFilter.class);
         */
        link.put("/asserts/**", "anon");
        link.put("/webjars/**","anon");
        link.put("/ClassInfos","anon");
        link.put("/index","anon");
        link.put("/student/login","anon");
        link.put("/student/register","anon");
        link.put("/teacher/login","anon");
        link.put("/teacher/loginIn","anon");
        link.put("/teacher/register","anon");
        link.put("/teacher/isOnlyAccount","anon");
        link.put("/logout","logout");
        link.put("teacher/teacherManager","authc");
        link.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(link);
        shiroFilterFactoryBean.setLoginUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("unAuth");
        return shiroFilterFactoryBean;
    }
    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm,@Qualifier("teacherRealm") TeacherRealm teacherRealm,@Qualifier("modularRealmAuthenticator") ModularRealmAuthenticator modularRealmAuthenticator) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setAuthenticator(modularRealmAuthenticator);
        List<Realm> realms = new ArrayList<>();
        realms.add(userRealm);
        realms.add(teacherRealm);
        defaultWebSecurityManager.setRealms(realms);
        return defaultWebSecurityManager;
    }


    /**
     * 创建Realm
     */
    @Bean(value = "userRealm")
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setName(LoginType.STUDENT.getLogin());
        /**
         * 设置认证算法为MD5算法
         */
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        hashedCredentialsMatcher.setHashIterations(hashIterations);
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }

    @Bean("teacherRealm")
    public TeacherRealm teacherRealm() {

      TeacherRealm teacherRealm = new TeacherRealm();
      teacherRealm.setName(LoginType.TEACHER.getLogin());
        /**
         * 设置认证算法为MD5算法
         */
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        hashedCredentialsMatcher.setHashIterations(hashIterations);
        teacherRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return  teacherRealm;
    }

    @Bean(name = "modularRealmAuthenticator")
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        UserModularRealmAuthenticator userModularRealmAuthenticator = new UserModularRealmAuthenticator();
        userModularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return userModularRealmAuthenticator;
    }

    /**
     * 开启shiro的注解
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
     public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager ) {
         AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
         authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
         return authorizationAttributeSourceAdvisor;
     }
}
