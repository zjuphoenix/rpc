package com.middleware.study.rpc.transport.api;

/**
 * @author wuhaitao
 * @date 2016/5/25 23:18
 */
public interface Endpoint {
    void init();
    void close();
    boolean isClosed();
    boolean isAvailable();
}
