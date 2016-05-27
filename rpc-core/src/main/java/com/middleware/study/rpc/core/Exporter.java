package com.middleware.study.rpc.core;

import com.middleware.study.rpc.core.impl.RpcServiceProvider;

/**
 * @author wuhaitao
 * @date 2016/5/25 21:00
 */
public interface Exporter<T> extends Node {
    RpcServiceProvider<T> getProvider();
    void export();
    void unexport();
}
