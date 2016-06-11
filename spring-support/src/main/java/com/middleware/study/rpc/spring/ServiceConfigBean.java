package com.middleware.study.rpc.spring;

import com.middleware.study.rpc.config.ServiceConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author wuhaitao
 * @date 2016/6/3 11:02
 */
public class ServiceConfigBean extends ServiceConfig implements
        BeanPostProcessor,
        BeanFactoryAware,
        InitializingBean,
        DisposableBean,
        ApplicationListener<ContextRefreshedEvent> {

    private static final long serialVersionUID = 1L;

    private transient BeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!getExported().get()){
            super.export();
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public void destroy() throws Exception {
        super.unexport();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
