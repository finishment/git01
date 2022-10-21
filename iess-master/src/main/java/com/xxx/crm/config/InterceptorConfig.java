package com.xxx.crm.config;

import com.xxx.crm.exceptions.NoLoginException;
import com.xxx.crm.interceptor.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Bean
    public NoLoginInterceptor noLoginInterceptor() {
        return new NoLoginInterceptor();
    }

//    /**
//     * 拦截器配置
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(noLoginInterceptor())
//                // 设置需要被拦截的资源，这里配置拦截所有
////        !--开启具体的拦截器的使用，可以配置多个-->
////        <!--设置拦截器的拦截路径，支持*通配-->
////        <!--/**         表示拦截所有映射-->
////         <!--/*          表示拦截所有/开头的映射-->
////         <!--/user/*     表示拦截所有/user/开头的映射-->
////         <!--/user/add*  表示拦截所有/user/开头，且具体映射名称以add开头的映射-->
////         <!--/user/*All  表示拦截所有/user/开头，且具体映射名称以All结尾的映射-->
////         <!--设置拦截排除的路径，配置/**或/*，达到快速配置的目的-->
////         <!--指定具体的拦截器类-->
//                .addPathPatterns("/discuss")
//                // 设置不需要被拦截的资源
//                .excludePathPatterns("/css/**", "/images/**",
//                        "/js/**", "/lib/**",
//                        "/index",
//                        "/user/login",
//                        "/api/msm/*");
//    }

    // springboot2.x 静态资源在自定义拦截器之后无法访问的解决方案
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**") //代表以什么样的请求路径访问静态资源
                .addResourceLocations("classpath:/views/")
                .addResourceLocations("classpath:/public/");

    }

}
