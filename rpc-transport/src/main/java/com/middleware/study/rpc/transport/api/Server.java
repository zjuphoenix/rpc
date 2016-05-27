package com.middleware.study.rpc.transport.api;

/**
 * @author wuhaitao
 * @date 2016/5/25 23:19
 */
public interface Server extends Endpoint {
    boolean bind();
    boolean isBound();
}
