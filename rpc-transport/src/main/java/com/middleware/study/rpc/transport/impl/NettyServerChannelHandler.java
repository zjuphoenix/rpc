package com.middleware.study.rpc.transport.impl;

import com.middleware.study.rpc.transport.api.RequestHandler;
import com.middleware.study.rpc.transport.exception.FrameworkException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wuhaitao
 * @date 2016/5/25 23:50
 */
public class NettyServerChannelHandler extends ChannelInboundHandlerAdapter {

    private RequestHandler requestHandler;

    public NettyServerChannelHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof RpcRequest)){
            throw new FrameworkException("message type not support:"+msg.getClass());
        }
        RpcRequest request = (RpcRequest) msg;
        RpcResponse response = requestHandler.handler(request);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
