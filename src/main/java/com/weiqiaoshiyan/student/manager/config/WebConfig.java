package com.weiqiaoshiyan.student.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by zhang_htao on 2019/7/23.
 */

//这个类用于来扩展springmvc的功能
@Configuration
//@EnableWebMvc 如果使用这个注解将会全面接管springmvc的配置
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/success").setViewName("success");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Bean
    public WebMvcConfigurer getWebConfig(){
        return  new WebMvcConfigurer(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/index").setViewName("login");
               registry.addViewController("/teacher/login").setViewName("teacher/teacher_login");
                registry.addViewController("/unAuth").setViewName("unAuth");
                registry.addViewController("/teacher/manager").setViewName("teacher/teacher_manager");

            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {

            }
        };

    }

    @Bean
    public LocaleResolver localeResolver() {
        return  new LocalResolver();
    }
}
