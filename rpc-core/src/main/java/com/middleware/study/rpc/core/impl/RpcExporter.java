package com.middleware.study.rpc.core.impl;

import com.middleware.study.rpc.core.Exporter;
import com.middleware.study.rpc.core.Provider;
import com.middleware.study.rpc.transport.api.RequestHandler;
import com.middleware.study.rpc.transport.api.Server;
import com.middleware.study.rpc.transport.impl.NettyEndpointFactory;
import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;

/**
 * @author wuhaitao
 * @date 2016/5/27 15:30
 */
public class RpcExporter<T> implements Exporter<T> {

    private Provider<T> provider;
    private int port;
    private Server server;

    public RpcExporter(Provider<T> provider, int port) {
        this.provider = provider;
        this.port = port;
    }

    @Override
    public Provider<T> getProvider() {
        return provider;
    }

    @Override
    public void export() {
        server.bind();
    }

    @Override
    public void unexport() {
        destroy();
    }

    @Override
    public void init() {
        NettyEndpointFactory factory = new NettyEndpointFactory();
        server = factory.createServer(port, new RequestHandler() {
            @Override
            public RpcResponse handler(RpcRequest request) {
                return provider.call(request);
            }
        });
        server.init();
    }

    @Override
    public void destroy() {
        server.close();
    }

    @Override
    public boolean isAvailable() {
        return server.isAvailable();
    }

    @Override
    public String desc() {
        return "RpcExporter";
    }
}
