package com.middleware.study.rpc.core.impl;

import com.middleware.study.rpc.core.Referer;
import com.middleware.study.rpc.transport.api.Client;
import com.middleware.study.rpc.transport.exception.FrameworkException;
import com.middleware.study.rpc.transport.impl.NettyEndpointFactory;
import com.middleware.study.rpc.transport.impl.ResponseFuture;
import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;

/**
 * @author wuhaitao
 * @date 2016/5/27 12:32
 */
public class RpcReferer<T> implements Referer<T> {

    private Client client;
    private Class<T> clz;
    private String serviceHost;
    private int servicePort;

    public RpcReferer(Class<T> clz, String serviceHost, int servicePort) {
        this.clz = clz;
        this.serviceHost = serviceHost;
        this.servicePort = servicePort;
    }

    @Override
    public Class<T> getInterface() {
        return clz;
    }

    @Override
    public RpcResponse call(RpcRequest request) {
        ResponseFuture responseFuture = client.request(request);
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        try {
            Object obj = responseFuture.getValue();
            if (responseFuture.isSuccess()) {
                response.setValue(obj);
            }
            else {
                response.setException(responseFuture.getException());
            }
        } catch (Throwable t){
            response.setException(new FrameworkException(t));
        }
        return response;
    }

    @Override
    public void init() {
        NettyEndpointFactory factory = new NettyEndpointFactory();
        client = factory.createClient(serviceHost, servicePort);
        client.init();
    }

    @Override
    public void destroy() {
        client.close();
    }

    @Override
    public boolean isAvailable() {
        return client.isAvailable();
    }

    @Override
    public String desc() {
        return "RpcReferer";
    }
}
