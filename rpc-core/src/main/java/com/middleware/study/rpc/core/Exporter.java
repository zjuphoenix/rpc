package com.middleware.study.rpc.core;


/**
 * @author wuhaitao
 * @date 2016/5/25 21:00
 */
public interface Exporter<T> extends Node {
    Provider<T> getProvider();
    void export();
    void unexport();
}
