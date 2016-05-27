package com.middleware.study.rpc.transport.impl;

import org.apache.commons.pool.BasePoolableObjectFactory;

/**
 * @author wuhaitao
 * @date 2016/5/25 23:39
 */
public class NettyChannelFactory extends BasePoolableObjectFactory {

    private NettyClient nettyClient;

    public NettyChannelFactory(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public Object makeObject() throws Exception {
        NettyChannel channel = new NettyChannel(nettyClient);
        channel.open();
        return channel;
    }

    @Override
    public void destroyObject(Object obj) throws Exception {
        if (obj instanceof NettyChannel){
            NettyChannel channel = (NettyChannel) obj;
            channel.close();
        }
    }

    @Override
    public boolean validateObject(Object obj) {
        if (obj instanceof NettyChannel){
            final NettyChannel channel = (NettyChannel) obj;
            return channel.isAvailable();
        }
        else{
            return false;
        }
    }

    @Override
    public void activateObject(Object obj) throws Exception {
        if (obj instanceof NettyChannel){
            NettyChannel channel = (NettyChannel) obj;
            if (!channel.isAvailable()) {
                channel.open();
            }
        }
    }

    @Override
    public void passivateObject(Object obj) throws Exception {

    }

}
