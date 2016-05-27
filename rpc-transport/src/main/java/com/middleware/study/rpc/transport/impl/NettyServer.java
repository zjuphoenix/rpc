package com.middleware.study.rpc.transport.impl;

import com.middleware.study.rpc.transport.api.RequestHandler;
import com.middleware.study.rpc.transport.common.EndpointState;
import com.middleware.study.rpc.transport.api.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wuhaitao
 * @date 2016/5/25 23:20
 */
public class NettyServer implements Server {

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;//acceptor线程
    private EventLoopGroup workerGroup;//io线程
    private Channel serverChannel;
    private NettyServerChannelManage nettyServerChannelManage;
    private volatile EndpointState state = EndpointState.UNINIT;
    private int port;
    private RequestHandler requestHandler;

    public NettyServer(int port, RequestHandler requestHandler) {
        this.port = port;
        this.requestHandler = requestHandler;
    }

    @Override
    public boolean bind() {
        try {
            ChannelFuture f = serverBootstrap.bind(port).sync();
            serverChannel = f.channel();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isBound() {
        return serverChannel != null && serverChannel.isOpen();
    }

    @Override
    public void init() {
        nettyServerChannelManage = new NettyServerChannelManage(100);
        bossGroup = new NioEventLoopGroup(1);//acceptor线程
        workerGroup = new NioEventLoopGroup(2);//io线程
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        RpcCodecAdapter rpcCodecAdapter = new RpcCodecAdapter(new FastJsonSerialization());
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast("channelManage", nettyServerChannelManage);
                        p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                        p.addLast("decoder", rpcCodecAdapter.decoder());
                        p.addLast("frameEncoder", new LengthFieldPrepender(2));
                        p.addLast("encoder", rpcCodecAdapter.encoder());
                        p.addLast(new NettyServerChannelHandler(requestHandler));
                    }
                });
        state = EndpointState.INIT;
    }

    @Override
    public void close() {
        /**
         * 关闭监听
         */
        if (serverChannel != null){
            serverChannel.close();
        }
        /**
         * 关闭client连接
         */
        nettyServerChannelManage.close();
        /**
         * 关闭线程池
         */
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        state = EndpointState.CLOSE;
    }

    @Override
    public boolean isClosed() {
        return state.isCloseState();
    }

    @Override
    public boolean isAvailable() {
        return state.isAliveState();
    }
}
