package com.middleware.study.rpc.filter;

import com.middleware.study.rpc.core.Caller;
import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;

/**
 * @author wuhaitao
 * @date 2016/6/3 9:59
 */
public interface Filter {
    RpcResponse request(Caller caller, RpcRequest request);
}
