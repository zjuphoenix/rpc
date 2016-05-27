package com.middleware.study.rpc.transport.impl;

import com.middleware.study.rpc.transport.api.*;
import com.middleware.study.rpc.transport.exception.ServiceException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.commons.pool.impl.GenericObjectPool;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuhaitao
 * @date 2016/5/25 23:20
 */
public class NettyClient implements Client {

    private Bootstrap bootstrap;
    private EventLoopGroup group;
    private String serverHost;
    private int port;
    private GenericObjectPool pool;

    private ConcurrentHashMap<Long, ResponseFuture> callbackMap = new ConcurrentHashMap<>();

    public NettyClient(String serverHost, int port) {
        this.serverHost = serverHost;
        this.port = port;
    }

    @Override
    public String getServerHost() {
        return serverHost;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public ResponseFuture request(Request request) {
        Channel channel = null;
        try {
            channel = borrowChannel();
            if (channel == null){
                return null;
            }
            ResponseFuture response = channel.sendRequest(request);
            returnChannel(channel);
            return response;
        } catch (Exception e) {
            invalidateChannel(channel);
            throw new ServiceException(e);
        }
    }

    private Channel borrowChannel() throws Exception {
        Channel channel = (Channel) pool.borrowObject();
        if (channel != null && channel.isAvailable()){
            return channel;
        }
        invalidateChannel(channel);
        throw new SecurityException("borrow channel error");
    }

    private void invalidateChannel(Channel nettyChannel) {
        if (nettyChannel == null) {
            return;
        }
        try {
            pool.invalidateObject(nettyChannel);
        } catch (Exception ie) {
            ie.printStackTrace();
        }
    }

    private void returnChannel(Channel channel) {
        if (channel == null) {
            return;
        }
        try {
            pool.returnObject(channel);
        } catch (Exception ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void init() {
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        long defaultMinEvictableIdleTimeMillis = (long) 1000 * 60 * 60;//默认链接空闲时间
        long defaultSoftMinEvictableIdleTimeMillis = (long) 1000 * 60 * 10;//
        long defaultTimeBetweenEvictionRunsMillis = (long) 1000 * 60 * 10;//默认回收周期
        poolConfig.minIdle = 10;
        poolConfig.maxIdle = 50;
        poolConfig.maxActive = poolConfig.maxIdle;
        poolConfig.maxWait = 50;
        poolConfig.lifo = true;
        poolConfig.minEvictableIdleTimeMillis = defaultMinEvictableIdleTimeMillis;
        poolConfig.softMinEvictableIdleTimeMillis = defaultSoftMinEvictableIdleTimeMillis;
        poolConfig.timeBetweenEvictionRunsMillis = defaultTimeBetweenEvictionRunsMillis;
        pool = new GenericObjectPool(new NettyChannelFactory(this), poolConfig);

        group = new NioEventLoopGroup(10);
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        RpcCodecAdapter rpcCodecAdapter = new RpcCodecAdapter(new FastJsonSerialization());
                        p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                        p.addLast("decoder", rpcCodecAdapter.decoder());
                        p.addLast("frameEncoder", new LengthFieldPrepender(2));
                        p.addLast("encoder", rpcCodecAdapter.encoder());
                        p.addLast(new NettyClientChannelHandler(new ResponseHandler() {
                            @Override
                            public void handler(RpcResponse response) {
                                ResponseFuture responseFuture = NettyClient.this.removeCallback(response.getRequestId());
                                if (responseFuture == null){
                                    System.out.println("responseFuture callback not found!");
                                    return;
                                }
                                if (response.getException() != null){
                                    responseFuture.onFailure(response);
                                }
                                else{
                                    responseFuture.onSuccess(response);
                                }
                            }
                        }));
                    }
                });
    }

    @Override
    public void close() {
        group.shutdownGracefully();
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    /**
     * 移除回调的responseFuture
     *
     * @param requestId
     * @return
     */
    public ResponseFuture removeCallback(long requestId) {
        return callbackMap.remove(requestId);
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public void registerCallback(long requestId, ResponseFuture responseFuture) {
        this.callbackMap.put(requestId, responseFuture);
    }
}
