package com.middleware.study.rpc.core;

import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;

/**
 * @author wuhaitao
 * @date 2016/5/25 21:01
 */
public interface Caller<T> {
    Class<T> getInterface();
    RpcResponse call(RpcRequest request);
}
