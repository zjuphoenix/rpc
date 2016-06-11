package com.middleware.study.rpc.core;

/**
 * @author wuhaitao
 * @date 2016/5/27 15:13
 */
public interface Provider<T> extends Caller<T> {
    Class<T> getInterface();
}
