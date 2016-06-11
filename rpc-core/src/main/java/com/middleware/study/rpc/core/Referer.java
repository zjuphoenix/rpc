package com.middleware.study.rpc.core;

/**
 * @author wuhaitao
 * @date 2016/5/25 21:00
 */
public interface Referer<T> extends Node, Caller<T> {
    Class<T> getInterface();
    String getServiceUrl();
}
