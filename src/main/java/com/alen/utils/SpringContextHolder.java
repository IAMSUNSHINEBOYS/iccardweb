package com.alen.utils;

import org.apache.commons.lang.Validate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 *  applicationContext静态化
 * 使用了ApplicationContextAware接口的类，如果受spring容器管理的
 * 话，那么就会自动的调用ApplicationContextAware中的setApplicationContext方法
 * @author LK
 * @version 1.0
 * @date 2020/5/21 14:00
 */
@Service
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware,DisposableBean {
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext=applicationContext;
    }

    //清空applicationContext 设置其为null
    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }
    //获得applicationContext
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    public static void clearHolder(){
        applicationContext=null;
    }
    //获取Bean
    public static <T> T getBean(Class<T> requiredType){
        //assertContextInjected();
        return (T) getApplicationContext().getBean(requiredType);
    }
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name){
        assertContextInjected();
        return (T) getApplicationContext().getBean(name);
    }
    //判断application是否为空
    public static void assertContextInjected(){
        Validate.isTrue(applicationContext==null, "application未注入 ，请在springContext.xml中注入SpringHolder!");
    }
}
