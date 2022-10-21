package com.xxx.crm.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


// 用于获取SpringBoot创建好的工厂
//@Configuration
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    // 保留下来的工厂
    private static ApplicationContext applicationContext;

    // 将创建好的工厂一参数的形式传递给这个类
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // 提供在工厂中获取对象的方法
    /**
     * 根据bean的名字拿到工厂对象   RedisTemplate --> redisTemplate
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
