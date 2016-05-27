package com.middleware.study.rpc.transport.impl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuhaitao
 * @date 2016/5/26 16:15
 */
public class NettyServerChannelManage extends ChannelInboundHandlerAdapter {

    private ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<>();
    private int maxChannel = 0;

    public NettyServerChannelManage(int maxChannel) {
        this.maxChannel = maxChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        io.netty.channel.Channel channel = ctx.channel();
        String channelKey = getChannelKey((InetSocketAddress) channel.localAddress(),
                (InetSocketAddress) channel.remoteAddress());

        if (channels.size() > maxChannel) {
            // 超过最大连接数限制，直接close连接
            System.out.println(String.format("NettyServerChannelManage channelConnected channel size out of limit: limit=%d current=%d",
                    maxChannel, channels.size()));

            channel.close();
        } else {
            channels.put(channelKey, channel);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        io.netty.channel.Channel channel = ctx.channel();
        String channelKey = getChannelKey((InetSocketAddress) channel.localAddress(),
                (InetSocketAddress) channel.remoteAddress());
        channels.remove(channelKey);
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    /**
     * close所有的连接
     */
    public void close() {
        for (Map.Entry<String, Channel> entry : channels.entrySet()) {
            try {
                Channel channel = entry.getValue();

                if (channel != null) {
                    channel.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getChannelKey(InetSocketAddress local, InetSocketAddress remote) {
        String key = "";
        if (local == null || local.getAddress() == null) {
            key += "null-";
        } else {
            key += local.getAddress().getHostAddress() + ":" + local.getPort() + "-";
        }

        if (remote == null || remote.getAddress() == null) {
            key += "null";
        } else {
            key += remote.getAddress().getHostAddress() + ":" + remote.getPort();
        }

        return key;
    }
}
