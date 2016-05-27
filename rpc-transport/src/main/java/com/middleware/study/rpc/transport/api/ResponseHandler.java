package com.middleware.study.rpc.transport.api;


import com.middleware.study.rpc.transport.impl.RpcResponse;

/**
 * @author wuhaitao
 * @date 2016/5/26 12:56
 */
public interface ResponseHandler {
    void handler(RpcResponse response);
}
