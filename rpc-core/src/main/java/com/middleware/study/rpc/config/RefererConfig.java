package com.middleware.study.rpc.config;

import com.middleware.study.rpc.core.impl.RpcReferer;
import com.middleware.study.rpc.proxy.ProxyFactory;
import com.middleware.study.rpc.proxy.RefererInvocationHandler;
import com.middleware.study.rpc.proxy.spi.JdkProxyFactory;

/**
 * @author wuhaitao
 * @date 2016/5/27 15:57
 */
public class RefererConfig<T> implements Config {
    private Class<T> clz;
    private String host;
    private int port;
    private T ref;

    public RefererConfig(Class<T> clz, String host, int port) {
        this.clz = clz;
        this.host = host;
        this.port = port;
    }

    public T getRef(){
        RpcReferer referer = new RpcReferer(clz, host, port);
        referer.init();
        ProxyFactory proxyFactory = new JdkProxyFactory();
        ref = proxyFactory.getProxy(clz, new RefererInvocationHandler<T>(clz, referer));
        return ref;
    }
}
