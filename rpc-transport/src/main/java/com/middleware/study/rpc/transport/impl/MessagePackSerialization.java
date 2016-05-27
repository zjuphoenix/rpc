package com.middleware.study.rpc.transport.impl;

import com.middleware.study.rpc.transport.api.Serialization;
import org.msgpack.MessagePack;

import java.io.IOException;

/**
 * @author wuhaitao
 * @date 2016/5/26 13:24
 */
public class MessagePackSerialization implements Serialization {
    @Override
    public byte[] serialize(Object obj) throws IOException {
        MessagePack messagePack = new MessagePack();
        byte[] bytes = messagePack.write(obj);
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        MessagePack messagePack = new MessagePack();
        return messagePack.read(bytes, clz);
    }
}
