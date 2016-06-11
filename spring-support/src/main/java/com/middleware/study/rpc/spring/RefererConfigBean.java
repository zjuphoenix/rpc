package com.middleware.study.rpc.spring;

import com.middleware.study.rpc.config.RefererConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * @author wuhaitao
 * @date 2016/6/3 11:02
 */
public class RefererConfigBean<T> extends RefererConfig<T> implements FactoryBean<T>, BeanFactoryAware, InitializingBean, DisposableBean {

    private static final long serialVersionUID = 2L;

    private transient BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public T getObject() throws Exception {
        return getRef();
    }

    @Override
    public Class<?> getObjectType() {
        return getClz();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
