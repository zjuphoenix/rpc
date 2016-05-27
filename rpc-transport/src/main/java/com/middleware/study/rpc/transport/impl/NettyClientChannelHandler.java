package com.middleware.study.rpc.transport.impl;

import com.middleware.study.rpc.transport.api.ResponseHandler;
import com.middleware.study.rpc.transport.exception.FrameworkException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wuhaitao
 * @date 2016/5/25 23:51
 */
public class NettyClientChannelHandler extends ChannelInboundHandlerAdapter {

    private ResponseHandler responseHandler;

    public NettyClientChannelHandler(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof RpcResponse)){
            throw new FrameworkException("message type not support:"+msg.getClass());
        }
        RpcResponse response = (RpcResponse) msg;
        responseHandler.handler(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
