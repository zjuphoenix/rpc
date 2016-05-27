package com.middleware.study.rpc.proxy.spi;

import com.middleware.study.rpc.proxy.ProxyFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author wuhaitao
 * @date 2016/5/26 23:16
 */
public class JdkProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(Class<T> clz, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clz}, invocationHandler);
    }
}
