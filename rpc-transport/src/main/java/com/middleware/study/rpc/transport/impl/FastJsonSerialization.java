package com.middleware.study.rpc.transport.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.middleware.study.rpc.transport.api.Serialization;

import java.io.IOException;

/**
 * @author wuhaitao
 * @date 2016/5/27 23:17
 */
public class FastJsonSerialization implements Serialization {
    @Override
    public byte[] serialize(Object obj) throws IOException {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.config(SerializerFeature.WriteClassName, true);
        serializer.write(obj);
        return out.toBytes(null);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        return JSON.parseObject(new String(bytes), clz);
    }
}
