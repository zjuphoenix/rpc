package com.middleware.study.rpc.transport.api;

/**
 * @author wuhaitao
 * @date 2016/5/25 22:21
 */
public interface Response {
    Object getValue();
    Exception getException();
    long getRequestId();
}
