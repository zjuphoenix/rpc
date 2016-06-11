package com.middleware.study.rpc.filter.impl;

import com.middleware.study.rpc.core.Caller;
import com.middleware.study.rpc.filter.Filter;
import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuhaitao
 * @date 2016/6/3 10:07
 */
public class MonitorFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(MonitorFilter.class);
    @Override
    public RpcResponse request(Caller caller, RpcRequest request) {
        long start = System.currentTimeMillis();
        RpcResponse response = caller.call(request);
        long end = System.currentTimeMillis();
        logger.info("service is {}, method is {}, time is {}", request.getInterfaceName(), request.methodName(), end-start);
        return response;
    }
}
