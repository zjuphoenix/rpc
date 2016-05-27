package com.middleware.study.rpc.transport.impl;

import com.middleware.study.rpc.transport.api.Client;
import com.middleware.study.rpc.transport.api.EndpointFactory;
import com.middleware.study.rpc.transport.api.RequestHandler;
import com.middleware.study.rpc.transport.api.Server;

/**
 * @author wuhaitao
 * @date 2016/5/25 23:40
 */
public class NettyEndpointFactory implements EndpointFactory {
    @Override
    public Server createServer(int port, RequestHandler requestHandler){
        return new NettyServer(port, requestHandler);
    }
    @Override
    public Client createClient(String host, int port){
        return new NettyClient(host, port);
    }
}
