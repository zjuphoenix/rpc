package com.middleware.study.rpc.proxy;

import java.lang.reflect.InvocationHandler;

/**
 * @author wuhaitao
 * @date 2016/5/26 23:15
 */
public interface ProxyFactory {
    <T> T getProxy(Class<T> clz, InvocationHandler invocationHandler);
}
