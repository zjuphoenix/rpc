package com.middleware.study.rpc.transport.api;

import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;

/**
 * @author wuhaitao
 * @date 2016/5/27 15:18
 */
public interface RequestHandler {
    RpcResponse handler(RpcRequest request);
}
