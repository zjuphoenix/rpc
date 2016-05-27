package com.middleware.study.rpc.transport.api;

import com.middleware.study.rpc.transport.impl.ResponseFuture;

/**
 * @author wuhaitao
 * @date 2016/5/25 23:20
 */
public interface Client extends Endpoint {
    String getServerHost();
    int getPort();
    ResponseFuture request(Request request);
}
