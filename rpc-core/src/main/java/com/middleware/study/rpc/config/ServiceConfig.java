package com.middleware.study.rpc.config;

import com.middleware.study.rpc.core.impl.RpcExporter;
import com.middleware.study.rpc.core.impl.RpcServiceProvider;

/**
 * @author wuhaitao
 * @date 2016/5/27 15:56
 */
public class ServiceConfig<T> implements Config {
    private T instance;
    private int port;

    public ServiceConfig(T instance, int port) {
        this.instance = instance;
        this.port = port;
    }

    public void export(){
        RpcServiceProvider provider = new RpcServiceProvider(instance);
        RpcExporter exporter = new RpcExporter(provider, port);
        exporter.init();
        exporter.export();
    }
}
