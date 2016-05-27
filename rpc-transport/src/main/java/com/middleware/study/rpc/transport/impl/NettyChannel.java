package com.middleware.study.rpc.transport.impl;

import com.middleware.study.rpc.transport.api.Request;
import com.middleware.study.rpc.transport.common.EndpointState;
import com.middleware.study.rpc.transport.api.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author wuhaitao
 * @date 2016/5/25 23:26
 */
public class NettyChannel implements Channel {

    private NettyClient nettyClient;
    private io.netty.channel.Channel channel;
    private volatile EndpointState state = EndpointState.UNINIT;

    public NettyChannel(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
        state = EndpointState.UNINIT;
    }

    @Override
    public synchronized boolean open() {
        String host = nettyClient.getServerHost();
        int port = nettyClient.getPort();
        try {
            ChannelFuture f = nettyClient.getBootstrap().connect(host, port).sync();
            channel = f.channel();
            state = EndpointState.ALIVE;
            return true;
        } catch (InterruptedException e) {
            state = EndpointState.UNALIVE;
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean close() {
        channel.close();
        state = EndpointState.CLOSE;
        return true;
    }

    @Override
    public boolean isClosed() {
        return state == EndpointState.CLOSE;
    }

    @Override
    public boolean isAvailable() {
        return state == EndpointState.ALIVE;
    }

    @Override
    public ResponseFuture sendRequest(Request request) {
        ResponseFuture responseFuture = new ResponseFuture(request);
        nettyClient.registerCallback(request.getRequestId(), responseFuture);
        ChannelFuture writeFuture = channel.writeAndFlush(request);
        //boolean writeResult = writeFuture.awaitUninterruptibly(1000, TimeUnit.MILLISECONDS);
        return responseFuture;
    }
}
